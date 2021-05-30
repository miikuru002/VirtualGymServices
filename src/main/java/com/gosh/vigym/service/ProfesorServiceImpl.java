package com.gosh.vigym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gosh.vigym.model.Profesor;
import com.gosh.vigym.repository.ProfesorRepository;

@Service
public class ProfesorServiceImpl implements ProfesorService{

	@Autowired
	ProfesorRepository repo;

	@Override
	@Transactional(readOnly = true) //transaccion de solo lectura, no guarda nada en la BD
	public Iterable<Profesor> getProfesores() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Profesor> getProfesores(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Profesor findById(int id) {
		return repo.findById(id).orElse(null); //si no encuenta devuelve nulo
	}
	
	@Override
	@Transactional(readOnly = true)
	public Profesor findByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Profesor> findByNombre(String nombre) {
		List<Profesor> profesores = repo.findByNombre(nombre);
		return profesores;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Profesor> findByApellido(String apellido) {
		List<Profesor> profesores = repo.findByApellido(apellido);
		return profesores;
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
	@Transactional(readOnly = false) //false porque guarda datos en la BD
	public Profesor saveProfesor(Profesor profesor) {
		return repo.save(profesor);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteProfesorById(int id) {
		repo.deleteById(id);
	}
}
