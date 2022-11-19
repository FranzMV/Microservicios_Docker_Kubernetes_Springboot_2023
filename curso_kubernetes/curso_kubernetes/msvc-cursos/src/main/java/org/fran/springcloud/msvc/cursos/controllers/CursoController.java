package org.fran.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import org.fran.springcloud.msvc.cursos.models.Usuario;
import org.fran.springcloud.msvc.cursos.models.entity.Curso;
import org.fran.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/")
    public ResponseEntity<List<Curso>> listarCursos(){
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarCursoPorId(@PathVariable Long id){
        Optional<Curso> cursoOptional = cursoService.listarCursoPorId(id);
        return cursoOptional.isPresent() ? ResponseEntity.ok(cursoOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crearCurso(@Valid @RequestBody Curso curso, BindingResult result){
        if(result.hasErrors()){ return validar(result); }
        Curso aux = cursoService.guardarCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editarCurso(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){ return validar(result); }

        Optional<Curso> cursoOptional = cursoService.listarCursoPorId(id);
        if(cursoOptional.isPresent()){
            Curso aux = cursoOptional.get();
            aux.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardarCurso(aux));
        }
        return  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id){
        Optional<Curso> cursoOptional = cursoService.listarCursoPorId(id);
        if(cursoOptional.isPresent()){
            cursoService.eliminarCurso(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional;
        try{
            usuarioOptional = cursoService.asignarUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("ERROR", "No existe el " +
                    "usuario por id o error en la comunicación: ".concat(e.getMessage())));
        }

        return usuarioOptional.isPresent() ?  ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional;
        try{
            usuarioOptional = cursoService.crearUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("ERROR",
                    "No se pudo crear el usuario o error en la comunicación: ".concat(e.getMessage())));
        }

        return usuarioOptional.isPresent() ? ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get()) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional;
        try{
            usuarioOptional = cursoService.eliminarUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("ERROR", "No existe el " +
                    "usuario por id o error en la comunicación: ".concat(e.getMessage())));
        }
        return usuarioOptional.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get()) :
                ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> errores.put(error.getField(), "El campo ".concat(error.getField()).concat(" "+error.getDefaultMessage() )));
        return ResponseEntity.badRequest().body(errores);
    }
}
