/**
 * 
 */
package ejercicioRepasoDao.interfaces;

import java.util.List;

import ejercicioRepasoDao.modelos.Producto;

/**
 * 
 */
public interface ProductoDAO {
	
	boolean insertar(Producto prod);
	List<Producto> listarTodos();
	boolean actualizarStock(int id,int nuevoStock);
	boolean eliminar(int id);
}
