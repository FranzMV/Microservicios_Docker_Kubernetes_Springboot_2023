package org.fran.springcloud.msvc.cursos.services;

import org.fran.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.fran.springcloud.msvc.cursos.models.Usuario;
import org.fran.springcloud.msvc.cursos.models.entity.Curso;
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
    public Optional<Usuario> asignarUsuarioCurso(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> eliminarUsuarioCurso(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }
}
