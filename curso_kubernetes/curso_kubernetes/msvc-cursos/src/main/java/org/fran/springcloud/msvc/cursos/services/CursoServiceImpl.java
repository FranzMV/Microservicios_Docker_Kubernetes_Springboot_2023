package org.fran.springcloud.msvc.cursos.services;

import org.fran.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.fran.springcloud.msvc.cursos.models.Usuario;
import org.fran.springcloud.msvc.cursos.models.entity.Curso;
import org.fran.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.fran.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listarCursos() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> listarCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuarioCurso(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if(cursoOptional.isPresent()){
            //validar que el usuario existe en el microservicio
            Usuario usuarioMsvc = usuarioClientRest.listarUsuarioPorId(usuario.getId());
            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            //Asignamos el curso
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);

            return Optional.of(usuarioMsvc);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if(cursoOptional.isPresent()){
            //añadimos el nuevo usuario
            Usuario usuarioNuevoMsvc = usuarioClientRest.crearUsuario(usuario);
            Curso curso = cursoOptional.get();//Obtenemos el curso
            CursoUsuario cursoUsuario = new CursoUsuario();//Instancia relacion curso-usuario.
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());//Se añade a la relacion curso-usuario
            curso.addCursoUsuario(cursoUsuario); //Le asignamos el curso al nuevo usuario
            cursoRepository.save(curso);

            return Optional.of(usuarioNuevoMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuarioCurso(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if(cursoOptional.isPresent()){
            //validar que el usuario existe en el microservicio
            Usuario usuarioMsvc = usuarioClientRest.listarUsuarioPorId(usuario.getId());
            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            //Eliminamos el usuario del curso
            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);

            return Optional.of(usuarioMsvc);

        }
        return Optional.empty();
    }
}
