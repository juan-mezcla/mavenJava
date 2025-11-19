package accesoDatos.tarea11;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ArchivoXml {
    private String nombreXml, elementoRaiz;
    private DocumentBuilderFactory instancia = DocumentBuilderFactory.newInstance();
    private DocumentBuilder builder;
    private DOMImplementation domXml;
    private Document doc;
    private Source recurso;
    private Result result;
    private Transformer creacionXml;
    private String XSL = "alumnos.xsl";
    private File directorioSalida;

    /**
     * Constructor base para crear un archivo XML.
     *
     * @param nombreXml    nombre del documento que se creará (puede incluir ruta)
     * @param elementoRaiz etiqueta principal dentro del XML.
     * @param version      versión XML (por ejemplo, "1.0").
     */
    public ArchivoXml(String nombreXml, String elementoRaiz, String version) {
        this.nombreXml = nombreXml;
        this.elementoRaiz = elementoRaiz;

  
        File posibleRuta = new File(nombreXml);
        if (posibleRuta.isAbsolute()) {
            this.directorioSalida = posibleRuta.getParentFile();
            this.nombreXml = posibleRuta.getName();
        } else {
            this.directorioSalida = new File(System.getProperty("user.dir"));
        }

        try {
            this.builder = this.instancia.newDocumentBuilder();
            this.domXml = this.builder.getDOMImplementation();
            this.doc = this.domXml.createDocument(null, this.elementoRaiz, null);
            this.doc.setXmlVersion(version);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    
    public void anadirEtiqueta(Element padre, String nomEtiqueta, String texto) {
        Element etiqueta = this.doc.createElement(nomEtiqueta);
        etiqueta.setTextContent(texto);
        padre.appendChild(etiqueta);
    }

    
    public void anadirAtributo(Element padre, String nomEtiqueta, String texto, List<Atributo> atributos) {
        Element etiqueta = this.doc.createElement(nomEtiqueta);
        etiqueta.setTextContent(texto);

        if (atributos != null) {
            for (Atributo atributo : atributos) {
                etiqueta.setAttribute(atributo.getNombre(), atributo.getValor());
            }
        }
        padre.appendChild(etiqueta);
    }

    
    public void crearXml() {
        try {
            
            File archivoXml = new File(this.directorioSalida, this.nombreXml);
            this.recurso = new DOMSource(this.doc);
            this.result = new StreamResult(archivoXml);

            this.creacionXml = TransformerFactory.newInstance().newTransformer();
            this.creacionXml.transform(this.recurso, this.result);

            System.out.println("XML creado correctamente en: " + archivoXml.getAbsolutePath());

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    
    public void crearXslHtml() {
        File xmlFile = new File(this.directorioSalida, this.nombreXml + ".xml");
        File xslFile = new File(this.XSL);
        File htmlOutput = new File(this.directorioSalida, this.nombreXml + ".html");

        Source xmlSource = new StreamSource(xmlFile);
        Source xslSource = new StreamSource(xslFile);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xslSource);
            transformer.transform(xmlSource, new StreamResult(htmlOutput));
            System.out.println("HTML generado correctamente en: " + htmlOutput.getAbsolutePath());
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    protected Document getDoc() { return doc; }
    protected String getNombreXml() { return nombreXml; }
    protected void setResult(Result result) { this.result = result; }
    protected void setRecurso(Source recurso) { this.recurso = recurso; }
    protected Source getRecurso() { return recurso; }
    protected Result getResult() { return result; }
    protected File getDirectorioSalida() { return directorioSalida; }
}
