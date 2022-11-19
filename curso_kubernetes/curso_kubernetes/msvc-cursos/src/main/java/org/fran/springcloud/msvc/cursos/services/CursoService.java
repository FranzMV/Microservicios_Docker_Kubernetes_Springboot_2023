package org.fran.springcloud.msvc.cursos.services;

import org.apache.juli.logging.Log;
import org.fran.springcloud.msvc.cursos.models.Usuario;
import org.fran.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listarCursos();
    Optional<Curso> listarCursoPorId(Long id);
    Curso guardarCurso(Curso curso);
    void eliminarCurso(Long id);

    //Los datos se obtienen de otro servicio
    /**
     * Asigna un usuario ya existente a un curso actual.
     * @param usuario Usuario a asignar
     * @param cursoId del curso
     * @return Usuario que ha sido asignado al curso
     */
    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);

    /**
     * Crea un nuevo usuario que no exista en el microservicio usuarios.
     * Desde el microservicio cursos se envía este nuevo usuario para crearlo en el
     * microservicio usuarios, posteriormente asignándolo al curso id.
     * @param usuario Usuario a crear
     * @param cursoId correspondiente al curso
     * @return Usuario
     */
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);

    /**
     * Elimina un usuario de un curso
     * @param usuario
     * @param cursoId
     * @return
     */
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);

}
