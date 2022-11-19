package org.fran.springcloud.msvc.usuarios.service;

import org.fran.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listarUsuarios();
    Optional<Usuario> buscarUsuarioPorId(Long id);
    Usuario guardarUsuario(Usuario usuario);
    void eliminarUsuario(Long id);
    List<Usuario> listarUsuariosPorIds(Iterable<Long> ids);

    Optional<Usuario> buscarUsuarioPorEmail(String email);
    boolean usuarioExistePorEmail(String email);

}
