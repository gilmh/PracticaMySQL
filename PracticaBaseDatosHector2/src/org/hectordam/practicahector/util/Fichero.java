package org.hectordam.practicahector.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hectordam.practicahector.base.Batalla;
import org.hectordam.practicahector.base.Jugador;
import org.hectordam.practicahector.base.Personaje;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;


public class Fichero {
	
	/**
	 *  escribe en un archivo con la ruta recibida el vector recibido
	 * @param objeto
	 * @param ruta
	 * @throws IOException
	 */
	public static void escribirFichero(Object objeto, String ruta) throws IOException{
		
		FileOutputStream fichero = new FileOutputStream(ruta);
		ObjectOutputStream serializador = new ObjectOutputStream(fichero);
		
		serializador.writeObject(objeto);
		
		if(serializador != null){
			serializador.close();
		}
	}
	
	/**
	 *  copia el fichero con la ruta recibida y lo guarda en un objeto y lo devuelve
	 * @param ruta
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public static Object leerFichero(String ruta) throws IOException, ClassNotFoundException, FileNotFoundException{
	
		FileInputStream fichero = new FileInputStream(ruta);
		
		ObjectInputStream serializador = new ObjectInputStream(fichero);
		
		Object objeto = serializador.readObject();
		
		if(serializador != null){
			serializador.close();
		}
		
		return objeto;
	}
	
	/**
	 *  crea el XML de los jugadores
	 * @param listaDatos
	 * @param ruta
	 */
	public static void jugadorXML(ArrayList<Jugador> listaDatos, String ruta) {
		
		DateFormat formateadorFechas = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document documento = null;
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation dom = builder.getDOMImplementation();
			documento = dom.createDocument(null,  "xml", null);
			
			Element raiz = documento.createElement("jugadores");
			documento.getDocumentElement().appendChild(raiz);
			
			Element nodoEquipo = null, nodoDatos = null;
			
			Text texto = null;
			for (Jugador jugador : listaDatos) {
				nodoEquipo = documento.createElement("jugador");
				raiz.appendChild(nodoEquipo);
				
				nodoDatos = documento.createElement("usuario");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(jugador.getUsuario());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("contrasena");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(jugador.getContrasena());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("correo_electronico");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(jugador.getCorreoElectronico());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("pais");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(jugador.getPais());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("fecha_nacimiento");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(formateadorFechas.format(jugador.getFechaNacimiento()));
				nodoDatos.appendChild(texto);
			}
			
			Source source = new DOMSource(documento);
			Result resultado = new StreamResult(new File(ruta));
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, resultado);
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		}
	}
	
	/**
	 * crea el XML de los personajes
	 * @param listaDatos
	 * @param ruta
	 */
	public static void personajeXML(ArrayList<Personaje> listaDatos, String ruta) {
		
		DateFormat formateadorFechas = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document documento = null;
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation dom = builder.getDOMImplementation();
			documento = dom.createDocument(null,  "xml", null);
			
			Element raiz = documento.createElement("personajes");
			documento.getDocumentElement().appendChild(raiz);
			
			Element nodoEquipo = null, nodoDatos = null;
			
			Text texto = null;
			for (Personaje personaje : listaDatos) {
				nodoEquipo = documento.createElement("personaje");
				raiz.appendChild(nodoEquipo);
				
				nodoDatos = documento.createElement("nombre");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(personaje.getNombre());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("fecha_creacion");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(formateadorFechas.format(personaje.getFechaCreacion()));
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("clase");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(personaje.getClase());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("raza");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(personaje.getRaza());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("usuario");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(personaje.getUsuario().getUsuario());
				nodoDatos.appendChild(texto);
			}
			
			Source source = new DOMSource(documento);
			Result resultado = new StreamResult(new File(ruta));
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, resultado);
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		}
	}
	
	/**
	 *  crea el XML de las batallas
	 * @param listaDatos
	 * @param ruta
	 */
	public static void batallaXML(ArrayList<Batalla> listaDatos, String ruta) {
	
		DateFormat formateadorFechas = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document documento = null;
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation dom = builder.getDOMImplementation();
			documento = dom.createDocument(null,  "xml", null);
			
			Element raiz = documento.createElement("batallas");
			documento.getDocumentElement().appendChild(raiz);
			
			Element nodoEquipo = null, nodoDatos = null;
			
			Text texto = null;
			for (Batalla batalla : listaDatos) {
				nodoEquipo = documento.createElement("batalla");
				raiz.appendChild(nodoEquipo);
				
				nodoDatos = documento.createElement("fecha");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(formateadorFechas.format(batalla.getFechaBatalla()));
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("duracion");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(Integer.toString(batalla.getDuracion()));
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("historial");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(batalla.getHistorial());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("atacante");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(batalla.getUsuarioAtacante().getUsuario());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("defensor");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(batalla.getUsuarioDefensor().getUsuario());
				nodoDatos.appendChild(texto);
			}
			
			Source source = new DOMSource(documento);
			Result resultado = new StreamResult(new File(ruta));
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, resultado);
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		}
	}
}
