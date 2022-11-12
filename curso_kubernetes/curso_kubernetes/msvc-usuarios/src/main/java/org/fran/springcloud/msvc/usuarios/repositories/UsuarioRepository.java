package org.fran.springcloud.msvc.usuarios.repositories;

import org.fran.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository  extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query("Select u from Usuario u where u.email=?1")
    Optional<Usuario> buscarPorEmail(String email);

    boolean existsByEmail(String email);
}
