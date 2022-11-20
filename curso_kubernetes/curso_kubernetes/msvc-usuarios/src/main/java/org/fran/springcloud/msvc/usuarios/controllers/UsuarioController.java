package org.fran.springcloud.msvc.usuarios.controllers;

import org.fran.springcloud.msvc.usuarios.models.entity.Usuario;
import org.fran.springcloud.msvc.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarUsuarioPorId(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);

        return usuarioOptional.isPresent() ?
                ResponseEntity.ok().body(usuarioOptional.get()) :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario, BindingResult result){
        if(result.hasErrors()){ return validar(result); }

        if(!usuario.getEmail().isEmpty() && usuarioService.usuarioExistePorEmail(usuario.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("Error","Ya existe un usuario con ese email"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){ return validar(result); }

        Optional<Usuario> aux = usuarioService.buscarUsuarioPorId(id);
        if(aux.isPresent()){
            Usuario editarUsuario = aux.get();
            if(!usuario.getEmail().isEmpty() &&
                    !usuario.getEmail().equalsIgnoreCase(editarUsuario.getEmail()) &&
                    usuarioService.buscarUsuarioPorEmail(usuario.getEmail()).isPresent()){

                return ResponseEntity
                        .badRequest()
                        .body(Collections.singletonMap("Error","Ya existe un usuario con ese email"));
            }
            editarUsuario.setNombre(usuario.getNombre());
            editarUsuario.setEmail(usuario.getEmail());
            editarUsuario.setPassword(usuario.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(editarUsuario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        Optional<Usuario> optionalUsuario = usuarioService.buscarUsuarioPorId(id);

        if(optionalUsuario.isPresent()){
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(usuarioService.listarUsuariosPorIds(ids));
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            errores.put(error.getField(), "El campo ".concat(error.getField()).concat(" "+error.getDefaultMessage() ));
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
