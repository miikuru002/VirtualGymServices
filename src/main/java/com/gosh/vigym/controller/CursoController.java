package com.gosh.vigym.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gosh.vigym.dto.CursoDto;
import com.gosh.vigym.dto.Mensaje;
import com.gosh.vigym.model.Curso;
import com.gosh.vigym.model.Estudiante;
import com.gosh.vigym.model.Profesor;
import com.gosh.vigym.service.CursoServiceImpl;
import com.gosh.vigym.service.EstudianteServiceImpl;
import com.gosh.vigym.service.ProfesorServiceImpl;

@RestController // se indica que es un API REST
@RequestMapping("vigym/api/curso") // la url
@CrossOrigin(origins = "*") // evitar el error de CORS, se puede indicar la url especifica con el puerto
public class CursoController {

	@Autowired
	CursoServiceImpl service;
	@Autowired
	EstudianteServiceImpl serviceEst;
	@Autowired
	ProfesorServiceImpl servicePro;

	@GetMapping("/lista")
	public ResponseEntity<Iterable<Curso>> listar() {
		
		Iterable<Curso> cursos = service.getCursos();
		return new ResponseEntity<Iterable<Curso>>(cursos, HttpStatus.OK); // 200
	}

	@GetMapping("/listaPage")
	public ResponseEntity<Page<Curso>> listaPaginada(@RequestParam(defaultValue = "0") int page, // se pasa como parametro el numero de pagina
			@RequestParam(defaultValue = "10") int size, // el tamaño de cada pagina
			@RequestParam(defaultValue = "nombre") String order, // las paginas se ordenaran por nombre, orden alfabetico
			@RequestParam(defaultValue = "true") boolean asc // se indica el orden (true=asc, false=desc)
	) {
		// que sea la pagina 0 x defecto, el tamaño de registros x pagina y que este
		// ordenado x nombre
		Page<Curso> cursos = service.getCursos(PageRequest.of(page, size, Sort.by(order)));

		if (!asc) // si asc es falso
			cursos = service.getCursos(PageRequest.of(page, size, Sort.by(order).descending()));

		return new ResponseEntity<Page<Curso>>(cursos, HttpStatus.OK); // 200
	}

	@GetMapping("/detalle/id/{id}") // {} es variable
	public ResponseEntity<?> getById(@PathVariable("id") int id) {
		
		if (!service.existsById(id)) // si no existe el curso
			return new ResponseEntity<Object>(new Mensaje("No existe el curso con id: " + id), HttpStatus.NOT_FOUND); // retorna mensaje y 404

		Curso cur = service.findById(id);
		return new ResponseEntity<Curso>(cur, HttpStatus.OK);
	}

	@GetMapping("/detalle/nombre/{nombre}")
	public ResponseEntity<?> getByNombre(@PathVariable("nombre") String nombre) {
		
		if (service.findByNombre(nombre) == null) // si retorna nulo
			return new ResponseEntity<Object>(new Mensaje("No existe el curso con nombre: " + nombre), HttpStatus.NOT_FOUND);

		Curso cur = service.findByNombre(nombre);
		return new ResponseEntity<Curso>(cur, HttpStatus.OK);
	}

	@GetMapping("/lista/sesiones/{sesiones}")
	public ResponseEntity<?> getBySesiones(@PathVariable("sesiones") int sesiones) {
		
		if (service.findBySesiones(sesiones).size() == 0) // si no hay cursos con ese num de sesiones
			return new ResponseEntity<Object>(new Mensaje("No existen cursos con numero de sesiones: " + sesiones), HttpStatus.NOT_FOUND);

		List<Curso> cursos = service.findBySesiones(sesiones);
		return new ResponseEntity<List<Curso>>(cursos, HttpStatus.OK); // retorna la lista con los nombres
	}

