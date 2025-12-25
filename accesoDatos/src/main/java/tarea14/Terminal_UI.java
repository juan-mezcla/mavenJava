package tarea14;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Terminal_UI implements UserInterface {
private DataBaseInterface db;
private static Scanner prompt = new Scanner(System.in);
private  List<Alumno> alumnos = new ArrayList<Alumno>();
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
			System.out.println("10-Salir.");
			opcion = prompt.nextInt();

			prompt.nextLine();
			try {

				switch (opcion) {
				case 1:
                    db.insertar_Alumno(new Alumno());
					
					break;
				case 2:
                    alumnos = db.obtener_todos_los_alumnos();

                    alumnos.forEach(alumno -> {
                        System.out.println(alumno.toString());
                    });
					
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
                    String nombre=prompt.nextLine();
                    db.actualizar_Alumno_por_NIA(opcion, nombre);
					
					break;
				case 6:
                    System.out.println("Introduce el NIA del alumno que quieres eliminar:");

                     nia = prompt.nextInt();

                    db.eliminar_Alumno_por_NIA(nia);
					
					break;
				case 7:
                    System.out.println("Introduce el apellido de el alumno que quieres eliminar:");
                    nombre=prompt.nextLine();    

                    db.eliminar_Alumno_por_Apellido(nombre);
					
					break;
				case 8:
					guardar_datos_Xml_o_Json();
					break;
				case 9:
					leer_datos_Xml_o_Json();//FALLA JSON CAGO EN DEU
					break;
				case 10:
					System.out.println("Fin del programa");
					db.cerrarConexion();
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

    private void guardarEnFichero() {
		System.out.println("introduce la ruta donde quieres que se guarden los datos (tiene que ser un .txt):");
		String ruta = prompt.nextLine();
		if (ruta.endsWith(".txt")) {
			db.guardar_Datos_En_Fichero(ruta);
		} else {
			System.out.println("Tiene que ser en formato .txt el fichero");
		}
	}

	private void mandar_datos_Fichera_a_BD() {// hacer primero lo de eliminar para hacer prueba
		System.out.println("introduce la ruta del fichero que contiene los datos:");
		String ruta = prompt.nextLine();
		if (ruta.endsWith(".txt")) {
			db.mandar_Datos_De_Fichero_A_BD(ruta);
		} else {
			System.out.println("Tiene que ser en formato .txt el fichero");
		}
	}
	
	private void guardar_datos_Xml_o_Json() {
		System.out.println("Introduce la ruta y el nombre del archivo (SOLO se admiten archivos .xml o .json):");
		String ruta=prompt.nextLine();
		
		if(ruta.endsWith(".json") || ruta.endsWith(".JSON")) {
			
			if(!db.guardar_Datos_En_Json(ruta)) {
				 System.out.println("Error al crear el Json.");
			}
			
		}else if(ruta.endsWith(".xml") || ruta.endsWith(".XML")) {
			
			db.guardar_Datos_En_Xml(ruta);
			System.out.println("XML creado correctamente");
			
		}else {
			System.out.println("Formato no valido de archivo");
		}
		
	}
	
	private void leer_datos_Xml_o_Json() {
		System.out.println("Introduce la ruta y el nombre del archivo (SOLO se admiten archivos .xml o .json):");
		String ruta=prompt.nextLine();
		
		if(ruta.endsWith(".json") || ruta.endsWith(".JSON")) {
			
			if(!db.leer_Datos_En_Json(ruta)) {
				 System.out.println("Error al crear el Json.");
			}
			
		}else if(ruta.endsWith(".xml") || ruta.endsWith(".XML")) {
			
			db.guardar_Datos_En_Xml(ruta);
			System.out.println("XML creado correctamente");
			
		}else {
			System.out.println("Formato no valido de archivo");
		}
		
	}
   
}
