package org.fran.springcloud.msvc.usuarios.controllers;

import org.fran.springcloud.msvc.usuarios.models.entity.Usuario;
import org.fran.springcloud.msvc.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        if(usuarioOptional.isPresent())
            return  ResponseEntity.ok().body(usuarioOptional.get());

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@RequestBody Usuario usuario, @PathVariable Long id){
        Optional<Usuario> aux = usuarioService.buscarUsuarioPorId(id);
        if(aux.isPresent()){
            Usuario editarUsuario = aux.get();
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
}
