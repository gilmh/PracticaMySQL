package org.hectordam.practicahector.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;

import org.hectordam.practicahector.base.Jugador;

public class ComboJugadores extends JComboBox<String>{

	private Connection conexion;
	
	/**
	 *  crea el vector
	 */
	public ComboJugadores(){
		super();
	}
	
	public void conectar(Connection conectar){
		
		this.conexion = conectar;
	}
	
	/**
	 *  lista los datos del vector en el combobox
	 * @throws SQLException 
	 */
	public void listar() throws SQLException{
		
		if (conexion == null){
			this.removeAllItems();
			return;
		}
		if (conexion.isClosed()){
			this.removeAllItems();
			return;
		}
		this.removeAllItems();
		
		Statement sentencia = this.conexion.createStatement();
		String consulta = "select * from usuarios";
		ResultSet resultado = sentencia.executeQuery(consulta);	
		
		
		while (resultado.next()) {
			this.addItem(resultado.getString(2));
		}
		
		if (sentencia != null)
			sentencia.close();
		
	}
	
	/**
	 *  devuelve el equipo seleccionado del comobox
	 * @return
	 * @throws SQLException 
	 */
	public int jugadorSeleccionadoId(Connection conectar) throws SQLException{
		
		// si el combobox esta vacio devuelve null
		if(this.getSelectedIndex() == -1){
			return -1;
		}
		String nombre = (String) getSelectedItem();
		Statement sentencia = conexion.createStatement();
		String consulta = "select id from usuarios where nombre = '" + nombre+"'";
		ResultSet resultado = sentencia.executeQuery(consulta);
		
		int num = -1;
		if(resultado.first()){
			num = resultado.getInt(1);
		}
		
		if (sentencia != null)
			sentencia.close();
		return num;
	}
	
	public String mostrarNombre(int id, Connection conexion) throws SQLException{
		
		// si el combobox esta vacio devuelve null
		if(this.getSelectedIndex() == -1){
			return null;
		}
		
		Statement sentencia = conexion.createStatement();
		String consulta = "select nombre from usuarios where id = " + id;
		ResultSet resultado = sentencia.executeQuery(consulta);
		
		String nom = null;
		if(resultado.first()){
			nom = resultado.getString(1);
		}
		
		if (sentencia != null)
			sentencia.close();

		return nom;
	}
}
