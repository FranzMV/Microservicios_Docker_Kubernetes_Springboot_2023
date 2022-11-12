package org.fran.springcloud.msvc.cursos.controllers;

import org.fran.springcloud.msvc.cursos.models.entity.Curso;
import org.fran.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        if(cursoOptional.isPresent()){
            return ResponseEntity.ok(cursoOptional.get());
        }
        return ResponseEntity.notFound().build();
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

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            errores.put(error.getField(), "El campo ".concat(error.getField()).concat(" "+error.getDefaultMessage() ));
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
