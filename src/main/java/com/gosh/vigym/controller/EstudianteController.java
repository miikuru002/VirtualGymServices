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

import com.gosh.vigym.dto.EstudianteDto;
import com.gosh.vigym.dto.EstudiantePerfilDto;
import com.gosh.vigym.dto.Mensaje;
import com.gosh.vigym.model.Curso;
import com.gosh.vigym.model.Estudiante;
import com.gosh.vigym.service.CursoServiceImpl;
import com.gosh.vigym.service.EstudianteServiceImpl;

@RestController
@RequestMapping("vigym/api/estudiante")
@CrossOrigin(origins = "*")
public class EstudianteController {

	@Autowired
	EstudianteServiceImpl service;
	
	@Autowired
	CursoServiceImpl serviceCurso;

	//-----------------------------------[LISTADOS]-----------------------------------
	@GetMapping("/lista")
	public ResponseEntity<Iterable<Estudiante>> listar() {
		
		Iterable<Estudiante> estudiantes = service.getEstudiantes();
		return new ResponseEntity<Iterable<Estudiante>>(estudiantes, HttpStatus.OK); // 200
	}
	
	@GetMapping("/listaPage")
	public ResponseEntity<Page<Estudiante>> listaPaginada(@RequestParam(defaultValue = "0") int page, // se pasa como parametro el numero de pagina
			@RequestParam(defaultValue = "10") int size, // el tamaño de cada pagina
			@RequestParam(defaultValue = "nombre") String order, // las paginas se ordenaran por nombre, orden alfabetico
			@RequestParam(defaultValue = "true") boolean asc // se indica el orden (true=asc, false=desc)
	) {
		// que sea la pagina 0 x defecto, el tamaño de registros x pagina y que este
		// ordenado x nombre
		Page<Estudiante> estudiantes = service.getEstudiantes(PageRequest.of(page, size, Sort.by(order)));

		if (!asc) // si asc es falso
			estudiantes = service.getEstudiantes(PageRequest.of(page, size, Sort.by(order).descending()));

		return new ResponseEntity<Page<Estudiante>>(estudiantes, HttpStatus.OK); // 200
	}

	@GetMapping("/perfil/id/{id}") 
	public ResponseEntity<?> getById(@PathVariable("id") int id) {
		
		if (!service.existsById(id)) // si no existe el estudiante
			return new ResponseEntity<Object>(new Mensaje("No existe el estudiante con id: " + id), HttpStatus.NOT_FOUND);

		Estudiante est = service.findById(id);
		return new ResponseEntity<Estudiante>(est, HttpStatus.OK);
	}
	
	@GetMapping("/perfil/username/{username}") 
	public ResponseEntity<?> getByUsername(@PathVariable("username") String username) {
		
		if (!service.existsByUsername(username)) // si no existe el estudiante
			return new ResponseEntity<Object>(new Mensaje("No existe el estudiante con el nombre usuario: " + username), HttpStatus.NOT_FOUND);

		Estudiante est = service.findByUsername(username);
		return new ResponseEntity<Estudiante>(est, HttpStatus.OK);
	}

	@GetMapping("/buscar/nombre/{nombre}")
	public ResponseEntity<?> getByNombre(@PathVariable("nombre") String nombre) {
		
		if (service.findByNombre(nombre).size() == 0) // si hay 0 estudiantes con ese nombre
			return new ResponseEntity<Object>(new Mensaje("No existen estudiantes con el nombre: " + nombre), HttpStatus.NOT_FOUND);

		List<Estudiante> esttudiantes = service.findByNombre(nombre);
		return new ResponseEntity<List<Estudiante>>(esttudiantes, HttpStatus.OK);
	}

	@GetMapping("/buscar/apellido/{apellido}")
	public ResponseEntity<?> getBySesiones(@PathVariable("apellido") String apellido) {
		
		if (service.findByApellido(apellido).size() == 0) 
			return new ResponseEntity<Object>(new Mensaje("No existen estudiantes con el apellido: " + apellido), HttpStatus.NOT_FOUND);

		List<Estudiante> estudiantes = service.findByApellido(apellido);
		return new ResponseEntity<List<Estudiante>>(estudiantes, HttpStatus.OK); // retorna la lista con los nombres
	}

