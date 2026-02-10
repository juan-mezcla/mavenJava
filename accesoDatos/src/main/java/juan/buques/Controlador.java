package juan.buques;

import juan.buques.interfaces.BuquesDAO;
import juan.buques.interfaces.VistaDAO;

public class Controlador {
	private BuquesDAO db;
	private VistaDAO vista;

	public Controlador(BuquesDAO db, VistaDAO vista) {
		super();
		this.db = db;
		this.vista = vista;
	}

	public void ejecutar() {
		int opcion=0;
		do {
			opcion=vista.mostrarOpcionesMenu();
			
			switch (opcion) {
			case 1:
				insertarFlota();
				break;
			case 2:
				auditoriaSeguridad();		
							break;
			case 3:
				gestionCertificaciones();
				break;
			case 4:
				consultasCruzadas();
				break;
			case 5:
				renovacion();
				break;
			case 6:
				System.out.println("hasta luego");
				break;

			default:
				System.out.println("numero fuera de rango");
				break;
			}
		}while(opcion!=6);
	}
	
	private void insertarFlota() {
		
	}
	
private void auditoriaSeguridad() {
		
	}

private void gestionCertificaciones() {
	
}

private void consultasCruzadas() {
	vista.mostrarBuques(db.mostrarBuques());
}

private void renovacion() {
	
}

}
