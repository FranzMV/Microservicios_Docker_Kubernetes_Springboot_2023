package org.fran.springcloud.msvc.cursos.services;

import org.fran.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listarCursos();
    Optional<Curso> listarCursoPorId(Long id);
    Curso guardarCurso(Curso curso);
    void eliminarCurso(Long id);
}
