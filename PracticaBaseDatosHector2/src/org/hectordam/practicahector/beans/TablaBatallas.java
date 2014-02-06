package org.hectordam.practicahector.beans;

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

import org.hectordam.practicahector.base.Batalla;

public class TablaBatallas extends JTable{

	private Connection conexion;
	private DefaultTableModel modelo;
	
	public TablaBatallas(){
		super();
		
		inicializar();
	}
	
	/**
	 *  crea la tabla con una cabecera y asigna los datos del vector recibido por parametro al vector
	 * @param lista
	 */
	public void inicializar(){
		
		String[] columna = {"duracion", "historial"};
		
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
		String consulta = "select * from batallas";
		ResultSet resultado = sentencia.executeQuery(consulta);
		
		modelo.setNumRows(0);
		
		while (resultado.next()) {
			Object[] fila = new Object[]{ resultado.getString(3), resultado.getString(4)};
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
		ResultSet resultado = sentencia.executeQuery("select * from batallas where historial = '" + filtro + "'");
		
		modelo.setNumRows(0);
		
		while (resultado.next()) {
			Object[] fila = new Object[]{resultado.getInt(3), resultado.getString(4)};
			modelo.addRow(fila);
		}
		
		if (sentencia != null)
			sentencia.close();
		
	}
	
	public void insertar(boolean esNuevoBatalla, Batalla batalla, String historial, int idAtacante, int idDefensor){
		DateFormat formateadorFechas = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
		
		if(esNuevoBatalla){
			
			try {
				conexion.setAutoCommit(false);
				
				String sentenciaSql = "INSERT INTO batallas (fecha, duracion, historial) VALUES (?, ?, ?)";
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
				sentencia.setString(1, formateadorFechas.format(batalla.getFechaBatalla()));
				sentencia.setInt(2, batalla.getDuracion());
				sentencia.setString(3, batalla.getHistorial());
				sentencia.executeUpdate();
				
				if (sentencia != null)
					sentencia.close();
				
				sentenciaSql = "INSERT INTO usuarios_batallas (id_usuario, id_batalla) VALUES (?, ?)";
				sentencia = conexion.prepareStatement(sentenciaSql);
				sentencia.setInt(1, idAtacante);
				sentencia.setInt(2, this.batallaId(batalla.getHistorial()));
				sentencia.executeUpdate();
				
				if (sentencia != null)
					sentencia.close();
				
				sentenciaSql = "INSERT INTO usuarios_batallas (id_usuario, id_batalla) VALUES (?, ?)";
				sentencia = conexion.prepareStatement(sentenciaSql);
				sentencia.setInt(1, idDefensor);
				sentencia.setInt(2, this.batallaId(batalla.getHistorial()));
				sentencia.executeUpdate();
				
				if (sentencia != null)
					sentencia.close();
				
				conexion.commit();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		// En el caso de que la batalla sea para modificar
		else{
			
			String sentenciaSql = "UPDATE batallas SET fecha = ?, duracion = ?, historial = ? WHERE historial = ?";
			try {
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
				sentencia.setString(1, formateadorFechas.format(batalla.getFechaBatalla()));
				sentencia.setInt(2, batalla.getDuracion());
				sentencia.setString(3, batalla.getHistorial());
				sentencia.setString(4, historial);
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
		
		String nombreSeleccionado = (String) getValueAt(filaSeleccionada, 1);
		String sentenciaSql = "DELETE FROM batallas WHERE historial = ?";
		
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
		
		String nombreSeleccionado = (String) getValueAt(filaSeleccionada, 1);
		return nombreSeleccionado;
	}
	
	public int batallaId(String historial) throws SQLException{
		
		Statement sentencia = this.conexion.createStatement();
		ResultSet resultado = sentencia.executeQuery("select id from batallas where historial = '" + historial + "'");
		
		int id = -1;
		if(resultado.first()){
			id = resultado.getInt(1);
		}
		
		return id;
	}
}
