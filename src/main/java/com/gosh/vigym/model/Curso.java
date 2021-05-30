package com.gosh.vigym.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity //creara una tabla en la BD apartir de esta clase
@Table(name = "cursos") //el nombre de la tabla
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //que sea Auto-Increment
	private int id;
	
	@Column(nullable = false, length = 20)
	private String nombre;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = false)
	private int sesiones;
	
	@Column(nullable = false)
	private int capacidad;
	
	@Column(nullable = false)
	private double calorias_perdidas;

	@ManyToMany
	@JoinTable( //crea una nueva tabla
			name = "matricula",
			joinColumns = @JoinColumn(name = "curso_id"),
			inverseJoinColumns = @JoinColumn(name = "estudiante_id")
			)
	private Set<Estudiante> estudiantesInscritos = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.ALL) //muchos cursos tienen 1 profesor
	@JoinColumn(name = "profesor_id", referencedColumnName = "id") //
	private Profesor profesor; //se declara un atributo de tipo Profeso
	
	//CONSTRUCTORES
	public Curso() { //constructor vacio para crear solo un objeto
		super();
	}
	public Curso(String nombre, String descripcion, int sesiones, int capacidad, double calorias_perdidas) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.sesiones = sesiones;
		this.capacidad = capacidad;
		this.calorias_perdidas = calorias_perdidas;
	}
	
	//GETTERS Y SETTERS
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getSesiones() {
		return sesiones;
	}
	public void setSesiones(int sesiones) {
		this.sesiones = sesiones;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public double getCalorias_perdidas() {
		return calorias_perdidas;
	}
	public void setCalorias_perdidas(double calorias_perdidas) {
		this.calorias_perdidas = calorias_perdidas;
	}
	
	
	
	
	public Set<Estudiante> getEstudiantesInscritos() {
		return estudiantesInscritos;
	}
	public Profesor getProfesor() {
		return profesor;
	}
	
	public void matricularEstudiante(Estudiante estudiante) {
		estudiantesInscritos.add(estudiante);
	}
	
	public void asignarProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
}
