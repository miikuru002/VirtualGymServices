package com.gosh.vigym.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosh.vigym.model.Estudiante;

public interface EstudianteService {

	//obtener todos los estudiantes
	Iterable<Estudiante> getEstudiantes();
	Page<Estudiante> getEstudiantes(Pageable pageable);
			
	//verificar si existe el estudiante x id, username o correo
	boolean existsById(int id);
	boolean existsByUsername(String username);
	boolean existsByCorreo(String correo);
		
	//obtener estudiante x id o username
	Estudiante findById(int id);
	Estudiante findByUsername(String username);
	Estudiante findByCorreo(String correo);
	
	//busca estudiantes por nombres
	List<Estudiante> findByNombre(String nombre);
	List<Estudiante> findByApellido(String apellido);
		
	//LOGIN
	Estudiante findByUsernameAndPassword(String username, String password);
	
	//devuelve la entidad estudiante (para insertar o actualizar)
	Estudiante saveEstudiante(Estudiante estudiante);
	//elimina al estudiante x id
	void deleteEstudianteById(int id);

}
