package org.hectordam.practicahector.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Conecsion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private Connection conexion;
	private String nivel;
	private ResultSet resultado;

	private boolean esCorrecto = false;
	
	
	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public void mostrarConecsion(Connection conexion){
		
		this.setVisible(true);
		
		try {
			
			Statement sentencia = conexion.createStatement();
			String consulta = "select nivel from login where usuario = '" + this.textField.getText() + "' and contrasena = '" + this.textField_1.getText() + "'";
			resultado = sentencia.executeQuery(consulta);
			
			if (resultado.first())
				
				this.nivel = resultado.getString(1);
			else{
				
				this.nivel = "";
			}
			if (sentencia != null)
				sentencia.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void aceptar(){
		
		this.setVisible(false);
		
	}
	
	
	private void cancelar(){
		
		this.setVisible(false);
	}
	
	public boolean estado(){
		
		return esCorrecto;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Conecsion dialog = new Conecsion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Conecsion() {
		
		setModal(true);
		
		setBounds(100, 100, 315, 223);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUsuario = new JLabel("Usuario");
			lblUsuario.setBounds(133, 40, 46, 14);
			contentPanel.add(lblUsuario);
		}
		{
			textField = new JTextField();
			textField.setBounds(91, 65, 122, 20);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JLabel lblContrasea = new JLabel("Contrase\u00F1a");
			lblContrasea.setBounds(123, 96, 56, 14);
			contentPanel.add(lblContrasea);
		}
		{
			textField_1 = new JTextField();
			textField_1.setBounds(91, 121, 122, 20);
			contentPanel.add(textField_1);
			textField_1.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						aceptar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelar();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
