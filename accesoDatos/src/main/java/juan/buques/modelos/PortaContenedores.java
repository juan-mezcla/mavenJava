package juan.buques.modelos;

import java.util.List;

public class PortaContenedores extends Buque{
	private int maxTEUS;
	private String tipoGrua;
	/**
	 * @param iMO
	 * @param nombre
	 * @param caladoMax
	 * @param historialAstillero
	 * @param maxTEUS
	 * @param tipoGrua
	 */
	public PortaContenedores(String iMO, String nombre, int caladoMax, List<Astillero> historialAstillero, int maxTEUS,
			String tipoGrua,int idCapitan) {
		super(iMO, nombre, caladoMax, historialAstillero, idCapitan);
		this.maxTEUS = maxTEUS;
		this.tipoGrua = tipoGrua;
	}
	
	@Override
	void indiceRiesgo() {
		// TODO Auto-generated method stub
		
	}

	public int getMaxTEUS() {
		return maxTEUS;
	}

	public void setMaxTEUS(int maxTEUS) {
		this.maxTEUS = maxTEUS;
	}

	public String getTipoGrua() {
		return tipoGrua;
	}

	public void setTipoGrua(String tipoGrua) {
		this.tipoGrua = tipoGrua;
	}
	
	

}
