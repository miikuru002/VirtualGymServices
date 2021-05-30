package com.gosh.vigym.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gosh.vigym.model.Estudiante;
import com.gosh.vigym.repository.EstudianteRepository;

@Service
public class EstudianteServiceImpl implements EstudianteService {
	
	@Autowired
	EstudianteRepository repo;

	@Override
	@Transactional(readOnly = true) //transaccion de solo lectura, no guarda nada en la BD
	public Iterable<Estudiante> getEstudiantes() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Estudiante> getEstudiantes(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Estudiante findById(int id) {
		return repo.findById(id).orElse(null); //si no encuenta devuelve nulo
	}
	
	@Override
	@Transactional(readOnly = true)
	public Estudiante findByUsername(String username) {
		return repo.findByUsername(username);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Estudiante findByCorreo(String correo) {
		return repo.findByCorreo(correo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Estudiante> findByNombre(String nombre) {
		List<Estudiante> estudiantes = repo.findByNombre(nombre);
		return estudiantes;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Estudiante> findByApellido(String apellido) {
		List<Estudiante> estudiantes = repo.findByApellido(apellido);
		return estudiantes;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsById(int id) {
		return repo.existsById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByUsername(String username) {
		return repo.existsByUsername(username);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByCorreo(String correo) {
		return repo.existsByCorreo(correo);
	}

	@Override
	@Transactional(readOnly = true)
	public Estudiante findByUsernameAndPassword(String username, String password) {
		return repo.findByUsernameAndPassword(username, password);
	}
	
	@Override
	@Transactional(readOnly = false) //false porque guarda datos en la BD
	public Estudiante saveEstudiante(Estudiante estudiante) {
		return repo.save(estudiante);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteEstudianteById(int id) {
		repo.deleteById(id);
	}

}
