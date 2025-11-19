/**
 * 
 */
package accesoDatos.tarea11;

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
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



/**
 * 
 */
public class DatabaseManager {
	private String url = "jdbc:mysql://localhost:3306/tarea11?useSSL=false&serverTimezone=UTC", user = "root",
			pass = "manager";
	private Connection conexion;
	private List<String> campos = List.of("NIA", "Nombre", "Apellidos", "Genero", "FechaNacimiento", "Ciclo", "Curso",
			"Grupo");
	private String strCampos = String.join(",", campos);

	/**
	 * 
	 */
	public DatabaseManager() {
		try {
			conexion = DriverManager.getConnection(url, user, pass);
			System.out.println("conexion creada correctamente");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al crear conexion");
		}

	}

	public boolean inertar_Datos(Alumno alumno) {
		String insert = "INSERT INTO alumno (" + strCampos + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement consulta = this.getConexion().prepareStatement(insert);// para evitar inyecciones de
																						// codigo.

			consulta.setInt(1, alumno.getNia());
			consulta.setString(2, alumno.getNombre());
			consulta.setString(3, alumno.getApellidos());
			consulta.setString(4, alumno.getGenero());
			consulta.setDate(5, java.sql.Date.valueOf(alumno.getFecha()));// Para formatearlo y que valga para mysql
			consulta.setString(6, alumno.getCiclo());
			consulta.setString(7, alumno.getCurso());
			consulta.setString(8, alumno.getGrupo());

			consulta.executeUpdate();
			System.out.println("Alumno insertado correctamente");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("---ERROR EN LA INSERCION---");
			return false;
		}

	}

	public List<Alumno> select_AllDatos() {
		String select = "Select " + strCampos + " FROM alumno";

		List<Alumno> alumnos = new ArrayList<Alumno>();
		try {
			PreparedStatement consulta = this.getConexion().prepareStatement(select);

			ResultSet r = consulta.executeQuery();

			while (r.next()) {
				alumnos.add(new Alumno(r.getInt(1), r.getString(2), r.getString(3), r.getString(4).toCharArray()[0],
						LocalDate.parse(r.getString(5)), r.getString(6), r.getString(7), r.getString(8)));
			}
			System.out.println("Datos recogidos correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alumnos;
	}

	public boolean select_Datos(String campos) {

		return false;
	}

	public boolean guardar_Datos_En_Fichero(String rutaFichero) {
		File arch = comprobarFichero(rutaFichero);

		try (FileOutputStream escribir = new FileOutputStream(arch, false)) {
			List<Alumno> alumnos = select_AllDatos();

			ObjectOutputStream escribirAlumno = new ObjectOutputStream(escribir);

			alumnos.forEach(alumno -> {

				try {
					escribirAlumno.writeObject(alumno);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			escribir.flush();
			escribir.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean mandar_Datos_De_Fichero_A_BD(String rutaArchAlumnos) {
		File arch = comprobarFichero(rutaArchAlumnos);
		
		try (FileInputStream leer=new FileInputStream(arch)){
			
			ObjectInputStream objArch=new ObjectInputStream(leer);
			
			while(leer.available()>0) {
				Alumno alumno=(Alumno) objArch.readObject();
				this.inertar_Datos(alumno);
			}
		
			leer.close();
			objArch.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean modificar_Datos(int nia,String nombre) {
		String update = "UPDATE alumno SET nombre =? WHERE NIA=?";

		try {
			PreparedStatement consulta = conexion.prepareStatement(update);
			consulta.setString(1, nombre);
			consulta.setInt(2, nia);
			consulta.execute();
			
			System.out.println("Alumno modificado correctamente");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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

	public boolean eliminar_Datos(int nia) {
		String delete = "DELETE FROM alumno WHERE NIA =?";

		try {
			PreparedStatement consulta = conexion.prepareStatement(delete);
			consulta.setInt(1, nia);
			consulta.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean eliminar_Datos_por_apellido(String apellido) {
		String delete = "DELETE FROM alumno WHERE Apellidos =?";

		try {
			PreparedStatement consulta = conexion.prepareStatement(delete);
			consulta.setString(1, apellido);
			consulta.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public void guardar_Datos_En_Xml(String ruta) {
		
		ArchivoXml xml=new ArchivoXml(ruta, "alumnos", "1.0");
		
		List<Alumno> alumnos=this.select_AllDatos();
		List<Atributo> atributosAlumno=new ArrayList<Atributo>();
		
		Document doc=xml.getDoc();
		alumnos.forEach(alumno->{
			
			atributosAlumno.add(new Atributo("nia",Integer.toString(alumno.getNia())));
			atributosAlumno.add(new Atributo("nombre",alumno.getNombre()));
			atributosAlumno.add(new Atributo("apellidos",alumno.getApellidos()));
			atributosAlumno.add(new Atributo("genero",String.valueOf(alumno.getGenero())));
			atributosAlumno.add(new Atributo("fechaNacimiento",alumno.getFechaString()));
			atributosAlumno.add(new Atributo("ciclo",alumno.getCiclo()));
			atributosAlumno.add(new Atributo("curso",alumno.getCurso()));
			atributosAlumno.add(new Atributo("grupo",alumno.getGrupo()));
			
			
			xml.anadirAtributo(doc.getDocumentElement(),"alumno",alumno.getNombre(), atributosAlumno);
		});
		
		xml.crearXml();
	}
	
	public boolean guardar_Datos_En_Json(String ruta) {
		File arch=comprobarFichero(ruta);
		List<Alumno> alumnos=this.select_AllDatos();
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			FileWriter escribirJson = new FileWriter(arch);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
				
			mapper.writeValue(arch, alumnos);
			
			
			 System.out.println("Archivo JSON creado correctamente.");
			 return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean leer_Datos_En_Xml() {
		return false;
	}
	
	public boolean leer_Datos_En_Json(String ruta) {
		File arch=comprobarFichero(ruta);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			List<Alumno> alumnos = mapper.readValue(arch,new TypeReference<List<Alumno>>() {});
			alumnos.forEach(alumno->{
				System.out.println(alumno);
			});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void cerrarConexion() {
		try {
			this.getConexion().close();
			System.out.println("conexion cerrada");
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
