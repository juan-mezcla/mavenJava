/**
 * 
 */
package ra_2;

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
public class Ddbb {
	private Connection conexion;
	private String user = "root", password = "manager",
			url = "jdbc:mysql://localhost:3306/ra_2_vehiculos?useSSL=false&serverTimeZone=UTC";

	private String camposVehiculo = "matricula, marca, modelo, kilometros, fechaInspeccion";

	/**
	 * 
	 */
	public Ddbb() {
		// TODO Auto-generated constructor stub
		try {
			this.conexion = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("error al conectar");
			e.printStackTrace();
		}
	}

	public void cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insert(Vehiculo vehiculo) {
		String srtInsertar = "INSERT INTO vehiculos (" + camposVehiculo + ") values (?,?,?,?,?)";

		try {
			PreparedStatement consulta = conexion.prepareStatement(srtInsertar);

			consulta.setString(1, vehiculo.getMatricula());
			consulta.setString(2, vehiculo.getMarca());
			consulta.setString(3, vehiculo.getModelo());
			consulta.setInt(4, vehiculo.getKm());
			consulta.setDate(5, java.sql.Date.valueOf(vehiculo.getFechaInspenccion()));

			consulta.execute();
			consulta.close();

			System.out.println("Datos del vehiculo añadidos");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Vehiculo> selectAll() {
		String srtSelect = "SELECT " + camposVehiculo + " FROM vehiculos ORDER BY matricula";
		List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		try {
			PreparedStatement consulta = conexion.prepareStatement(srtSelect);
			ResultSet resultado = consulta.executeQuery();

			while (resultado.next()) {
				resultado.getString(1);
				resultado.getString(2);
				resultado.getString(3);
				resultado.getInt(4);
				resultado.getDate(5);
				Vehiculo vehiculo = new Vehiculo(resultado.getString(1), resultado.getString(2), resultado.getString(3),
						resultado.getInt(4),LocalDate.parse(resultado.getDate(5).toString()));

				vehiculos.add(vehiculo);
			}
			resultado.close();

			consulta.close();
			System.out.println("Recogidos correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vehiculos;
	}

	public List<Vehiculo> select_por_KM(int rangoMin, int rangoMax) {
		String srtSelect = "SELECT " + camposVehiculo + " FROM vehiculos WHERE km>=?AND km<=?";
		List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		try {
			PreparedStatement consulta = conexion.prepareStatement(srtSelect);

			consulta.setInt(1, rangoMin);
			consulta.setInt(2, rangoMax);

			ResultSet resultado = consulta.executeQuery();

			while (resultado.next()) {
				resultado.getString(1);
				resultado.getString(2);
				resultado.getString(3);
				resultado.getInt(4);
				resultado.getDate(5);
				Vehiculo vehiculo = new Vehiculo(resultado.getString(1), resultado.getString(2), resultado.getString(3),
						resultado.getInt(4), LocalDate.parse(resultado.getDate(5).toString()));
				
				vehiculos.add(vehiculo);
			}
			resultado.close();

			consulta.close();
			System.out.println("Recogidos correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vehiculos;
	}

	public List<Vehiculo> borrarVehiculo_por_matricula(String matricula) {
		String srtUpdate = "DELETE FROM vehiculos WHERE matricula=?";
		List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		try {
			PreparedStatement consulta = conexion.prepareStatement(srtUpdate);

			consulta.setString(1, matricula);

			consulta.execute();
			consulta.close();
			System.out.println("vehiculo eliminado correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vehiculos;
	}
}
