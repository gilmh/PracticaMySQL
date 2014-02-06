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

public class TablaJugadores extends JTable{

	private Connection conexion;
	private DefaultTableModel modelo;
	
	public TablaJugadores(){
		super();
		
		inicializar();
	}
	
	/**
	 *  crea la tabla con una cabecera y asigna los datos del vector recibido por parametro al vector
	 * @param lista
	 */
	public void inicializar(){
		
		String[] columnas = {"Usuario", "Correo Electronico", "Pais"};
		
		modelo = new DefaultTableModel(columnas, 0){
			@Override
			public boolean isCellEditable(int fila, int columna) {
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
		
		if (conexion == null){
			this.modelo.setNumRows(0);
			return;
		}
		if (conexion.isClosed()){
			this.modelo.setNumRows(0);
			return;
		}
		
		Statement sentencia = this.conexion.createStatement();
		String consulta = "select * from usuarios";
		ResultSet resultado = sentencia.executeQuery(consulta);	
		
		modelo.setNumRows(0);
		
		while (resultado.next()) {
			Object[] fila = new Object[]{resultado.getString(2), resultado.getString(3), resultado.getString(4)};
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
	public void listafiltrada(String filtro) throws SQLException{
		
		if (conexion == null)
			return;
		
		if (conexion.isClosed())
			return;
		
		
		Statement sentencia = this.conexion.createStatement();
		String consulta = "select * from usuarios where nombre like '%" + filtro + "%'";
		ResultSet resultado = sentencia.executeQuery(consulta);	
		
		modelo.setNumRows(0);
		
		while (resultado.next()) {
			Object[] fila = new Object[]{resultado.getString(2), resultado.getString(3), resultado.getString(4)};
			modelo.addRow(fila);
		}
		
		if (sentencia != null)
			sentencia.close();
	}
	
	public void insertar(boolean esNuevoJugador, Jugador jugador, String usuario){
		DateFormat formateadorFechas = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
		
		// en el caso de que el jugador sea nuevo
		if(esNuevoJugador){
			try {
				String sentenciaSql = "INSERT INTO usuarios (nombre, correo, pais, fecha_nacimiento, contrasena) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
				sentencia.setString(1, jugador.getUsuario());
				sentencia.setString(2, jugador.getCorreoElectronico());
				sentencia.setString(3, jugador.getPais());
				sentencia.setString(4, formateadorFechas.format(jugador.getFechaNacimiento()));
				sentencia.setString(5, jugador.getContrasena());
				sentencia.executeUpdate();
				
				if (sentencia != null)
					sentencia.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		// en el caso de que el jugador sea para modificar
		else{
			
			String sentenciaSql = "UPDATE usuarios SET nombre = ?, correo = ?, pais = ?, fecha_nacimiento = ?, contrasena = ? WHERE nombre = ?";
			try {
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
				sentencia.setString(1, jugador.getUsuario());
				sentencia.setString(2, jugador.getCorreoElectronico());
				sentencia.setString(3, jugador.getPais());
				sentencia.setString(4, formateadorFechas.format(jugador.getFechaNacimiento()));
				sentencia.setString(5, jugador.getContrasena());
				sentencia.setString(6, usuario);
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
		CallableStatement procedimiento = conexion.prepareCall("call elimina_usuario ( ? )");
		procedimiento.setString(1, nombreSeleccionado);
		procedimiento.execute();
		
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
