package ejercicioRepasoDao;

import ejercicioRepasoDao.interfaces.ProductoDAO;
import ejercicioRepasoDao.interfaces.ViewDAO;
import ejercicioRepasoDao.modelos.Producto;

public class Controlador {
	private ProductoDAO db;
	private ViewDAO vista;

	/**
	 * @param db
	 * @param vista
	 */
	public Controlador(ProductoDAO db, ViewDAO vista) {
		super();
		this.db = db;
		this.vista = vista;
	}

	public void ejecutar() {
		int opcion;
		
		do {
			opcion=vista.mostrarMenuOpciones();
			
			switch (opcion) {
			case 1:
				mostrarProductos();
				break;
				
			case 2:
				crearProducto();
				break;
				
			case 3:
				actualizarProducto();
				
				break;
				
			case 4:
				
				eliminarProducto();
				
				break;
				
			case 5:
				
				vista.info("Fin del programa");
				
				break;

			default:
				vista.error("tiene que ser un numero entre los que se dan de opcion");
				break;
			}
			
			
		} while (opcion!=5);
		
		
		
	}
	
	
	private void mostrarProductos() {
		vista.mostrarProductos(db.listarTodos());
	}
	
	private void crearProducto() {
		db.insertar(vista.guardarProducto());
		vista.info("producto creado correctamente");
	}
	
	private void actualizarProducto() {
		Producto prodSeleccionado=vista.actualizarStockProducto(db.listarTodos());
		db.actualizarStock(prodSeleccionado.getId(),prodSeleccionado.getStock());
		vista.info("producto actualizado correctamente");
	}

	private void eliminarProducto() {
		Producto prodSeleccionado=vista.eliminarProducto(db.listarTodos());
		db.eliminar(prodSeleccionado.getId());
		vista.info("producto eliminado correctamente");
	}
	
}
