/**
 * 
 */
package ejercicioRepasoDao.modelos;

/**
 * 
 */
public class Producto {
	private String nombre;
	private int id,precio,stock;
	/**
	 * @param nombre
	 * @param id
	 * @param precio
	 * @param stock
	 */
	public Producto(int id, String nombre,  int precio, int stock) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.precio = precio;
		this.stock = stock;
	}
	
	/**
	 * @param nombre
	 * @param precio
	 * @param stock
	 */
	public Producto(String nombre, int precio, int stock) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Producto [nombre=" + nombre + ", id=" + id + ", precio=" + precio + ", stock=" + stock + "]";
	}


	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the precio
	 */
	public int getPrecio() {
		return precio;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	/**
	 * @return the stock
	 */
	public int getStock() {
		return stock;
	}
	/**
	 * @param stock the stock to set
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
