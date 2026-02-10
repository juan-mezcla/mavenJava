/**
 * 
 */
package juan.buques.modelos;

import java.time.LocalDate;

/**
 * 
 */
public class Astillero {
	private LocalDate fecha;
	private String motivo;
	private double precio;
	/**
	 * @param fecha
	 * @param motivo
	 * @param precio
	 */
	public Astillero(LocalDate fecha, String motivo, double precio) {
		super();
		this.fecha = fecha;
		this.motivo = motivo;
		this.precio = precio;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	
}
