/**
 * 
 */
package tarea15.interfaces;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tarea15.Alumno;
import tarea15.Atributo;

/**
 * 
 */
public interface XmlFileInterface {
	public void anadirEtiqueta(Element padre, String nomEtiqueta, String texto);

	public void anadirAtributo(Element padre, String nomEtiqueta, String texto, List<Atributo> atributos);

	public void crearXml();

	public void crearXslHtml();

	public List<Alumno> leerXml(String ruta) ;
	public Document getDoc();
}
