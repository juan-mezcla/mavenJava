package juan.buques.interfaces;

import java.util.List;

import juan.buques.modelos.Buque;
import juan.buques.modelos.Capitan;

public interface VistaDAO {
	int mostrarOpcionesMenu();
	void mostrarBuques(List<Buque> buques);
	void asociarCapitarABuque();
	Buque insertarFlota();
	Capitan insertarCapitan();
	void comprobarIndiceRiesgo(List<Buque> buques);
	void anadirCapitanBuque();
	
}
