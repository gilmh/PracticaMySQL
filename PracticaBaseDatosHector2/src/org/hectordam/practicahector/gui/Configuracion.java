package org.hectordam.practicahector.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Configuracion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblPreferencias;
	private JComboBox comboBox;
	private JTextField txtServidor;
	private JLabel lblServidor;
	private JTextField txtPuerto;
	private JLabel lblPuerto;
	private JLabel lblBaseDeDatos;
	private JTextField txtBaseDatos;
	private JLabel lblUsuario;
	private JTextField txtUsuarios;
	private JLabel lblContrasea;
	private JTextField txtContrasena;

	
	
	public void mostrarConfiguracion(){
		setVisible(true);
		
		Properties configuracion = new Properties();
		
		try {
			configuracion.load(new FileInputStream("preferencias"));
			
			this.comboBox.setSelectedItem(configuracion.getProperty("driver"));
			this.txtServidor.setText(configuracion.getProperty("servidor"));
			this.txtPuerto.setText(configuracion.getProperty("puerto"));
			this.txtBaseDatos.setText(configuracion.getProperty("baseDatos"));
			this.txtUsuarios.setText(configuracion.getProperty("usuario"));
			this.txtContrasena.setText(configuracion.getProperty("contrasena"));
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void aceptar(){
		
		Properties configuracion = new Properties();
		
		configuracion.setProperty("driver", this.comboBox.getSelectedItem().toString());
		configuracion.setProperty("servidor", this.txtServidor.getText());
		configuracion.setProperty("puerto", this.txtPuerto.getText());
		configuracion.setProperty("baseDatos", this.txtBaseDatos.getText());
		configuracion.setProperty("usuario", this.txtUsuarios.getText());
		configuracion.setProperty("contrasena", this.txtContrasena.getText());
		
		try {
			configuracion.store(new FileOutputStream("preferencias"), "Fichero de propiedades");
			setVisible(false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void cancelar(){
		
		setVisible(false);
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Configuracion dialog = new Configuracion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Configuracion() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblPreferencias = new JLabel("PREFERENCIAS");
		lblPreferencias.setBounds(170, 11, 83, 14);
		contentPanel.add(lblPreferencias);
		
		comboBox = new JComboBox();
		comboBox.setBounds(60, 40, 121, 22);
		contentPanel.add(comboBox);
		comboBox.addItem("Mysql");
		comboBox.addItem("Postgre");
		
		txtServidor = new JTextField();
		txtServidor.setBounds(60, 98, 121, 20);
		contentPanel.add(txtServidor);
		txtServidor.setColumns(10);
		
		lblServidor = new JLabel("Servidor");
		lblServidor.setBounds(60, 73, 46, 14);
		contentPanel.add(lblServidor);
		
		txtPuerto = new JTextField();
		txtPuerto.setBounds(191, 98, 86, 20);
		contentPanel.add(txtPuerto);
		txtPuerto.setColumns(10);
		
		lblPuerto = new JLabel("Puerto");
		lblPuerto.setBounds(191, 73, 46, 14);
		contentPanel.add(lblPuerto);
		
		lblBaseDeDatos = new JLabel("Base de datos");
		lblBaseDeDatos.setBounds(60, 129, 83, 14);
		contentPanel.add(lblBaseDeDatos);
		
		txtBaseDatos = new JTextField();
		txtBaseDatos.setBounds(60, 154, 86, 20);
		contentPanel.add(txtBaseDatos);
		txtBaseDatos.setColumns(10);
		
		lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(60, 185, 46, 14);
		contentPanel.add(lblUsuario);
		
		txtUsuarios = new JTextField();
		txtUsuarios.setBounds(60, 210, 86, 20);
		contentPanel.add(txtUsuarios);
		txtUsuarios.setColumns(10);
		
		lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(191, 185, 62, 14);
		contentPanel.add(lblContrasea);
		
		txtContrasena = new JTextField();
		txtContrasena.setBounds(191, 210, 86, 20);
		contentPanel.add(txtContrasena);
		txtContrasena.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
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
