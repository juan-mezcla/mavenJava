/**
 * 
 */
package accesoDatos.tarea11;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
		
		List<Alumno> alumnos=new ArrayList<Alumno>();
		try {
			PreparedStatement consulta = this.getConexion().prepareStatement(select);

			ResultSet r = consulta.executeQuery();
			
			while (r.next()) {
				alumnos.add(new Alumno(r.getInt(1),r.getString(2),r.getString(3),r.getString(4).toCharArray()[0],LocalDate.parse(r.getString(5)) , r.getString(6), r.getString(7) , r.getString(8)) );
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
		File arch = new File(rutaFichero);

		if (!arch.exists()) {
			try {
				arch.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (!arch.isAbsolute()) {
			arch = new File(System.getProperty("user.dir"), rutaFichero);
		}


		try (FileOutputStream escribir = new FileOutputStream(arch,false)) {
			List <Alumno> alumnos=select_AllDatos();
			
			ObjectOutputStream escribirAlumno = new ObjectOutputStream(escribir);
			
			alumnos.forEach(alumno->{
				
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

	public boolean mandar_Datos_De_Fichero_A_BD() {
		return false;
	}

	public boolean modificar_Datos() {
		return false;
	}

	public boolean eliminar_Datos() {
		return false;
	}

	public boolean guardar_Datos_En_Xml_o_Json() {
		return false;
	}

	public boolean leer_Datos_En_Xml_o_Json() {
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
