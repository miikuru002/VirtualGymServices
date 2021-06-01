package com.gosh.vigym.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "profesores")
public class Profesor{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@NotNull
	@Column(length = 20)
	private String nombre;
	
	@NotNull
	@Column(length = 20)
	private String apellido;
	
	@NotNull
	@Column(length = 15, unique = true)
	private String username;
	
	@NotNull
	@Column(length = 30, unique = true)
	private String correo;
	
	@NotNull
	@Column(length = 20)
	private String password;
	
	@NotNull
	private String ocupacion;
	
	@NotNull
	private int experiencia;
	
	@NotNull
	private int edad;

	@JsonIgnore
	@OneToMany(mappedBy = "profesor")
	private Set<Curso> cursos = new HashSet<Curso>();
	
	public Profesor() {
		super();
	}
	public Profesor(@NotNull String nombre, @NotNull String apellido, @NotNull String username,
			@NotNull String correo, @NotNull String password, @NotNull String ocupacion, 
			@NotNull int experiencia, @NotNull int edad) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.correo = correo;
		this.password = password;
		this.ocupacion = ocupacion;
		this.experiencia = experiencia;
		this.edad = edad;
	}

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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOcupacion() {
		return ocupacion;
	}
	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}
	public int getExperiencia() {
		return experiencia;
	}
	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	public Set<Curso> getCursos() {
		return cursos;
	}


}
