/**
 * 
 */
package juan.buques.vistas;

import java.util.List;
import java.util.Scanner;

import juan.buques.interfaces.VistaDAO;
import juan.buques.modelos.Buque;
import juan.buques.modelos.Capitan;

/**
 * 
 */
public class consola implements VistaDAO {
	
	private static Scanner prompt=new Scanner(System.in);
	
	

	@Override
	public void mostrarBuques(List<Buque> buques) {
		System.out.println("quieres organizarlo por alguno de estos tipos?");
		System.out.println("1-Cirsterna 2-Porta contenedores");
		
		//buques.stream().filter(buque-> buque.get)

	}

	@Override
	public void asociarCapitarABuque() {
		System.out.println("introduce el nombre del capitan");
		String nombre=prompt.nextLine();

	}

	@Override
	public Buque insertarFlota() {
		System.out.println("nombre del barco:");
		String nombre=prompt.nextLine();
		
		
		return null;
	}

	@Override
	public Capitan insertarCapitan() {
		System.out.println("introduce el nombre del capitan");
		String nombre=prompt.nextLine();
		
		
		return null;
	}

	@Override
	public void comprobarIndiceRiesgo(List<Buque> buques) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int mostrarOpcionesMenu() {
		int opcion;
		
		System.out.println("Menu buques");
		System.out.println("1- Alta Flota.");
		System.out.println("2- indice riesgo bote.");
		System.out.println("3- Gestionar certifiaciones.");
		System.out.println("4- consultas cruzadas.");
		System.out.println("5- Renovacion de flota.");
		System.out.println("6- Salir.");
		opcion=prompt.nextInt();
		prompt.nextLine();
		return opcion;
	}

	@Override
	public void anadirCapitanBuque() {
		// TODO Auto-generated method stub
		
	}

}
