/**
 * 
 */
package ejercicioRepasoDao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ejercicioRepasoDao.interfaces.ProductoDAO;
import ejercicioRepasoDao.modelos.Producto;

/**
 * 
 */
public class ProductosDaoImpl implements ProductoDAO{
	private String tabla="productos",campos="nombre,precio,stock";
	

	@Override
	public boolean insertar(Producto prod) {
		String insertar="INSERT INTO "+tabla+" ("+campos+") Values(?,?,?)";
		try(Connection con=PoolConexiones.getConnection();
				PreparedStatement consulta=con.prepareStatement(insertar);){
			
			consulta.setString(1, prod.getNombre());
			consulta.setInt(2, prod.getPrecio());
			consulta.setInt(3, prod.getStock());
			
			
			consulta.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
		
	}

	@Override
	public List<Producto> listarTodos() {
		String select="SELECT id,"+ campos +" FROM "+tabla;
		List<Producto> productos=new ArrayList<Producto>();
		try(Connection con=PoolConexiones.getConnection();
				PreparedStatement consulta=con.prepareStatement(select);){
		
			ResultSet resultadoSelect=consulta.executeQuery();
			
			while (resultadoSelect.next()) {
				productos.add(new Producto(resultadoSelect.getInt(1), resultadoSelect.getString(2), 
										   resultadoSelect.getInt(3), resultadoSelect.getInt(4)));
			}
			
			resultadoSelect.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		return productos;
	}

	@Override
	public boolean actualizarStock(int id, int nuevoStock) {
		String update="UPDATE "+tabla+" SET stock=? WHERE id=?";
		try(Connection con=PoolConexiones.getConnection();
				PreparedStatement consulta=con.prepareStatement(update);){
			
			
			consulta.setInt(1, nuevoStock);
			consulta.setInt(2, id);
			
			
			consulta.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean eliminar(int id) {
		String delete="DELETE FROM "+tabla+" WHERE id=?";
		try(Connection con=PoolConexiones.getConnection();
				PreparedStatement consulta=con.prepareStatement(delete);){	
			
			consulta.setInt(1, id);
			
			consulta.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	

}
