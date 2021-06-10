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

import com.gosh.vigym.dto.ProfesorDto;
import com.gosh.vigym.dto.ProfesorPerfilDto;
import com.gosh.vigym.dto.Mensaje;
import com.gosh.vigym.model.Profesor;
import com.gosh.vigym.service.ProfesorServiceImpl;

@RestController
@RequestMapping("vigym/api/profesor")
@CrossOrigin(origins = "*")
public class ProfesorController {

	@Autowired
	ProfesorServiceImpl service;

	//-----------------------------------[LISTADOS]-----------------------------------
	@GetMapping("/lista")
	public ResponseEntity<Iterable<Profesor>> listar() {
		
		Iterable<Profesor> estudiantes = service.getProfesores();
		return new ResponseEntity<Iterable<Profesor>>(estudiantes, HttpStatus.OK); // 200
	}
	
	@GetMapping("/listaPage")
	public ResponseEntity<Page<Profesor>> listaPaginada(@RequestParam(defaultValue = "0") int page, // se pasa como parametro el numero de pagina
			@RequestParam(defaultValue = "10") int size, // el tamaño de cada pagina
			@RequestParam(defaultValue = "nombre") String order, // las paginas se ordenaran por nombre, orden alfabetico
			@RequestParam(defaultValue = "true") boolean asc // se indica el orden (true=asc, false=desc)
	) {
		// que sea la pagina 0 x defecto, el tamaño de registros x pagina y que este
		// ordenado x nombre
		Page<Profesor> estudiantes = service.getProfesores(PageRequest.of(page, size, Sort.by(order)));

		if (!asc) // si asc es falso
			estudiantes = service.getProfesores(PageRequest.of(page, size, Sort.by(order).descending()));

		return new ResponseEntity<Page<Profesor>>(estudiantes, HttpStatus.OK); // 200
	}

	@GetMapping("/perfil/id/{id}") 
	public ResponseEntity<?> getById(@PathVariable("id") int id) {
		
		if (!service.existsById(id)) // si no existe el profesor
			return new ResponseEntity<Object>(new Mensaje("No existe el profesor con id: " + id), HttpStatus.NOT_FOUND);

		Profesor pro = service.findById(id);
		return new ResponseEntity<Profesor>(pro, HttpStatus.OK);
	}
	
	@GetMapping("/perfil/username/{username}") 
	public ResponseEntity<?> getByUsername(@PathVariable("username") String username) {
		
		if (!service.existsByUsername(username)) // si no existe el profesor
			return new ResponseEntity<Object>(new Mensaje("No existe el profesor con el nombre usuario: " + username), HttpStatus.NOT_FOUND);

		Profesor pro = service.findByUsername(username);
		return new ResponseEntity<Profesor>(pro, HttpStatus.OK);
	}

	@GetMapping("/buscar/nombre/{nombre}")
	public ResponseEntity<?> getByNombre(@PathVariable("nombre") String nombre) {
		
		if (service.findByNombre(nombre).size() == 0) // si hay 0 profesores con ese nombre
			return new ResponseEntity<Object>(new Mensaje("No existen profesores con el nombre: " + nombre), HttpStatus.NOT_FOUND);

		List<Profesor> profesores = service.findByNombre(nombre);
		return new ResponseEntity<List<Profesor>>(profesores, HttpStatus.OK);
	}

	@GetMapping("/buscar/apellido/{apellido}")
	public ResponseEntity<?> getBySesiones(@PathVariable("apellido") String apellido) {
		
		if (service.findByApellido(apellido).size() == 0) 
			return new ResponseEntity<Object>(new Mensaje("No existen profesores con el apellido: " + apellido), HttpStatus.NOT_FOUND);

		List<Profesor> profesores = service.findByApellido(apellido);
		return new ResponseEntity<List<Profesor>>(profesores, HttpStatus.OK); // retorna la lista con los nombres
	}

