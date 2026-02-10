/**
 * 
 */
package juan.buques.modelos;

import java.util.List;

/**
 * 
 */
public abstract class Buque {
	private String IMO,nombre;
	private int caladoMax,id,idCapitan;
	static int acumulador=1;
	private List<Astillero> historialAstillero;
	/**
	 * @param iMO
	 * @param nombre
	 * @param caladoMax
	 * @param historialAstillero
	 */
	public Buque(String iMO, String nombre, int caladoMax, List<Astillero> historialAstillero,int idCapitan) {
		super();
		this.id=acumulador;
		IMO = iMO+acumulador;
		this.nombre = nombre;
		this.caladoMax = caladoMax;
		this.historialAstillero = historialAstillero;
		this.idCapitan=idCapitan;
		acumulador++;
	}
	
	
	
	public int getIdCapitan() {
		return idCapitan;
	}



	public void setIdCapitan(int idCapitan) {
		this.idCapitan = idCapitan;
	}



	public void setId(int id) {
		this.id = id;
	}



	abstract void indiceRiesgo();
	
	
	public String getIMO() {
		return IMO;
	}
	public void setIMO(String iMO) {
		IMO = iMO;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCaladoMax() {
		return caladoMax;
	}
	public void setCaladoMax(int caladoMax) {
		this.caladoMax = caladoMax;
	}
	public List<Astillero> getHistorialAstillero() {
		return historialAstillero;
	}
	public void setHistorialAstillero(List<Astillero> historialAstillero) {
		this.historialAstillero = historialAstillero;
	}
	
	public int getId() {
		return id;
	}
}
