package com.gosh.vigym.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProfesorDto {

	@NotBlank
	private String nombre;
	
	@NotBlank
	private String apellido;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String correo;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String ocupacion;
	
	@Min(2) //minimo 2 años de experiencia
	private Integer experiencia;
	
	@Min(18) //minimo 18 años de edad
	private Integer edad;

	public ProfesorDto() {
		super();
	}
	public ProfesorDto(@NotBlank String nombre, @NotBlank String apellido, @NotBlank String username,
			@NotBlank String correo, @NotBlank String password, @NotBlank String ocupacion, @Min(2) Integer experiencia,
			@Min(18) Integer edad) {
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
	public Integer getExperiencia() {
		return experiencia;
	}
	public void setExperiencia(Integer experiencia) {
		this.experiencia = experiencia;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	
}
