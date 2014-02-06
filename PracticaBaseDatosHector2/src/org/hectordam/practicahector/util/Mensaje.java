package org.hectordam.practicahector.util;

import javax.swing.JOptionPane;

public class Mensaje {

	/**
	 *  muestra una ventana de error con el mensaje recibido por parametro
	 * @param mensaje
	 */
	public static void mensajeError(String mensaje){
		JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 *  muestra una ventana de error con el mensaje y titulo recibido por parametro
	 * @param mensaje
	 * @param titulo
	 */
	public static void mensajeError(String mensaje, String titulo){
		JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 *  muestra un mensaje de confirmacion con el mensaje recibido por parametro
	 * @param mensaje
	 * @return
	 */
	public static int mensajeConfirmar(String mensaje){
		return JOptionPane.showConfirmDialog(null, mensaje, "Confirmar", JOptionPane.YES_NO_OPTION);
	}
	
}
