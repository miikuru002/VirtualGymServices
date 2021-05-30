package com.gosh.vigym.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosh.vigym.model.Curso;

public interface CursoService  { //los servicios que hara el api

	//obtener todos los cursos
	Iterable<Curso> getCursos();
	Page<Curso> getCursos(Pageable pageable); //paginar el listado
		
	//verificar si existe el curso x id
	boolean existsById(int id);
	//verificar si existe el curso x nombre
	boolean existsByNombre(String nombre);
	
	//obtener curso x id
	Curso findById(int id);
	//metodo para devolver la entidad curso x nombre
	Curso findByNombre(String nombre);
		
	//metodo para devolver una lista de cursos x num de sesiones
	List<Curso> findBySesiones(int sesiones);
	//obtiene una lista de cursos x capacidad
	List<Curso> findByCapacidad(int capacidad);

	//devuelve la entidad curso (para insertar o actualizar)
	Curso saveCurso(Curso curso);
	//elimina al curso x id
	void deleteCursoById(int id);

}