	@PostMapping("/registrar")
	public ResponseEntity<?> create(@RequestBody ProfesorDto profesorDto) { // se va a pasar un json por medio de Http
		
		if (StringUtils.isBlank(profesorDto.getNombre()))
			return new ResponseEntity<Object>(new Mensaje("Tu nombre es un campo obligatorio"), HttpStatus.BAD_REQUEST); 
		
		if (StringUtils.isBlank(profesorDto.getApellido()))
			return new ResponseEntity<Object>(new Mensaje("Tu apellido es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		
		if (StringUtils.isBlank(profesorDto.getUsername()))
			return new ResponseEntity<Object>(new Mensaje("Tu nombre de usuario es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		if (service.existsByUsername(profesorDto.getUsername()))
			return new ResponseEntity<Object>(new Mensaje("Ese nombre de usuario ya está en uso"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(profesorDto.getCorreo()))
			return new ResponseEntity<Object>(new Mensaje("Tu correo es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		if (service.existsByCorreo(profesorDto.getCorreo()))
			return new ResponseEntity<Object>(new Mensaje("Ese correo ya está registrado"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(profesorDto.getPassword()))
			return new ResponseEntity<Object>(new Mensaje("Tu contraseña es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		
		if (StringUtils.isBlank(profesorDto.getOcupacion()))
			return new ResponseEntity<Object>(new Mensaje("Es nececario indicar tu ocupación actual"), HttpStatus.BAD_REQUEST);
		
		if (profesorDto.getEdad() == null || profesorDto.getEdad() < 18)
			return new ResponseEntity<Object>(new Mensaje("Debes tener como mínimo 18 años"), HttpStatus.BAD_REQUEST);
		
		if (profesorDto.getExperiencia() == null || profesorDto.getExperiencia() < 2)
			return new ResponseEntity<Object>(new Mensaje("Debes tener como mínimo 2 años de experiencia"), HttpStatus.BAD_REQUEST);
		
		Profesor pro = new Profesor(profesorDto.getNombre(), profesorDto.getApellido(), profesorDto.getUsername(), 
				profesorDto.getCorreo(), profesorDto.getPassword(), profesorDto.getOcupacion(), profesorDto.getExperiencia(), profesorDto.getEdad());
		service.saveProfesor(pro);

		return new ResponseEntity<Object>(new Mensaje("Registrado correctamente"), HttpStatus.CREATED); // 201
	}
	
	@PostMapping("/login") 
	public ResponseEntity<?> login(@RequestBody ProfesorDto estudianteDto) {
		
		if (StringUtils.isBlank(estudianteDto.getUsername())) // si no existe el profesor
			return new ResponseEntity<Object>(new Mensaje("Escribe tu nombre de usuario"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(estudianteDto.getPassword()))
			return new ResponseEntity<Object>(new Mensaje("Escribe tu contraseña"), HttpStatus.BAD_REQUEST);

		Profesor pro = service.findByUsernameAndPassword(estudianteDto.getUsername(), estudianteDto.getPassword());
		return new ResponseEntity<Profesor>(pro, HttpStatus.OK);
	}

	@PutMapping("/actualizar/cuenta/{id}")
	public ResponseEntity<?> updateDatosCuenta(@PathVariable("id") int id, @RequestBody ProfesorDto profesorDto) {
		
		if (!service.existsById(id)) // si no existe el profesor
			return new ResponseEntity<Object>(new Mensaje("No existe el profesor con id: " + id),	HttpStatus.NOT_FOUND); //400 
		
		// si se intenta actualizar un profesor con el username de otro profesor que ya existe
		if (service.existsByUsername(profesorDto.getUsername()) && service.findByUsername(profesorDto.getUsername()).getId() != id)
			return new ResponseEntity<Object>(new Mensaje("Ya existe un profesor con ese nombre de usuario"), HttpStatus.BAD_REQUEST); //400
		if (StringUtils.isBlank(profesorDto.getUsername()))
			return new ResponseEntity<Object>(new Mensaje("Tu nombre de usuario es un campo obligatorio"), HttpStatus.BAD_REQUEST); 
		
		if (service.existsByCorreo(profesorDto.getCorreo()) && service.findByCorreo(profesorDto.getCorreo()).getId() != id)
			return new ResponseEntity<Object>(new Mensaje("Ya existe un profesor con ese correo registrado"), HttpStatus.BAD_REQUEST); //400
		if (StringUtils.isBlank(profesorDto.getCorreo()))
			return new ResponseEntity<Object>(new Mensaje("Tu correo es un campo obligatorio"), HttpStatus.BAD_REQUEST); 

		if (StringUtils.isBlank(profesorDto.getNombre()))
			return new ResponseEntity<Object>(new Mensaje("Tu nombre es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(profesorDto.getApellido()))
			return new ResponseEntity<Object>(new Mensaje("Tu apellido es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(profesorDto.getPassword()))
			return new ResponseEntity<Object>(new Mensaje("Tu correo es un campo obligatorio"), HttpStatus.BAD_REQUEST);

		// se crea un nuevo objeto y se le setean los nuevos datos que fueron almacenados en el profesorDto
		Profesor pro = service.findById(id);
		pro.setNombre(profesorDto.getNombre());
		pro.setApellido(profesorDto.getApellido());
		pro.setUsername(profesorDto.getUsername());
		pro.setCorreo(profesorDto.getCorreo());
		pro.setPassword(profesorDto.getPassword());

		service.saveProfesor(pro);
		return new ResponseEntity<Object>(new Mensaje("Datos de la cuenta actualizados correctamente, vuelva a iniciar sesión"), HttpStatus.OK);
	}
	
	@PutMapping("/actualizar/datos/{id}")
	public ResponseEntity<?> updateDatosPerfil(@PathVariable("id") int id, @RequestBody ProfesorPerfilDto profesorPerfilDto) {
		
		if (!service.existsById(id)) // si no existe el profesor
			return new ResponseEntity<Object>(new Mensaje("No existe el profesor con id: " + id),	HttpStatus.NOT_FOUND); //400 
		
		if (StringUtils.isBlank(profesorPerfilDto.getOcupacion()))
			return new ResponseEntity<Object>(new Mensaje("Debes indicar tu ocupación actual"), HttpStatus.BAD_REQUEST);
		if (profesorPerfilDto.getExperiencia() == null || profesorPerfilDto.getExperiencia() < 2)
			return new ResponseEntity<Object>(new Mensaje("Debes tener como mínimo 2 años de experiencia"), HttpStatus.BAD_REQUEST);
		if (profesorPerfilDto.getEdad() == null || profesorPerfilDto.getEdad() < 18)
			return new ResponseEntity<Object>(new Mensaje("Debes tener como mínimo 18 años"), HttpStatus.BAD_REQUEST);
				
		// se crea un nuevo objeto y se le setean los nuevos datos que fueron almacenados en el estudiantePerfilDTO
		Profesor pro = service.findById(id);
		pro.setOcupacion(profesorPerfilDto.getOcupacion());
		pro.setExperiencia(profesorPerfilDto.getExperiencia());
		pro.setEdad(profesorPerfilDto.getEdad());
		
		service.saveProfesor(pro);
		return new ResponseEntity<Object>(new Mensaje("Datos actualizados correctamente"), HttpStatus.OK);
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		if (!service.existsById(id))
			return new ResponseEntity<Object>(new Mensaje("No existe el profesor con id: " + id), HttpStatus.NOT_FOUND);

		service.deleteProfesorById(id);
		return new ResponseEntity<Object>(new Mensaje("Profesor eliminado"), HttpStatus.OK);
	}

}
