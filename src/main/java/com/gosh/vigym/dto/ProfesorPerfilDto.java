package com.gosh.vigym.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProfesorPerfilDto {

	@NotBlank
	private String ocupacion;
	
	@Min(2) //minimo 2 años de experiencia
	private Integer experiencia;
	
	@Min(18) //minimo 18 años de edad
	private Integer edad;
	
	public ProfesorPerfilDto() {
		super();
	}
	public ProfesorPerfilDto(@NotBlank String ocupacion, @Min(2) Integer experiencia, @Min(18) Integer edad) {
		super();
		this.ocupacion = ocupacion;
		this.experiencia = experiencia;
		this.edad = edad;
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