	@PostMapping("/registrar")
	public ResponseEntity<?> create(@RequestBody EstudianteDto estudianteDto) { // se va a pasar un json por medio de Http
		
		if (StringUtils.isBlank(estudianteDto.getNombre()))
			return new ResponseEntity<Object>(new Mensaje("Tu nombre es un campo obligatorio"), HttpStatus.BAD_REQUEST); 
		
		if (StringUtils.isBlank(estudianteDto.getApellido()))
			return new ResponseEntity<Object>(new Mensaje("Tu apellido es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		
		if (StringUtils.isBlank(estudianteDto.getUsername()))
			return new ResponseEntity<Object>(new Mensaje("Tu nombre de usuario es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		if (service.existsByUsername(estudianteDto.getUsername()))
			return new ResponseEntity<Object>(new Mensaje("Ese nombre de usuario ya está en uso"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(estudianteDto.getCorreo()))
			return new ResponseEntity<Object>(new Mensaje("Tu correo es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		if (service.existsByCorreo(estudianteDto.getCorreo()))
			return new ResponseEntity<Object>(new Mensaje("Ese correo ya está registrado"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(estudianteDto.getPassword()))
			return new ResponseEntity<Object>(new Mensaje("Tu contraseña es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		
		Estudiante est = new Estudiante(estudianteDto.getNombre(), estudianteDto.getApellido(), estudianteDto.getUsername(), 
				estudianteDto.getCorreo(),	estudianteDto.getPassword());
		service.saveEstudiante(est);

		return new ResponseEntity<Object>(new Mensaje("Registrado correctamente"), HttpStatus.CREATED); // 201
	}
	
	@PostMapping("/login") 
	public ResponseEntity<?> login(@RequestBody EstudianteDto estudianteDto) {
		
		if (StringUtils.isBlank(estudianteDto.getUsername())) // si no existe el estudiante
			return new ResponseEntity<Object>(new Mensaje("Escribe tu nombre de usuario"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(estudianteDto.getPassword()))
			return new ResponseEntity<Object>(new Mensaje("Escribe tu contraseña"), HttpStatus.BAD_REQUEST);

		Estudiante est = service.findByUsernameAndPassword(estudianteDto.getUsername(), estudianteDto.getPassword());
		return new ResponseEntity<Estudiante>(est, HttpStatus.OK);
	}

	@PutMapping("/actualizar/cuenta/{id}")
	public ResponseEntity<?> updateDatosCuenta(@PathVariable("id") int id, @RequestBody EstudianteDto estudianteDto) {
		
		if (!service.existsById(id)) // si no existe el estudiante
			return new ResponseEntity<Object>(new Mensaje("No existe el estudiante con id: " + id),	HttpStatus.NOT_FOUND); //400 
		
		// si se intenta actualizar un estudiante con el username de otro estudiante que ya existe
		if (service.existsByUsername(estudianteDto.getUsername()) && service.findByUsername(estudianteDto.getUsername()).getId() != id)
			return new ResponseEntity<Object>(new Mensaje("Ya existe un estudiante con ese nombre de usuario"), HttpStatus.BAD_REQUEST); //400
		if (StringUtils.isBlank(estudianteDto.getUsername()))
			return new ResponseEntity<Object>(new Mensaje("Tu nombre de usuario es un campo obligatorio"), HttpStatus.BAD_REQUEST); 
		
		if (service.existsByCorreo(estudianteDto.getCorreo()) && service.findByCorreo(estudianteDto.getCorreo()).getId() != id)
			return new ResponseEntity<Object>(new Mensaje("Ya existe un estudiante con ese correo registrado"), HttpStatus.BAD_REQUEST); //400
		if (StringUtils.isBlank(estudianteDto.getCorreo()))
			return new ResponseEntity<Object>(new Mensaje("Tu correo es un campo obligatorio"), HttpStatus.BAD_REQUEST); 

		if (StringUtils.isBlank(estudianteDto.getNombre()))
			return new ResponseEntity<Object>(new Mensaje("Tu nombre es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(estudianteDto.getApellido()))
			return new ResponseEntity<Object>(new Mensaje("Tu apellido es un campo obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(estudianteDto.getPassword()))
			return new ResponseEntity<Object>(new Mensaje("Tu correo es un campo obligatorio"), HttpStatus.BAD_REQUEST);

		// se crea un nuevo objeto y se le setean los nuevos datos que fueron almacenados en el estudianteDTO
		Estudiante est = service.findById(id);
		est.setNombre(estudianteDto.getNombre());
		est.setApellido(estudianteDto.getApellido());
		est.setUsername(estudianteDto.getUsername());
		est.setCorreo(estudianteDto.getCorreo());
		est.setPassword(estudianteDto.getPassword());

		service.saveEstudiante(est);
		return new ResponseEntity<Object>(new Mensaje("Datos de la cuenta actualizados correctamente, vuelva a iniciar sesión"), HttpStatus.OK);
	}
	
	@PutMapping("/actualizar/datos/{id}")
	public ResponseEntity<?> updateDatosPerfil(@PathVariable("id") int id, @RequestBody EstudiantePerfilDto estudiantePerfilDto) {
		
		if (!service.existsById(id)) // si no existe el estudiante
			return new ResponseEntity<Object>(new Mensaje("No existe el estudiante con id: " + id),	HttpStatus.NOT_FOUND); //400 
		
		if (estudiantePerfilDto.getEstatura() == null || estudiantePerfilDto.getEstatura() < 50)
			return new ResponseEntity<Object>(new Mensaje("Debes poner una estatura válida"), HttpStatus.BAD_REQUEST);
		if (estudiantePerfilDto.getPeso() == null || estudiantePerfilDto.getPeso() < 10)
			return new ResponseEntity<Object>(new Mensaje("Debes poner un peso válido"), HttpStatus.BAD_REQUEST);

		double imc = (estudiantePerfilDto.getPeso()) / ((estudiantePerfilDto.getEstatura()/100.0)*(estudiantePerfilDto.getEstatura()/100.0));
				
		// se crea un nuevo objeto y se le setean los nuevos datos que fueron almacenados en el estudiantePerfilDTO
		Estudiante est = service.findById(id);
		est.setEstatura(estudiantePerfilDto.getEstatura());
		est.setPeso(estudiantePerfilDto.getPeso());
		est.setImc(Math.round(imc*100.0)/100.0);
		
		service.saveEstudiante(est);
		return new ResponseEntity<Object>(new Mensaje("Datos actualizados correctamente"), HttpStatus.OK);
	}
	
	@PutMapping("/agregarSaldo/{id}")
	public ResponseEntity<?> addSaldo(@PathVariable("id") int id, @RequestBody EstudiantePerfilDto estudiantePerfilDto) {
		
		if (!service.existsById(id)) // si no existe el estudiante
			return new ResponseEntity<Object>(new Mensaje("No existe el estudiante con id: " + id),	HttpStatus.NOT_FOUND); //400 
		
		if (estudiantePerfilDto.getSaldo() == null || estudiantePerfilDto.getSaldo() < 10)
			return new ResponseEntity<Object>(new Mensaje("Debes agregar como mínimo 10 de saldo"), HttpStatus.BAD_REQUEST);
		
		Estudiante est = service.findById(id);
		est.setSaldo(est.getSaldo() + estudiantePerfilDto.getSaldo());
		
		service.saveEstudiante(est);
		return new ResponseEntity<Object>(new Mensaje("Se ha agregado " + estudiantePerfilDto.getSaldo() +" de saldo correctamente"), HttpStatus.OK);
	}

	@GetMapping("/cursos/{estudiante_id}")
	public ResponseEntity<?> matricula_estudiante(@PathVariable("estudiante_id") int id_estudiante) {
		
		if (!service.existsById(id_estudiante)) // si no existe el estudiante
			return new ResponseEntity<Object>(new Mensaje("No existe el estudiante con id: " + id_estudiante), HttpStatus.NOT_FOUND);

			List cursos = service.matricula(id_estudiante);
			return new ResponseEntity<List<Curso>>(cursos, HttpStatus.OK); // retorna la lista con los nombres
	}
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		if (!service.existsById(id))
			return new ResponseEntity<Object>(new Mensaje("No existe el estudiante con id: " + id), HttpStatus.NOT_FOUND);

		service.deleteEstudianteById(id);
		return new ResponseEntity<Object>(new Mensaje("Estudiante eliminado"), HttpStatus.OK);
	}

}
