/**
 * 
 */
package juan.buques.modelos;

import java.util.List;

/**
 * 
 */
public class Cisterna extends Buque {
	private int capacidadCarga;
	private String tipoCaso;
	public Cisterna(String iMO, String nombre, int caladoMax, List<Astillero> historialAstillero,int capacidadCarga,int idCapitan) {
		super(iMO, nombre, caladoMax, historialAstillero, idCapitan);
		this.capacidadCarga=capacidadCarga;
	}
	
	public int getCapacidadCarga() {
		return capacidadCarga;
	}
	public void setCapacidadCarga(int capacidadCarga) {
		this.capacidadCarga = capacidadCarga;
	}

	@Override
	void indiceRiesgo() {
		// TODO Auto-generated method stub
		
	}
	
	

}
