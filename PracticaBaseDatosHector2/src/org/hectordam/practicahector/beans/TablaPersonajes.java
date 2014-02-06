package org.hectordam.practicahector.beans;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hectordam.practicahector.base.Jugador;
import org.hectordam.practicahector.base.Personaje;

public class TablaPersonajes extends JTable{

	private Connection conexion;
	private DefaultTableModel modelo;
	
	public TablaPersonajes(){
		super();
		
		inicializar();
	}
	
	/**
	 *  crea la tabla con una cabecera y asigna los datos del vector recibido por parametro al vector
	 * @param lista
	 */
	public void inicializar(){
		
		String[] columna = {"Nombre", "Clase", "Raza"};
		
		modelo = new DefaultTableModel(columna, 0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.setModel(modelo);
		
	}
	
	public void conectar(Connection conectar){
		
		this.conexion = conectar;
	}
	
	/**
	 *  rellena la tabla
	 * @throws SQLException 
	 */
	public void listar() throws SQLException{
		
		if(this.conexion == null){	
			modelo.setNumRows(0);
			return;
		}
		if(this.conexion.isClosed()){	
			modelo.setNumRows(0);
			return;
		}
		
		Statement sentencia = this.conexion.createStatement();
		String consulta = "select * from personajes";
		ResultSet resultado = sentencia.executeQuery(consulta);
		
		modelo.setNumRows(0);
		
		while (resultado.next()) {
			Object[] fila = new Object[]{resultado.getString(2), resultado.getString(4), resultado.getString(5)};
			modelo.addRow(fila);
		}
		
		if (sentencia != null)
			sentencia.close();
	}
	
	/**
	 *  rellena la tabla comparando con el filtro
	 * @param filtro
	 * @throws SQLException 
	 */
	public void listaFiltrada(String filtro) throws SQLException{
		
		if(this.conexion == null){	
			modelo.setNumRows(0);
			return;
		}
		if(this.conexion.isClosed()){	
			modelo.setNumRows(0);
			return;
		}
		
		Statement sentencia = this.conexion.createStatement();
		String consulta = "select * from personajes where nombre like '%" + filtro + "%'";
		ResultSet resultado = sentencia.executeQuery(consulta);
		
		modelo.setNumRows(0);
		
		while (resultado.next()) {
			Object[] fila = new Object[]{resultado.getString(2), resultado.getString(4), resultado.getString(5)};
			modelo.addRow(fila);
		}
		
		if (sentencia != null)
			sentencia.close();
		
	}
	
	public void insertar(boolean esNuevoPersonaje, Personaje personaje, String nombre, int id){
		DateFormat formateadorFechas = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
		
		if(esNuevoPersonaje){
			
			try {
				String sentenciaSql = "INSERT INTO personajes (nombre, fecha, clase, raza, id_usuario) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
				sentencia.setString(1, personaje.getNombre());
				sentencia.setString(2, formateadorFechas.format(personaje.getFechaCreacion()));
				sentencia.setString(3, personaje.getClase());
				sentencia.setString(4, personaje.getRaza());
				sentencia.setInt(5, id);
				sentencia.executeUpdate();
				
				if (sentencia != null)
					sentencia.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		// En el caso de que el personaje sea para modificar
		else{
			
			String sentenciaSql = "UPDATE personajes SET nombre = ?, fecha = ?, clase = ?, raza = ?, id_usuario = ? WHERE nombre = ?";
			try {
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
				sentencia.setString(1, personaje.getNombre());
				sentencia.setString(2, formateadorFechas.format(personaje.getFechaCreacion()));
				sentencia.setString(3, personaje.getClase());
				sentencia.setString(4, personaje.getRaza());
				sentencia.setInt(5, id);
				sentencia.setString(6, nombre);
				sentencia.executeUpdate();
				
				if (sentencia != null)
					sentencia.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		
	}
	
	public void eliminar() throws SQLException {
		
		int filaSeleccionada = 0;
		
		filaSeleccionada = getSelectedRow();
		if (filaSeleccionada == -1) 
			return;
		
		
		String nombreSeleccionado = (String) getValueAt(filaSeleccionada, 0);
		String sentenciaSql = "DELETE FROM personajes WHERE nombre = ?";
		
		PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setString(1, nombreSeleccionado);
		sentencia.executeUpdate();
		
		if (sentencia != null)
				sentencia.close();
		
	}
	
	public String filaSeleccionada(){
		
		int filaSeleccionada = 0;
		
		filaSeleccionada = getSelectedRow();
		if (filaSeleccionada == -1) 
			return null;
		
		String nombreSeleccionado = (String) getValueAt(filaSeleccionada, 0);
		return nombreSeleccionado;
	}
}
