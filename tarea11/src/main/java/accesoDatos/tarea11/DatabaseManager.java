/**
 * 
 */
package accesoDatos.tarea11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 */
public class DatabaseManager {
	private String url="jdbc:mysql://localhost:3306/tarea11?useSSL=false&serverTimezone=UTC", 
				   user="root",
				   pass="manager";
	private Connection conexion;
	private List<String> campos=List.of("NIA", "Nombre", "Apellidos", "Genero",
		    							"FechaNacimiento", "Ciclo", "Curso", "Grupo");
	private String strCampos=String.join(",", campos);
	/**
	 * 
	 */
	public DatabaseManager() {
		try {
			conexion=DriverManager.getConnection(url, user, pass);
			System.out.println("conexion creada correctamente");
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al crear conexion");
		}
		
	}
	
	
	public boolean inertar_Datos(Alumno alumno) {
		String insert="INSERT INTO alumno ("+strCampos+") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement consulta=this.getConexion().prepareStatement(insert);//para evitar inyecciones de codigo.
			
			consulta.setInt(1, alumno.getNia());
			consulta.setString(2, alumno.getNombre());
			consulta.setString(3, alumno.getApellidos());
			consulta.setString(4, alumno.getGenero());
			consulta.setDate(5, java.sql.Date.valueOf(alumno.getFecha()));//Para formatearlo y que valga para mysql
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
	
	public boolean select_AllDatos() {
		String select="Select * FROM alumno";
		
		try {
			PreparedStatement consulta=this.getConexion().prepareStatement(select);
			
			ResultSet r=consulta.executeQuery();
			System.out.println("NIA | NOMBRE | APELLIDOS | GENERO | FECHA | CICLO | CURSO | GRUPO");
			while (r.next()) {
				
			System.out.printf("%d   | %s | %s | %s     | %s  | %s  | %s| %s %n", 
								  r.getInt(1),r.getString(2),r.getString(3),
								  r.getString(4),r.getString(5).toString(),r.getString(6),
								  r.getString(7),r.getString(8));
				
			}
			System.out.println("Todos los datos completados");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}
	
	public boolean select_Datos(String campos) {
		
		return false;
	}
	
	
	public boolean guardar_Datos_En_Fichero() {
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
