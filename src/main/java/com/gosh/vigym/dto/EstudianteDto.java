package com.gosh.vigym.dto;

import javax.validation.constraints.NotBlank;

public class EstudianteDto {
	
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
	
	public EstudianteDto() {
		super();
	}
	public EstudianteDto(@NotBlank String nombre, @NotBlank String apellido, @NotBlank String username,
			@NotBlank String correo, @NotBlank String password) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.correo = correo;
		this.password = password;
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
	
}
