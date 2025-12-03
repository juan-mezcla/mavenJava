/**
 * 
 */
package ra_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 */
public class App {
	private Scanner prompt = new Scanner(System.in);
	private Ddbb conexion = new Ddbb();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new App().menu();
	}

	private void menu() {
		int opcion = 0;

		do {
			System.out.println("1-añadir vehiculo.");
			System.out.println("2-mostrar vehiculos.");
			System.out.println("3-mostrar vehiculos por rango KM.");
			System.out.println("4-Borrar vehiculo por matricula.");
			System.out.println("5-Salir.");
			System.out.println("Elige una opcion");
			opcion = prompt.nextInt();
			prompt.nextLine();
			switch (opcion) {
			case 1:
				guardarVehiculo();
				break;

			case 2:
				mostrarVehiculos();
				break;

			case 3:
				mostrarVehiculosPor_KM();
				break;

			case 4:
				borrarVehiculo();
				break;
			case 5:
				System.out.println("Hasta luego");
				conexion.cerrarConexion();
				break;
			default:
				System.out.println("fuera de rango");
				break;
			}

		} while (opcion != 5);
	}

	private void guardarVehiculo() {
		List<Vehiculo> vehiculos = conexion.selectAll();
		vehiculos=conexion.selectAll();
		for (int i = 0; i < 5; i++) {//Para añadir datos y hacer las demas operaciones
			
				conexion.insert(new Vehiculo());
		}
	}

	private void mostrarVehiculos() {
		List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		vehiculos = conexion.selectAll();

		System.out.println("Todos los vehiculos");
		vehiculos.forEach(v -> {
			System.out.println(v.toString());
		});
	}

	private void mostrarVehiculosPor_KM() {
		List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		System.out.println("Rango minimo");
		int min = prompt.nextInt();
		prompt.nextLine();
		System.out.println("Rango maximo");
		int max = prompt.nextInt();
		prompt.nextLine();

		if (min < max) {

			vehiculos = conexion.select_por_KM(min, max);
		} else {
			vehiculos = conexion.select_por_KM(max, min);
		}
		System.out.println("Todos los vehiculos");
		vehiculos.forEach(v -> {
			System.out.println(v.toString());
		});
	}

	private void borrarVehiculo() {
		mostrarVehiculos();
		System.out.println("Matricula del coche que se quiere borrar:");
		String matricula = prompt.nextLine();

		conexion.borrarVehiculo_por_matricula(matricula);
	}

}
