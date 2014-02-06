package org.hectordam.practicahector.base;

import java.io.Serializable;
import java.util.Date;

public class Batalla implements Serializable{

	private Date fechaBatalla;
	private Jugador usuarioAtacante;
	private Jugador usuarioDefensor;
	private String historial;
	private int duracion;
	
	
	public Date getFechaBatalla() {
		return fechaBatalla;
	}
	public void setFechaBatalla(Date fechaBatalla) {
		this.fechaBatalla = fechaBatalla;
	}
	public Jugador getUsuarioAtacante() {
		return usuarioAtacante;
	}
	public void setUsuarioAtacante(Jugador usuarioAtacante) {
		this.usuarioAtacante = usuarioAtacante;
	}
	public Jugador getUsuarioDefensor() {
		return usuarioDefensor;
	}
	public void setUsuarioDefensor(Jugador usuarioDefensor) {
		this.usuarioDefensor = usuarioDefensor;
	}
	public String getHistorial() {
		return historial;
	}
	public void setHistorial(String historial) {
		this.historial = historial;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
}
