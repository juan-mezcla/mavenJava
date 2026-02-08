/**
 * 
 */
package ejercicioRepasoDao;

import java.util.List;

import ejercicioRepasoDao.db.ProductosDaoImpl;
import ejercicioRepasoDao.interfaces.ProductoDAO;
import ejercicioRepasoDao.interfaces.ViewDAO;
import ejercicioRepasoDao.modelos.Producto;
import ejercicioRepasoDao.vista.VistaTerminal;

/**
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProductoDAO db=new ProductosDaoImpl();
		ViewDAO vista=new VistaTerminal();
		new Controlador(db, vista).ejecutar();
	}

}
