package com.gosh.vigym.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity 
@Table(name = "estudiantes")
public class Estudiante {
	
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
	
	private int saldo;
	private int estatura;
	private double peso;
	private double imc;
	private double calorias_perdidas;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "estudiantesInscritos")
	private Set<Curso> cursos = new HashSet<Curso>();
	
	public Estudiante() {
		super();
	}
	public Estudiante(@NotNull String nombre, @NotNull String apellido, @NotNull String username,
			@NotNull String correo, @NotNull String password, int saldo, int estatura, double peso, double imc, 
			double calorias_perdidas, Set<Curso> cursos) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.correo = correo;
		this.password = password;
		this.saldo = saldo;
		this.estatura = estatura;
		this.peso = peso;
		this.imc = imc;
		this.calorias_perdidas = calorias_perdidas;
		this.cursos = cursos;
	}
	public Estudiante(@NotNull String nombre, @NotNull String apellido, @NotNull String username,
			@NotNull String correo, @NotNull String password) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.correo = correo;
		this.password = password;
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
	public int getSaldo() {
		return saldo;
	}
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	public int getEstatura() {
		return estatura;
	}
	public void setEstatura(int estatura) {
		this.estatura = estatura;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public double getImc() {
		return imc;
	}
	public void setImc(double imc) {
		this.imc = imc;
	}
	public double getCalorias_perdidas() {
		return calorias_perdidas;
	}
	public void setCalorias_perdidas(double calorias_perdidas) {
		this.calorias_perdidas = calorias_perdidas;
	}
	
	public Set<Curso> getCursos() {
		return cursos;
	}
	
}