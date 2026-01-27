/**
 * 
 */
package tarea16.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
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

import tarea16.Alumno;
import tarea16.ArchivoXml;
import tarea16.Atributo;
import tarea16.interfaces.DataBaseInterface;
import tarea16.interfaces.XmlFileInterface;

/**
 * 
 */
public class DatabaseManager implements DataBaseInterface{
	private String strCamposAlumno ="nia,nombre,apellidos,genero,fechaNac,ciclo,curso,id_grupo",
				   strCamposGrupo="nombreGrupo",
				   tablaAlumnos="alumnos",
				   tablaGrupos="grupos";
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
		String insert = "INSERT INTO "+ tablaAlumnos +" (" + strCamposAlumno + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection con=PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(insert);){

			consulta.setInt(1, alumno.getNia());
			consulta.setString(2, alumno.getNombre());
			consulta.setString(3, alumno.getApellidos());
			consulta.setString(4, alumno.getGenero());
			consulta.setString(5, alumno.getFecha().toString());
			consulta.setString(6, alumno.getCiclo());
			consulta.setString(7, alumno.getCurso());
			
			if(alumno.getGrupo().contains("-1")) {
				consulta.setInt(8,(int)(Math.random() * obtener_grupos().size()) + 1 );
			}else {
				consulta.setInt(8,Integer.valueOf(obtener_grupo_alumno(alumno).getGrupo()));
			}
			
			consulta.executeUpdate();
			consulta.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.warn(e.getMessage());  
			
		}

	}
	
	@Override
	public void crear_grupo(String nuevoGrupo) {
		String insert = "INSERT INTO "+ tablaGrupos +" (" + strCamposGrupo + ") VALUES (?)";

		try (Connection con=PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(insert);){

			consulta.setString(1, nuevoGrupo);
			

			consulta.executeUpdate();
			consulta.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.warn(e.getMessage());  
			
		}
	}
	
	
	@Override
	public List<Alumno> obtener_todos_los_alumnos() {
		String select = "Select " + strCamposAlumno + " FROM "+ tablaAlumnos +" ORDER BY id_grupo";

		List<Alumno> alumnos = new ArrayList<Alumno>();
		try (Connection con=PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(select);){

			ResultSet r = consulta.executeQuery();

			while (r.next()) {
				Alumno alumno=new Alumno(r.getInt(1), r.getString(2), r.getString(3), r.getString(4).toCharArray()[0],
							  LocalDate.parse(r.getString(5)), r.getString(6), r.getString(7),String.valueOf(r.getInt(8)));
				
				
				alumnos.add(obtener_grupo_alumno(alumno));
			}
			
			r.close(); 
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.error(e.getMessage());  
		}
		return alumnos;
	}
	
	
	@Override
	public Alumno obtener_grupo_alumno(Alumno alumno) {
	    String sql = "SELECT idgrupos, " + strCamposGrupo + " FROM " + tablaGrupos + " WHERE idgrupos=? OR nombreGrupo=?";
	    
	    try (Connection con = PoolConexion.getConnection();
	         PreparedStatement consulta = con.prepareStatement(sql)) {

	        
	        boolean esNumero = alumno.getGrupo() != null && alumno.getGrupo().matches("^\\d+$");

	        
	        if (esNumero) {
	            consulta.setInt(1, Integer.parseInt(alumno.getGrupo()));
	            consulta.setString(2, ""); 
	        } else {
	            consulta.setInt(1, -1); 
	            consulta.setString(2, alumno.getGrupo());
	        }

	        try (ResultSet r = consulta.executeQuery()) {
	            if (r.next()) {
	                
	                if (esNumero) {
	                    
	                    alumno.setGrupo(r.getString(2)); 
	                } else {
	                  
	                    alumno.setGrupo(String.valueOf(r.getInt(1))); 
	                }
	            }
	        }
	        
	    } catch (SQLException e) {
	        log.warn("Error al obtener grupo: " + e.getMessage());
	    }
	    
	    return alumno;
	}
	
	
	
	@Override
	public List<Integer> obtener_grupos() {
		String insert = "SELECT idgrupos FROM "+ tablaGrupos;
		List<Integer> id_grupos=new ArrayList<Integer>();
		try (Connection con=PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(insert);){
			
			ResultSet r = consulta.executeQuery();
			
			while(r.next()) {
				id_grupos.add(r.getInt(1));
			}
			
			r.close();
			consulta.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.warn(e.getMessage());  
			
		}
		
		return id_grupos;
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
		String update = "UPDATE "+ tablaAlumnos +" SET nombre =? WHERE nia=?";

		try (Connection con=PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(update);){
			
			consulta.setString(1, nombre);
			consulta.setString(2,String.valueOf(nia));
			
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
		String delete = "DELETE FROM "+ tablaAlumnos +" WHERE nia =?";

		try(Connection con=PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(delete);) {
			consulta.setString(1,String.valueOf(nia));
			consulta.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());  
			
		}
		
	}
	@Override
	public void eliminar_Alumno_por_Apellido(String apellido) {
		String delete = "DELETE FROM "+ tablaAlumnos +" WHERE Apellidos =?";

		try (Connection con=PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(delete);){
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
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
				
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
