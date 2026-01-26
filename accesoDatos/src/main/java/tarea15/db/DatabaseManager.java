/**
 * 
 */
package tarea15.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import tarea15.Alumno;
import tarea15.ArchivoXml;
import tarea15.Atributo;
import tarea15.interfaces.DataBaseInterface;
import tarea15.interfaces.XmlFileInterface;

/**
 * 
 */
public class DatabaseManager implements DataBaseInterface{
	private String strCampos ="NIA,Nombre,Apellidos,Genero,FechaNacimiento,Ciclo,Curso,Grupo";// String.join(",", campos);
	static DateTimeFormatter formatoFech=DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private static final Logger log =
			LoggerFactory.getLogger(DatabaseManager.class);
	
	/**
	 * 
	 */
	public DatabaseManager() {
		

	}
	@Override
	public void insertar_Alumno(Alumno alumno) {
		String insert = "INSERT INTO alumno (" + strCampos + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement consulta = PoolConexion.getConnection().prepareStatement(insert);){

			consulta.setInt(1, alumno.getNia());
			consulta.setString(2, alumno.getNombre());
			consulta.setString(3, alumno.getApellidos());
			consulta.setString(4, alumno.getGenero());
			consulta.setString(5, alumno.getFecha().toString());
			consulta.setString(6, alumno.getCiclo());
			consulta.setString(7, alumno.getCurso());
			consulta.setString(8, alumno.getGrupo());

			consulta.executeUpdate();
			consulta.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.warn(e.getMessage());  
			
		}

	}
	@Override
	public List<Alumno> obtener_todos_los_alumnos() {
		String select = "Select " + strCampos + " FROM alumno";

		List<Alumno> alumnos = new ArrayList<Alumno>();
		try (PreparedStatement consulta = PoolConexion.getConnection().prepareStatement(select);){

			ResultSet r = consulta.executeQuery();

			while (r.next()) {
				alumnos.add(new Alumno(r.getInt(1), r.getString(2), r.getString(3), r.getString(4).toCharArray()[0],
						LocalDate.parse(r.getString(5)), r.getString(6), r.getString(7), r.getString(8)));
			}
			
			r.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.error(e.getMessage());  
		}
		return alumnos;
	}
	@Override
	public void guardar_Datos_En_Fichero(String rutaFichero) {
		File arch = comprobarFichero(rutaFichero);

		try (FileOutputStream escribir = new FileOutputStream(arch, false)) {
			List<Alumno> alumnos = obtener_todos_los_alumnos();

			ObjectOutputStream escribirAlumno = new ObjectOutputStream(escribir);

			alumnos.forEach(alumno -> {

				try {
					escribirAlumno.writeObject(alumno);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			escribirAlumno.close();
			escribir.flush();
			escribir.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			log.error(e.getMessage());  
		}

		
	}
	@Override
	public void mandar_Datos_De_Fichero_A_BD(String rutaArchAlumnos) {
		File arch = comprobarFichero(rutaArchAlumnos);
		
		try (FileInputStream leer=new FileInputStream(arch)){
			
			ObjectInputStream objArch=new ObjectInputStream(leer);
			
			while(leer.available()>0) {
				Alumno alumno=(Alumno) objArch.readObject();
				this.insertar_Alumno(alumno);
			}
		
			leer.close();
			objArch.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());  
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			log.error(e.getMessage());  
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());  
			
		}
		
	}
	@Override
	public void actualizar_Alumno_por_NIA(int nia,String nombre) {
		String update = "UPDATE alumno SET nombre =? WHERE NIA=?";

		try (PreparedStatement consulta = PoolConexion.getConnection().prepareStatement(update);){
			
			consulta.setString(1, nombre);
			consulta.setInt(2, nia);
			
			consulta.execute();
			consulta.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());  
		}
		
	}
	
	private File comprobarFichero(String ruta) {
		File arch = new File(ruta);

		if (!arch.exists()) {
			try {
				arch.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());  
			}
		}

		if (!arch.isAbsolute()) {
			arch = new File(System.getProperty("user.dir"), ruta);
		}
		
		return arch;
	}
	@Override
	public void eliminar_Alumno_por_NIA(int nia) {
		String delete = "DELETE FROM alumno WHERE NIA =?";

		try(PreparedStatement consulta = PoolConexion.getConnection().prepareStatement(delete);) {
			consulta.setInt(1, nia);
			consulta.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());  
			
		}
		
	}
	@Override
	public void eliminar_Alumno_por_Apellido(String apellido) {
		String delete = "DELETE FROM alumno WHERE Apellidos =?";

		try (PreparedStatement consulta = PoolConexion.getConnection().prepareStatement(delete);){
			consulta.setString(1, apellido);
			consulta.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());  
		}
		
	}
	@Override
	public void guardar_Datos_En_Xml(String ruta) {
		
		XmlFileInterface xml=new ArchivoXml(ruta, "alumnos", "1.0");
		
		List<Alumno> alumnos=this.obtener_todos_los_alumnos();
		List<Atributo> atributosAlumno=new ArrayList<Atributo>();
		
		Document doc=xml.getDoc();
		alumnos.forEach(alumno->{
			
			atributosAlumno.add(new Atributo("nia",Integer.toString(alumno.getNia())));
			atributosAlumno.add(new Atributo("nombre",alumno.getNombre()));
			atributosAlumno.add(new Atributo("apellidos",alumno.getApellidos()));
			atributosAlumno.add(new Atributo("genero",String.valueOf(alumno.getGenero())));
			atributosAlumno.add(new Atributo("fechaNacimiento",alumno.getFecha().toString()));
			atributosAlumno.add(new Atributo("ciclo",alumno.getCiclo()));
			atributosAlumno.add(new Atributo("curso",alumno.getCurso()));
			atributosAlumno.add(new Atributo("grupo",alumno.getGrupo()));
			
			
			xml.anadirAtributo(doc.getDocumentElement(),"alumno",alumno.getNombre(), atributosAlumno);
		});
		
		xml.crearXml();
	}
	@Override
	public boolean guardar_Datos_En_Json(String ruta) {
		File arch=comprobarFichero(ruta);
		List<Alumno> alumnos=this.obtener_todos_los_alumnos();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			
				
			mapper.writeValue(arch, alumnos);
			
			return true;
			 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());  
			return false;
		}
	}
	@Override
	public void leer_Datos_En_Xml(String ruta) {
		XmlFileInterface xml=new ArchivoXml();
		
		xml.leerXml(ruta);	
	}
	@Override
	public boolean leer_Datos_En_Json(String ruta) {
		File arch=comprobarFichero(ruta);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
		try {
			List<Alumno> alumnos = mapper.readValue(arch,new TypeReference<List<Alumno>>() {});
			alumnos.forEach(alumno->{
				this.insertar_Alumno(alumno);
			});
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());  
			return false;
		}
		
	}

	

	

}
