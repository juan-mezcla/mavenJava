/**
 * 
 */
package tarea14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * 
 */
public class DatabaseManager implements DataBaseInterface{
	private String url ="jdbc:mysql://localhost:3306/tarea11?useSSL=false&serverTimezone=UTC", user = "root",
			pass = "manager";//RUTA DE CLASE->"jdbc:mysql://localhost:3306/acceso_a_datos?useSSL=false&serverTimezone=UTC" RUTA DE CASA->"jdbc:mysql://localhost:3306/tarea11?useSSL=false&serverTimezone=UTC"
	private Connection conexion;
	//private List<String> campos = List.of("NIA", "Nombre", "Apellidos", "Genero", "FechaNacimiento", "Ciclo", "Curso","Grupo");//No se por que no me funciona ahora el .of (problema de version jdk se supone)
	private String strCampos ="NIA,Nombre,Apellidos,Genero,FechaNacimiento,Ciclo,Curso,Grupo";// String.join(",", campos);
	static DateTimeFormatter formatoFech=DateTimeFormatter.ofPattern("dd/MM/yyyy");
	/**
	 * 
	 */
	public DatabaseManager() {
		try {
			conexion = DriverManager.getConnection(url, user, pass);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	@Override
	public void insertar_Alumno(Alumno alumno) {
		String insert = "INSERT INTO alumno (" + strCampos + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement consulta = this.getConexion().prepareStatement(insert);// para evitar inyecciones de
																						// codigo.

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
			e.printStackTrace();
			
			
		}

	}
	@Override
	public List<Alumno> obtener_todos_los_alumnos() {
		String select = "Select " + strCampos + " FROM alumno";

		List<Alumno> alumnos = new ArrayList<Alumno>();
		try {
			PreparedStatement consulta = this.getConexion().prepareStatement(select);

			ResultSet r = consulta.executeQuery();

			while (r.next()) {
				alumnos.add(new Alumno(r.getInt(1), r.getString(2), r.getString(3), r.getString(4).toCharArray()[0],
						LocalDate.parse(r.getString(5)), r.getString(6), r.getString(7), r.getString(8)));
			}
			
			r.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			e.printStackTrace();
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
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	@Override
	public void actualizar_Alumno_por_NIA(int nia,String nombre) {
		String update = "UPDATE alumno SET nombre =? WHERE NIA=?";

		try {
			PreparedStatement consulta = conexion.prepareStatement(update);
			consulta.setString(1, nombre);
			consulta.setInt(2, nia);
			
			consulta.execute();
			consulta.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
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

		try {
			PreparedStatement consulta = conexion.prepareStatement(delete);
			consulta.setInt(1, nia);
			consulta.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
	}
	@Override
	public void eliminar_Alumno_por_Apellido(String apellido) {
		String delete = "DELETE FROM alumno WHERE Apellidos =?";

		try {
			PreparedStatement consulta = conexion.prepareStatement(delete);
			consulta.setString(1, apellido);
			consulta.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
	}
	@Override
	public void guardar_Datos_En_Xml(String ruta) {
		
		ArchivoXml xml=new ArchivoXml(ruta, "alumnos", "1.0");
		
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
	public void guardar_Datos_En_Json(String ruta) {
		File arch=comprobarFichero(ruta);
		List<Alumno> alumnos=this.obtener_todos_los_alumnos();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			
				
			mapper.writeValue(arch, alumnos);
			
			
			 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	@Override
	public void leer_Datos_En_Xml(String ruta) {
		ArchivoXml xml=new ArchivoXml();
		
		xml.leerXml(ruta);	
	}
	@Override
	public void leer_Datos_En_Json(String ruta) {
		File arch=comprobarFichero(ruta);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
		try {
			List<Alumno> alumnos = mapper.readValue(arch,new TypeReference<List<Alumno>>() {});
			alumnos.forEach(alumno->{
				this.insertar_Alumno(alumno);
			});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void cerrarConexion() {
		try {
			this.getConexion().close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the conexion
	 */
	private Connection getConexion() {
		return conexion;
	}

	/**
	 * @param conexion the conexion to set
	 */
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

}
