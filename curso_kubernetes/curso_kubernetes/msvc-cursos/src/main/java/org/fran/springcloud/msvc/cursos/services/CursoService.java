package org.fran.springcloud.msvc.cursos.services;

import org.apache.juli.logging.Log;
import org.fran.springcloud.msvc.cursos.models.Usuario;
import org.fran.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listarCursos();
    Optional<Curso> listarCursoPorId(Long id);
    Optional<Curso> listarCursoUsuariosPorId(Long id);
    Curso guardarCurso(Curso curso);
    void eliminarCurso(Long id);
    void eliminarCursoUsuarioPorId(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);

}
