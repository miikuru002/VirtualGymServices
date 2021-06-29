package com.gosh.vigym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gosh.vigym.model.Curso;
import com.gosh.vigym.model.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer>{
	
	//LOGIN
	@Query("SELECT e FROM Estudiante e where e.username = :username and e.password = :password")
    Estudiante findByUsernameAndPassword(
			@Param("username") String username,
			@Param("password") String password);

	//verificar si ese username ya esta ocupado
	boolean existsByUsername(String username);
	//verificar si ese correo ya esta ocupado
	boolean existsByCorreo(String correo);
		
	//obtener cursos matriculados
	@Query(value = "SELECT * FROM matricula WHERE estudiante_id = :id_estudiante", nativeQuery = true)
	List matricula(@Param("id_estudiante") int id_estudiante);
	
	//busca estudiantes por username
	Estudiante findByUsername(String username);
	Estudiante findByCorreo(String correo);
	//busca estudiantes por nombres
	List<Estudiante> findByNombre(String nombre);
	//busca estudiantes por apellidos
	List<Estudiante> findByApellido(String apellido);
}
