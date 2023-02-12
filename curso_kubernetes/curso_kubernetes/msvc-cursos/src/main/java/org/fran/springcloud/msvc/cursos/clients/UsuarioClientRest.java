package org.fran.springcloud.msvc.cursos.clients;

import org.fran.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Cliente Http que consume el microservicio usuarios
 */
@FeignClient(name = "msvc-usuarios", url = "${msvc.usuarios.url}")//Microservicio a consumir, ruta url base del
//microservicio
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario listarUsuarioPorId(@PathVariable Long id);

    @PostMapping("/")
    Usuario crearUsuario(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-por-curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
