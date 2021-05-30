package com.gosh.vigym.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CursoDto {
	
	@NotBlank
	private String nombre;
	
	@NotBlank
	private String descripcion;
	
	@Min(3) //minimo 3 sesiones
	private Integer sesiones;
	
	@Min(5) //minimos 5 alumnos
	private Integer capacidad;
	
	private Double calorias_perdidas;
	
	public CursoDto() {
		super();
	}
	public CursoDto(@NotBlank String nombre, @NotBlank String descripcion, @Min(3) Integer sesiones,
			@Min(5) Integer capacidad, Double calorias_perdidas) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.sesiones = sesiones;
		this.capacidad = capacidad;
		this.calorias_perdidas = calorias_perdidas;
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
	public Integer getSesiones() {
		return sesiones;
	}
	public void setSesiones(Integer sesiones) {
		this.sesiones = sesiones;
	}
	public Integer getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	public Double getCalorias_perdidas() {
		return calorias_perdidas;
	}
	public void setCalorias_perdidas(Double calorias_perdidas) {
		this.calorias_perdidas = calorias_perdidas;
	}


}
