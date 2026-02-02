package tarea16;

import java.util.ArrayList;
import java.util.Iterator;
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
			System.out.println("3-Mostar alumno por id.");
			System.out.println("4-Guardar alumnos en fichero.");
			System.out.println("5-Leer alumnos de un fichero y guardar en base de datos.");
			System.out.println("6-Modificar datos alumno por nia.");
			System.out.println("7-Eliminar alumno por nia.");
			System.out.println("8-Eliminar alumnos por apellido.");
			System.out.println("9-Guardar alumnos en fichero XML o JSON.");
			System.out.println("10-Leer un fichero XML o JSON de alumnos y guardarlos en la BD");
			System.out.println("11-insertar grupo.");
			System.out.println("12-cambiar alumno de grupo.");
			System.out.println("13-guardar en xml un grupo.");
			System.out.println("14-Salir.");
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

					alumnos = eleccion_grupo(alumnos);

					mostrar_alumnos(alumnos, true);

					break;

				case 3:
					Alumno alumno = elegir_alumno_por_id();

					System.out.println(alumno.toString());
					break;
				case 4:
					guardarEnFichero();
					break;
				case 5:
					mandar_datos_Fichera_a_BD();
					break;
				case 6:
					System.out.println("Introduce el NIA del alumno que quieres modificar:");
					int nia = prompt.nextInt();
					prompt.nextLine();

					System.out.println("Introduce el nuevo nombre del alumno:");
					String nombre = prompt.nextLine();
					db.actualizar_Alumno_por_NIA(opcion, nombre);

					break;
				case 7:
					System.out.println("Introduce el NIA del alumno que quieres eliminar:");

					nia = prompt.nextInt();

					db.eliminar_Alumno_por_NIA(nia);

					break;
				case 8:
					System.out.println("Introduce el apellido de el alumno que quieres eliminar:");
					nombre = prompt.nextLine();

					db.eliminar_Alumno_por_Apellido(nombre);

					break;
				case 9:
					guardar_datos_Xml_o_Json();
					break;
				case 10:
					leer_datos_Xml_o_Json();
					break;
				case 11:
					System.out.println("introduce el grupo que quieres añadir:");
					String nuevoGrupo = prompt.nextLine();

					db.crear_grupo(nuevoGrupo);

					break;

				case 12:

					Alumno alumnoo = elegir_alumno_por_id();
					List<String> grupos = db.obtener_nombre_grupos();
					List<Integer> idgrupos = db.obtener_grupos();
					boolean cambioGrupo = false;
					int opcionGrupo = 0;
					
					do {

						for (int i = 0; i < idgrupos.size(); i++) {
							int id_grupo = idgrupos.get(i);
							String nomGrupo = grupos.get(i);
							System.out.println(id_grupo + "- " + nomGrupo);
						}
						
						System.out.println("elige entre los grupos al que quieres que cambie el alumno:");
						opcionGrupo = prompt.nextInt();
						prompt.nextLine();

						if (!alumnoo.getGrupo().matches(grupos.get(opcionGrupo - 1))) {
							cambioGrupo = true;
						} else {
							System.out.println("has seleccionado el mismo grupo en el que ya esta!!!");
						}

					} while (!cambioGrupo);

					db.cambiar_alumno_grupo(alumnoo.getId_alumno(), opcionGrupo);

					break;
				case 13:
					
					
				case 14:
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
		} while (opcion != 14);

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
			List<Alumno> alumnos= eleccion_grupo(db.obtener_todos_los_alumnos());
			if (!db.guardar_Datos_En_Json(ruta,alumnos)) {
				System.out.println("Error al crear el Json.");
			}

		} else if (ruta.endsWith(".xml") || ruta.endsWith(".XML")) {
			
			List<Alumno> alumnos= eleccion_grupo(db.obtener_todos_los_alumnos());
			db.guardar_Datos_En_Xml(ruta,alumnos);
			
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

			List<Alumno>alumnos=db.leer_Datos_En_Xml(ruta);
			
			mostrar_alumnos(alumnos, true);
			System.out.println("XML leido correctamente");

		} else {
			System.out.println("Formato no valido de archivo");
		}

	}

	public List<Alumno> eleccion_grupo(List<Alumno> alumnos) {
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

		final String grupoFilter = grupoElegido;// por que si no lo pongo final, me da error para usarlo en el lamba.
		return alumnos.stream().filter(alumno -> alumno.getGrupo().equals(grupoFilter)).collect(Collectors.toList());
	}

	public void mostrar_alumnos(List<Alumno> alumnos, boolean todosLosCampos) {
		String grupo = null;
		for (Alumno alumno : alumnos) {
			if (grupo == null) {

				grupo = alumno.getGrupo();
				System.out.println("\nGrupo " + grupo + ":");

			} else if (!grupo.contains(alumno.getGrupo())) {

				grupo = alumno.getGrupo();
				System.out.println("\nGrupo " + grupo + ":");

			}
			if (todosLosCampos) {

				System.out.println(alumno.toString());
			} else {
				System.out.println(alumno.toString_campos_basicos());
			}
		}
		grupo = null;
	}

	public Alumno elegir_alumno_por_id() {
		alumnos = db.obtener_todos_los_alumnos();
		Alumno alumnoSelect = null;
		do {
			mostrar_alumnos(alumnos, false);

			System.out.println("Elige entre uno de los alumnos por su id:");
			int alumnoId = prompt.nextInt();
			prompt.nextLine();

			alumnoSelect = alumnos.stream().filter(alumno -> alumno.getId_alumno() == alumnoId).findFirst()
					.orElse(null);

			if (alumnoSelect == null) {
				System.out.println("no se ha encontrado un alumno con ese id");
			}

		} while (alumnoSelect == null);

		System.out.println("alumno encontrado:");
		alumnoSelect.toString();
		return alumnoSelect;
	}

}
