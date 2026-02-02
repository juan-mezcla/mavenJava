package tarea16;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tarea16.Alumno;
import tarea16.interfaces.DataBaseInterface;
import tarea16.interfaces.UserInterface;

public class Terminal_UI implements UserInterface {
	private DataBaseInterface db;
	private static Scanner prompt = new Scanner(System.in);
	private List<Alumno> alumnos = new ArrayList<Alumno>();

	private static final Logger log = LoggerFactory.getLogger(Terminal_UI.class);

	public Terminal_UI(DataBaseInterface db) {
		this.db = db;
	}

	@Override
	public void menu(DataBaseInterface db) {

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
			System.out.println("10-insertar grupo.");
			System.out.println("11-Salir.");
			try {

				opcion = prompt.nextInt();
			} catch (java.util.InputMismatchException e) {
				log.warn("Tiene que ser un numero");
				opcion = 0;
			}
			prompt.nextLine();
			try {

				switch (opcion) {
				case 1:
					db.insertar_Alumno(new Alumno());

					break;
				case 2:
					alumnos = db.obtener_todos_los_alumnos();

					List<String> grupos = db.obtener_nombre_grupos();

					alumnos = eleccion_grupo();

					String grupo = null;
					for (Alumno alumno : alumnos) {
						if (grupo == null) {

							grupo = alumno.getGrupo();
							System.out.println("\nGrupo " + grupo + ":");

						} else if (!grupo.contains(alumno.getGrupo())) {

							grupo = alumno.getGrupo();
							System.out.println("\nGrupo " + grupo + ":");

						}
						System.out.println(alumno.toString());
					}
					grupo = null;

					break;
				case 3:
					guardarEnFichero();
					break;
				case 4:
					mandar_datos_Fichera_a_BD();
					break;
				case 5:
					System.out.println("Introduce el NIA del alumno que quieres modificar:");
					int nia = prompt.nextInt();
					prompt.nextLine();

					System.out.println("Introduce el nuevo nombre del alumno:");
					String nombre = prompt.nextLine();
					db.actualizar_Alumno_por_NIA(opcion, nombre);

					break;
				case 6:
					System.out.println("Introduce el NIA del alumno que quieres eliminar:");

					nia = prompt.nextInt();

					db.eliminar_Alumno_por_NIA(nia);

					break;
				case 7:
					System.out.println("Introduce el apellido de el alumno que quieres eliminar:");
					nombre = prompt.nextLine();

					db.eliminar_Alumno_por_Apellido(nombre);

					break;
				case 8:
					guardar_datos_Xml_o_Json();
					break;
				case 9:
					leer_datos_Xml_o_Json();
					break;
				case 10:
					System.out.println("introduce el grupo que quieres añadir:");
					String nuevoGrupo = prompt.nextLine();

					db.crear_grupo(nuevoGrupo);

					break;

				case 11:
					System.out.println("Fin del programa");

					break;
				default:
					System.out.println("Valor no valido");
				}
			} catch (java.lang.NullPointerException e) {
				log.warn("Error al querer conectar con base de datos");
				opcion = 0;
			} catch (java.util.InputMismatchException e) {
				log.warn("Tiene que ser un numero");
				opcion = 0;
			}
		} while (opcion != 11);

	}

	@Override
	public void guardarEnFichero() {
		System.out.println("introduce la ruta donde quieres que se guarden los datos (tiene que ser un .txt):");
		String ruta = prompt.nextLine();
		if (ruta.endsWith(".txt")) {
			db.guardar_Datos_En_Fichero(ruta);
		} else {
			System.out.println("Tiene que ser en formato .txt el fichero");
		}
	}

	@Override
	public void mandar_datos_Fichera_a_BD() {// hacer primero lo de eliminar para hacer prueba
		System.out.println("introduce la ruta del fichero que contiene los datos:");
		String ruta = prompt.nextLine();
		if (ruta.endsWith(".txt")) {
			db.mandar_Datos_De_Fichero_A_BD(ruta);
		} else {
			System.out.println("Tiene que ser en formato .txt el fichero");
		}
	}

	@Override
	public void guardar_datos_Xml_o_Json() {
		System.out.println("Introduce la ruta y el nombre del archivo (SOLO se admiten archivos .xml o .json):");
		String ruta = prompt.nextLine();

		if (ruta.endsWith(".json") || ruta.endsWith(".JSON")) {

			if (!db.guardar_Datos_En_Json(ruta)) {
				System.out.println("Error al crear el Json.");
			}

		} else if (ruta.endsWith(".xml") || ruta.endsWith(".XML")) {

			db.guardar_Datos_En_Xml(ruta);
			System.out.println("XML creado correctamente");

		} else {
			System.out.println("Formato no valido de archivo");
		}

	}

	@Override
	public void leer_datos_Xml_o_Json() {
		System.out.println("Introduce la ruta y el nombre del archivo (SOLO se admiten archivos .xml o .json):");
		String ruta = prompt.nextLine();

		if (ruta.endsWith(".json") || ruta.endsWith(".JSON")) {

			if (!db.leer_Datos_En_Json(ruta)) {
				System.out.println("Error al crear el Json.");
			}

		} else if (ruta.endsWith(".xml") || ruta.endsWith(".XML")) {

			db.guardar_Datos_En_Xml(ruta);
			System.out.println("XML creado correctamente");

		} else {
			System.out.println("Formato no valido de archivo");
		}

	}

	public List<Alumno> eleccion_grupo() {
		List<String> grupos = db.obtener_nombre_grupos();
		System.out.println(grupos.size());
		String grupoElegido = null;
		int opcionGrupo;
		do {

			int cont = 1;
			for (String grupo : grupos) {
				System.out.println(cont + "- " + grupo);
				cont++;
			}

			System.out.println(grupos.size() + 1 + "- Todos los grupos.");
			System.out.println("Elige el grupo que quieras ver:");
			opcionGrupo = prompt.nextInt();
			prompt.nextLine();

		} while (opcionGrupo < 1 || opcionGrupo != grupos.size() + 1);

		if (opcionGrupo <= grupos.size() - 1) {

			grupoElegido = grupos.get(opcionGrupo - 1);

		}

		final String grupoFilter = grupoElegido;// por que si no, me da error para usarlo en el lamba.
		return alumnos.stream().filter(alumno -> alumno.getGrupo().equals(grupoFilter)).collect(Collectors.toList());
	}

}
