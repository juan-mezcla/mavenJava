/**
 * 
 */
package juan.buques.interfaces;

import java.util.List;

import juan.buques.modelos.Buque;

/**
 * 
 */
public interface BuquesDAO {
	
	List<Buque> mostrarBuques();
	boolean gestionCertificaiones(int idCapitan,String imo);
	boolean anadirFlota();
	Buque indiceRiesgoBuque();
	boolean renovarBuque(String imo,Buque buque);
	boolean anadirFlota(Buque buque);
}
