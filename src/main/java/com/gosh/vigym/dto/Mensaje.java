package com.gosh.vigym.dto;

public class Mensaje { //mostrar mensajes por pantalla en el cliente
	private String mensaje;

	public Mensaje(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
