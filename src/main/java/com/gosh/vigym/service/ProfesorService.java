package com.gosh.vigym.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosh.vigym.model.Profesor;

public interface ProfesorService {

	//obtener todos los estudiantes
	Iterable<Profesor> getProfesores();
	Page<Profesor> getProfesores(Pageable pageable);
				
	//verificar si existe el estudiante x id, username o correo
	boolean existsById(int id);
	boolean existsByUsername(String username);
	boolean existsByCorreo(String correo);
			
	//obtener estudiante x id o username
	Profesor findById(int id);
	Profesor findByUsername(String username);
	Profesor findByCorreo(String correo);
		
	//busca estudiantes por nombres
	List<Profesor> findByNombre(String nombre);
	List<Profesor> findByApellido(String apellido);
	
	//LOGIN
	Profesor findByUsernameAndPassword(String username, String password);
			
	//devuelve la entidad estudiante (para insertar o actualizar)
	Profesor saveProfesor(Profesor profesor);
	//elimina al estudiante x id
	void deleteProfesorById(int id);
}
