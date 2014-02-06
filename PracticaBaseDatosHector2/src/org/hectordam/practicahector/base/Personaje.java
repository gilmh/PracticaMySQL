package org.hectordam.practicahector.base;

import java.io.Serializable;
import java.util.Date;

public class Personaje implements Serializable{

	private String nombre;
	private Date fechaCreacion;
	private String clase;
	private String raza;
	private Jugador usuario;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getRaza() {
		return raza;
	}
	public void setRaza(String raza) {
		this.raza = raza;
	}
	public Jugador getUsuario() {
		return usuario;
	}
	public void setUsuario(Jugador usuario) {
		this.usuario = usuario;
	}
	
}
