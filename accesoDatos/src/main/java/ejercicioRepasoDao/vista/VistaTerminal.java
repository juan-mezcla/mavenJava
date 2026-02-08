/**
 * 
 */
package ejercicioRepasoDao.vista;

import java.util.List;
import java.util.Scanner;

import ejercicioRepasoDao.interfaces.ViewDAO;
import ejercicioRepasoDao.modelos.Producto;

/**
 * 
 */
public class VistaTerminal implements ViewDAO{
	private static Scanner prompt=new Scanner(System.in);
	
	
	@Override
	public int mostrarMenuOpciones() {
		int opcion=0;
			
			System.out.println("MENU PRODUCTOS:");
			System.out.println("1-mostrar Productos");
			System.out.println("2-insertar Productos");
			System.out.println("3-actualizar Productos");
			System.out.println("4-eliminar Productos");
			System.out.println("5-Salir");
			System.out.println("elige una de las opciones:");
			opcion=prompt.nextInt();
			prompt.nextLine();
			
		return opcion;
	}

	@Override
	public Producto guardarProducto() {
		
		System.out.println("introduce el nombre del producto:");
		String nombreProd=prompt.nextLine();
		
		System.out.println("introduce el precio que va a tener:");
		int precio=prompt.nextInt();
		prompt.nextLine();
		
		System.out.println("introduce el stock que va a tener:");
		int stock=prompt.nextInt();
		prompt.nextLine();
		
		return new Producto(nombreProd, precio, stock);
	}

	@Override
	public Producto eliminarProducto(List<Producto> productos) {
		int opcion=0;
		mostrarProductos(productos);
		System.out.println("elige un producto que quieras eliminar:");
		opcion=prompt.nextInt();
		prompt.nextLine();
		
		return productos.get(opcion-1);
	}

	@Override
	public Producto actualizarStockProducto(List<Producto> productos) {
		int opcion=0;
		mostrarProductos(productos);
		System.out.println("elige un producto que quieras actualizar:");
		opcion=prompt.nextInt();
		prompt.nextLine();
		
		Producto producto=productos.get(opcion-1);
		
		System.out.println("Cual es el stock nuevo del producto:");
		opcion=prompt.nextInt();
		prompt.nextLine();
		
		producto.setStock(opcion);
		System.out.println("producto actualizado-> "+producto.toString());
		return producto;
	}

	@Override
	public void mostrarProductos(List<Producto> productos) {
		System.out.println("todo los productos:");
		
		productos.forEach(producto->{
			System.out.println(producto.toString());
		});
	
	}

	@Override
	public void error(String mensaje) {
		System.err.println(mensaje);
		
	}

	@Override
	public void info(String mensaje) {
		// TODO Auto-generated method stub
		System.out.println(mensaje);
	}

}
