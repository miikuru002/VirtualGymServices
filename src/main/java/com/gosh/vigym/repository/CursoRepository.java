package com.gosh.vigym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gosh.vigym.model.Curso;

@Repository //se coloca el tipo de dato de los objetos (Curso) y el tipo de dato del ID (Integer)
public interface CursoRepository extends JpaRepository<Curso, Integer> {  //METODOS EXTRA PARA EL API (aparte de los que ya brinda Jpa Repo)
		
	//se importa de JPA R. porque se va a usar paginacion del lado del servidor
	//si van a ser 1000 datos, eso no se puede pasar por Angular porque tardaria
	//entonces por eso es la paginacion del lado del servidor, para que meustre items de 10 en 10
	//tambien tiene los metodos para CRUD, es mas completo
	
	//verificar si ese nombre ya esta ocupado
	boolean existsByNombre(String nombre);

	//busca cursos por nombre
	Curso findByNombre(String nombre);
	//busca cursos por num de sesiones
	List<Curso> findBySesiones(int sesiones);
	//busca cursos por capacidad
	List<Curso> findByCapacidad(int capacidad);
		
}
