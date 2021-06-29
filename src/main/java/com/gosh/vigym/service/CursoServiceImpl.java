package com.gosh.vigym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gosh.vigym.model.Curso;
import com.gosh.vigym.repository.CursoRepository;
import com.gosh.vigym.repository.EstudianteRepository;

@Service
public class CursoServiceImpl implements CursoService{

	@Autowired //inyectar el repositorio (autoejecuten los metodos del repositorio)
	CursoRepository repo;
	@Autowired 
	EstudianteRepository repoEstudiante;

	@Override
	@Transactional(readOnly = true) //transaccion de solo lectura, no guarda nada en la BD
	public Iterable<Curso> getCursos() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Curso> getCursos(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Curso findById(int id) {
		return repo.findById(id).orElse(null); //si no encuenta devuelve nulo
	}
	
	@Override
	public Curso findByNombre(String nombre) {
		return repo.findByNombre(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(int id) {
		return repo.existsById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByNombre(String nombre) {
		return repo.existsByNombre(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Curso> findBySesiones(int sesiones) {
		List<Curso> cursos = repo.findBySesiones(sesiones);
		return cursos;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Curso> findByCapacidad(int capacidad) {
		List<Curso> cursos = repo.findByCapacidad(capacidad);
		return cursos;
	}

	@Override
	@Transactional(readOnly = false) //false porque guarda datos en la BD
	public Curso saveCurso(Curso curso) {
		return repo.save(curso);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteCursoById(int id) {
		repo.deleteById(id);
	}

}