	@GetMapping("/lista/capacidad/{capacidad}")
	public ResponseEntity<?> getByCapacidad(@PathVariable("capacidad") int capacidad) {
		
		if (service.findByCapacidad(capacidad).size() == 0) // si no hay cursos con esa capacidad
			return new ResponseEntity<Object>(new Mensaje("No existen cursos con capacidad: " + capacidad), HttpStatus.NOT_FOUND);

		List<Curso> cursos = service.findByCapacidad(capacidad);
		return new ResponseEntity<List<Curso>>(cursos, HttpStatus.OK); // retorna la lista con los nombres
	}

	@PostMapping("/crear")
	public ResponseEntity<?> create(@RequestBody CursoDto cursoDto) { // se va a pasar un json por medio de Http
		
		if (StringUtils.isBlank(cursoDto.getNombre()))
			return new ResponseEntity<Object>(new Mensaje("El nombre del curso es obligatorio"), HttpStatus.BAD_REQUEST); // 400
		
		if (service.existsByNombre(cursoDto.getNombre()))
			return new ResponseEntity<Object>(new Mensaje("Ese curso con ese nombre ya existe"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(cursoDto.getDescripcion()))
			return new ResponseEntity<Object>(new Mensaje("La descripcion del curso es obligatoria"), HttpStatus.BAD_REQUEST);

		if (cursoDto.getSesiones() == null || cursoDto.getSesiones() < 3)
			return new ResponseEntity<Object>(new Mensaje("El curso debe tener como mínimo 3 sesiones"), HttpStatus.BAD_REQUEST);

		if (cursoDto.getCapacidad() == null || cursoDto.getCapacidad() < 5)
			return new ResponseEntity<Object>(new Mensaje("El curso debe tener como mínimo 5 personas de capacidad"), HttpStatus.BAD_REQUEST);
		
		if (cursoDto.getPrecio() == null || cursoDto.getPrecio() < 0)
			return new ResponseEntity<Object>(new Mensaje("Debes especificar un precio válido para el curso"), HttpStatus.BAD_REQUEST);
		
		if (cursoDto.getCalorias_perdidas() == null || cursoDto.getCalorias_perdidas() < 0)
			return new ResponseEntity<Object>(new Mensaje("Debes espeficiar cuantas calorías se pierden con este curso"), HttpStatus.BAD_REQUEST);

		Curso cur = new Curso(cursoDto.getNombre(), cursoDto.getDescripcion(), cursoDto.getSesiones(), 
							cursoDto.getCapacidad(), cursoDto.getPrecio(), cursoDto.getCalorias_perdidas());
		service.saveCurso(cur);

		return new ResponseEntity<Object>(new Mensaje("Curso creado correctamente"), HttpStatus.CREATED); // 201
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody CursoDto cursoDTO) {
		
		if (!service.existsById(id)) // si no existe el curso
			return new ResponseEntity<Object>(new Mensaje("No existe el curso con id: " + id),	HttpStatus.NOT_FOUND); //400 
		
		// si se intenta actualizar un curso con el nombre de otro curso que ya existe
		if (service.existsByNombre(cursoDTO.getNombre()) && service.findByNombre(cursoDTO.getNombre()).getId() != id)
			return new ResponseEntity<Object>(new Mensaje("Ese curso con ese nombre ya existe"), HttpStatus.BAD_REQUEST); //400
		if (StringUtils.isBlank(cursoDTO.getNombre()))
			return new ResponseEntity<Object>(new Mensaje("El nombre del curso es obligatorio"), HttpStatus.BAD_REQUEST); 

		if (StringUtils.isBlank(cursoDTO.getDescripcion()))
			return new ResponseEntity<Object>(new Mensaje("La descripción del curso es obligatoria"), HttpStatus.BAD_REQUEST);

		if (cursoDTO.getSesiones() == null || cursoDTO.getSesiones() < 3)
			return new ResponseEntity<Object>(new Mensaje("El curso debe tener como mínimo 3 sesiones"), HttpStatus.BAD_REQUEST);
		
		if (cursoDTO.getCapacidad() == null || cursoDTO.getCapacidad() < 5)
			return new ResponseEntity<Object>(new Mensaje("El curso debe tener como mínimo 5 personas de capacidad"), HttpStatus.BAD_REQUEST);

		if (cursoDTO.getCalorias_perdidas() == null || cursoDTO.getCalorias_perdidas() < 0)
			return new ResponseEntity<Object>(new Mensaje("Debes espeficiar cuantas calorías se pierden con este curso"), HttpStatus.BAD_REQUEST);
		
		// se crea un nuevo objeto y se le setean los nuevos datos que fueron almacenados en el cursoDTO
		Curso cur = service.findById(id);
		cur.setNombre(cursoDTO.getNombre());
		cur.setDescripcion(cursoDTO.getDescripcion());
		cur.setSesiones(cursoDTO.getSesiones());
		cur.setCapacidad(cursoDTO.getCapacidad());
		cur.setCalorias_perdidas(cursoDTO.getCalorias_perdidas());

		service.saveCurso(cur);
		return new ResponseEntity<Object>(new Mensaje("Curso actualizado correctamente"), HttpStatus.OK);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		
		if (!service.existsById(id))
			return new ResponseEntity<Object>(new Mensaje("No existe el curso con id: " + id), HttpStatus.NOT_FOUND);

		service.deleteCursoById(id);
		return new ResponseEntity<Object>(new Mensaje("Curso eliminado"), HttpStatus.OK);
	}

	@PutMapping("/matricula/{curso_id}/{estudiante_id}")
	public ResponseEntity<?> matricula(@PathVariable("curso_id") int id_curso, @PathVariable("estudiante_id") int id_estudiante) {
		
		//obtiene los dato del estudiante y dle curso
		Estudiante estudiante = serviceEst.findById(id_estudiante); 
		Curso curso = service.findById(id_curso);
		
		if (!service.existsById(id_curso))
			return new ResponseEntity<Object>(new Mensaje("No existe el curso con id: " + id_curso), HttpStatus.NOT_FOUND);
		
		if (!serviceEst.existsById(id_estudiante))
			return new ResponseEntity<Object>(new Mensaje("No existe el estudiante con id: " + id_estudiante), HttpStatus.NOT_FOUND);
		
		if (estudiante.getSaldo() < curso.getPrecio())
			return new ResponseEntity<Object>(new Mensaje("No tienes suficiente saldo para comprar este curso"), HttpStatus.BAD_REQUEST);
		
		curso.matricularEstudiante(estudiante);
		service.saveCurso(curso);
		
		return new ResponseEntity<Object>(new Mensaje("Te haz matriculado correctamente al curso: " + curso.getNombre()), HttpStatus.CREATED); // 201
	}
	
	@PutMapping("/asignar/{curso_id}/{profesor_id}")
	public ResponseEntity<?> asignarProfesor(@PathVariable("curso_id") int id_curso, @PathVariable("profesor_id") int id_profesor) {
		
		if (!service.existsById(id_curso))
			return new ResponseEntity<Object>(new Mensaje("No existe el curso con id: " + id_curso), HttpStatus.NOT_FOUND);
		
		if (!servicePro.existsById(id_profesor))
			return new ResponseEntity<Object>(new Mensaje("No existe el profesor con id: " + id_profesor), HttpStatus.NOT_FOUND);
		
		//obtiene el curso, obtiene el profesor y lo asigna a ese curso y lo guarda
		Curso curso = service.findById(id_curso);
		Profesor profesor = servicePro.findById(id_profesor);
		curso.asignarProfesor(profesor);
		
		service.saveCurso(curso);
		
		return new ResponseEntity<Object>(new Mensaje("Se ha asignado correctamente el profesor al curso"), HttpStatus.CREATED); 
	}
}