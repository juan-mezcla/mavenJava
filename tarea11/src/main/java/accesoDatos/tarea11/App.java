package accesoDatos.tarea11;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
	private static Scanner prompt = new Scanner(System.in);
	private static DatabaseManager conexion = new DatabaseManager();
	private static List<Alumno> alumnos=new ArrayList<Alumno>();
	public static void main(String[] args) {
		
		new App().menu();
	}

	private void menu() {

		int opcion = 0;
		do {
			System.out.println("Elige una operacion a realizar:");
			System.out.println("1-Insetar alumno.");
			System.out.println("2-Mostar alumnos.");
			System.out.println("3-Guardar alumnos en fichero.");
			System.out.println("4-Leer alumnos de un fichero y guardar en base de datos.");
			System.out.println("5-Modificar datos alumno.");
			System.out.println("6-Eliminar alumno.");
			System.out.println("7-Eliminar alumnos por apellido.");
			System.out.println("8-Guardar alumnos en fichero XML o JSON.");
			System.out.println("9-Leer un fichero XML o JSON de alumnos y guardarlos en la BD");
			System.out.println("10-Salir.");
			opcion = prompt.nextInt();

			prompt.nextLine();
			try {
				
				switch (opcion) {
				case 1:
					insertar_Alumno();
					break;
				case 2:
				
					mostrar_todos_los_alumnos();
					break;
				case 3:
					guardarEnFichero();
					break;
				case 4:
					
					break;
				case 5:
					
					break;
				case 6:
					
					break;
				case 7:
					
					break;
				case 8:
					break;
				case 9:
					break;
				case 10:
					System.out.println("Fin del programa");
					conexion.cerrarConexion();
					break;
				default:
					System.out.println("Valor no valido");
				}
			}catch (java.lang.NullPointerException e) {
				System.out.println("Error al querer conectar con base de datos");
				e.printStackTrace();
			}

		} while (opcion != 10);

	}

	private void insertar_Alumno() {
		Alumno alumno=new Alumno();
		
		conexion.inertar_Datos(alumno);
	}
	
	
	private void mostrar_todos_los_alumnos() {
		alumnos=conexion.select_AllDatos();
		
		alumnos.forEach(alumno->{
			System.out.println(alumno);
		});
	}
	
	private void guardarEnFichero() {
		System.out.println("introduce la ruta donde quieres que se guarden los datos (tiene que ser un .txt):");
		String ruta=prompt.nextLine();
		if(ruta.endsWith(".txt")) {
			conexion.guardar_Datos_En_Fichero(ruta);
		}else {
			System.out.println("Tiene que ser en formato .txt el fichero");
		}
	}
	
	
	private void mandar_datos_Fichera_a_BD() {
		System.out.println("introduce la ruta del fichero que contiene los datos:");
		String ruta=prompt.nextLine();
		if(ruta.endsWith(".txt")) {
			conexion.guardar_Datos_En_Fichero(ruta);
		}else {
			System.out.println("Tiene que ser en formato .txt el fichero");
		}
	}
	
}
