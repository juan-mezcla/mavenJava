package ejercicioRepasoDao.interfaces;

import java.util.List;

import ejercicioRepasoDao.modelos.Producto;

public interface ViewDAO {
	
	int mostrarMenuOpciones();
	
	Producto guardarProducto();
	
	Producto eliminarProducto(List<Producto> productos);
	Producto actualizarStockProducto(List<Producto> productos);
	void mostrarProductos(List<Producto> productos);
	
	void error(String mensaje);
	void info(String mensaje);

}
