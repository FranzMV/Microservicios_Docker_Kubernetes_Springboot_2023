package org.fran.springcloud.msvc.cursos.clients;

import org.fran.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Cliente Http que consume el microservicio usuarios
 */
@FeignClient(name = "msvc-usuarios", url = "localhost:8001")//Microservicio a consumir, ruta url base del microservicio
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario listarUsuarioPorId(@PathVariable Long id);

    @PostMapping("/")
    Usuario crearUsuario(@RequestBody Usuario usuario);
}
