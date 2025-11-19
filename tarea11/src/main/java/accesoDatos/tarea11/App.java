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
	private static List<Alumno> alumnos = new ArrayList<Alumno>();

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
			System.out.println("5-Modificar datos alumno por nia.");
			System.out.println("6-Eliminar alumno por nia.");
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
					mandar_datos_Fichera_a_BD();
					break;
				case 5:
					actualizar_Alumno_por_NIA();
					break;
				case 6:
					eliminar_Alumno_por_NIA();
					break;
				case 7:
					eliminar_Alumno_por_Apellido();
					break;
				case 8:
					guardar_datos_Xml_o_Json();
					break;
				case 9:
					leer_datos_Xml_o_Json();//FALLA JSON CAGO EN DEU
					break;
				case 10:
					System.out.println("Fin del programa");
					conexion.cerrarConexion();
					break;
				default:
					System.out.println("Valor no valido");
				}
			} catch (java.lang.NullPointerException e) {
				System.out.println("Error al querer conectar con base de datos");
				e.printStackTrace();
			}

		} while (opcion != 10);

	}

	private void insertar_Alumno() {
		Alumno alumno = new Alumno();

		conexion.inertar_Datos(alumno);
	}

	private void mostrar_todos_los_alumnos() {
		alumnos = conexion.select_AllDatos();

		alumnos.forEach(alumno -> {
			System.out.println(alumno);
		});
	}

	private void guardarEnFichero() {
		System.out.println("introduce la ruta donde quieres que se guarden los datos (tiene que ser un .txt):");
		String ruta = prompt.nextLine();
		if (ruta.endsWith(".txt")) {
			conexion.guardar_Datos_En_Fichero(ruta);
		} else {
			System.out.println("Tiene que ser en formato .txt el fichero");
		}
	}

	private void mandar_datos_Fichera_a_BD() {// hacer primero lo de eliminar para hacer prueba
		System.out.println("introduce la ruta del fichero que contiene los datos:");
		String ruta = prompt.nextLine();
		if (ruta.endsWith(".txt")) {
			conexion.mandar_Datos_De_Fichero_A_BD(ruta);
		} else {
			System.out.println("Tiene que ser en formato .txt el fichero");
		}
	}

	private void eliminar_Alumno_por_NIA() {
		mostrar_todos_los_alumnos();
		System.out.println("Introduce el NIA del alumno que quieres eliminar:");

		if (prompt.hasNextInt()) {
			int nia = prompt.nextInt();
			prompt.nextLine();

			if (!conexion.eliminar_Datos(nia)) {
				System.out.println("No se ha encontrado ningun alumno con ese NIA");
			}
		} else {
			System.out.println("Tienes que introducir un numero");
			prompt.nextLine();
		}
	}
	
	private void eliminar_Alumno_por_Apellido() {
		mostrar_todos_los_alumnos();
		System.out.println("Introduce el apellido con el que quieres eliminar alumno/s:");

		String apellido = prompt.nextLine();

		if (!apellido.matches("\\d+$")) {

			if (!conexion.eliminar_Datos_por_apellido(apellido)) {
				System.out.println("No se ha encontrado ningun alumno con ese NIA");
			}
		} else {
			System.out.println("El nombre solo se admite caracteres.");
		}
	}

	private void actualizar_Alumno_por_NIA() {
		mostrar_todos_los_alumnos();
		System.out.println("Introduce el NIA del alumno que quieres modificar:");

		if (prompt.hasNextInt()) {
			int nia = prompt.nextInt();
			prompt.nextLine();

			System.out.println("Nuevo nombre para ese alummno:");
			String nombre = prompt.nextLine();

			if (!nombre.matches("\\d+$")) {

				if (!conexion.modificar_Datos(nia, nombre)) {
					System.out.println("No se ha encontrado ningun alumno con ese NIA");
				}
			} else {
				System.out.println("El nombre solo se admite caracteres.");
			}
		} else {
			System.out.println("Tienes que introducir un numero");
			prompt.nextLine();
		}
	}
	
	private void guardar_datos_Xml_o_Json() {
		System.out.println("Introduce la ruta y el nombre del archivo (SOLO se admiten archivos .xml o .json):");
		String ruta=prompt.nextLine();
		
		if(ruta.endsWith(".json") || ruta.endsWith(".JSON")) {
			
			if(!conexion.guardar_Datos_En_Json(ruta)) {
				 System.out.println("Error al crear el Json.");
			}
			
		}else if(ruta.endsWith(".xml") || ruta.endsWith(".XML")) {
			
			conexion.guardar_Datos_En_Xml(ruta);
			System.out.println("XML creado correctamente");
			
		}else {
			System.out.println("Formato no valido de archivo");
		}
		
	}
	
	private void leer_datos_Xml_o_Json() {
		System.out.println("Introduce la ruta y el nombre del archivo (SOLO se admiten archivos .xml o .json):");
		String ruta=prompt.nextLine();
		
		if(ruta.endsWith(".json") || ruta.endsWith(".JSON")) {
			
			if(!conexion.leer_Datos_En_Json(ruta)) {
				 System.out.println("Error al crear el Json.");
			}
			
		}else if(ruta.endsWith(".xml") || ruta.endsWith(".XML")) {
			
			conexion.guardar_Datos_En_Xml(ruta);
			System.out.println("XML creado correctamente");
			
		}else {
			System.out.println("Formato no valido de archivo");
		}
		
	}

}
