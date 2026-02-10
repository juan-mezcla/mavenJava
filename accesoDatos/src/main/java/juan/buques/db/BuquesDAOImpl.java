/**
 * 
 */
package juan.buques.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import juan.buques.interfaces.BuquesDAO;
import juan.buques.modelos.Astillero;
import juan.buques.modelos.Buque;
import juan.buques.modelos.Cisterna;
import juan.buques.modelos.PortaContenedores;

/**
 * 
 */
public class BuquesDAOImpl implements BuquesDAO {
	private String tablaBuque = "buques", camposCapitan = "nombre,numBuques,millas", tablaCapitanes = "capitanes",
			camposBuques = "imo,nombre,calado,numContenedores,tipoBuque,registroAstillero,idCapitan";

	@Override
	public List<Buque> mostrarBuques() {
		String select = "SELECT " + camposBuques + " FROM " + tablaBuque;
		List<Buque> buques = new ArrayList<Buque>();

		try (Connection con = PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(select);) {
			

			ResultSet resultadoConsulta = consulta.executeQuery();

			while (resultadoConsulta.next()) {

				Cisterna cisternaa = null;
				PortaContenedores portaContenedores = null;
				if (resultadoConsulta.getString(5).equals("Cisterna")) {

					buques.add(new Cisterna(resultadoConsulta.getString(1), resultadoConsulta.getString(2),
							resultadoConsulta.getInt(3), (List<Astillero>) resultadoConsulta.getObject(6), 0,
							resultadoConsulta.getInt(7)));

				} else {

					buques.add(new PortaContenedores(resultadoConsulta.getString(1), resultadoConsulta.getString(2),
							resultadoConsulta.getInt(3), (List<Astillero>) resultadoConsulta.getObject(6), 0, select,
							resultadoConsulta.getInt(7)));

				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return buques;
	}

	@Override
	public boolean gestionCertificaiones(int idCapitan,String imo) {
		String insert = "UPDATE " + tablaBuque + " SET idCapitan=? WHERE imo=?";
		try (Connection con = PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(insert);) {
			
			
			consulta.setInt(1, idCapitan);
			consulta.setString(2, imo);
			
			
			consulta.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean anadirFlota(Buque buque) {
		String insert = "INSERT INTO " + tablaBuque + " (" + camposBuques + ") VALUES(?,?,?,?,?,?,?)";
		try (Connection con = PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(insert);) {
			if (buque instanceof Cisterna) {

				Cisterna cisterna = (Cisterna) buque;
				consulta.setString(1, cisterna.getIMO());
				consulta.setString(2, cisterna.getNombre());
				consulta.setInt(3, cisterna.getCaladoMax());
				consulta.setInt(4, 0);
				consulta.setString(5, "Cisterna");
				consulta.setObject(6, cisterna.getHistorialAstillero());
				consulta.setInt(7, cisterna.getIdCapitan());

			} else {

				PortaContenedores cisterna = (PortaContenedores) buque;
				consulta.setString(1, cisterna.getIMO());
				consulta.setString(2, cisterna.getNombre());
				consulta.setInt(3, cisterna.getCaladoMax());
				consulta.setInt(4, 0);
				consulta.setString(5, "Cisterna");
				consulta.setObject(6, cisterna.getHistorialAstillero());
				consulta.setInt(7, cisterna.getIdCapitan());

			}
			consulta.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Buque indiceRiesgoBuque() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean renovarBuque(String imo,Buque buque) {
		String update = "UPDATE " + tablaBuque + " SET idCapitan=? WHERE imo=?";
		try (Connection con = PoolConexion.getConnection();
				PreparedStatement consulta = con.prepareStatement(update);) {
			
			/*
			 
			 * 
			consulta.setInt(1, imo);
			consulta.setString(2, imo);
			 * */
			
			
			consulta.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean anadirFlota() {
		// TODO Auto-generated method stub
		return false;
	}

}
