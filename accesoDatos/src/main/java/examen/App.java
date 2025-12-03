package examen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class App {
	private Scanner prompt = new Scanner(System.in);
	private static List<Vehiculo> vehiculos=new ArrayList<Vehiculo>();
	
	public static void main(String[] args) {
		for(int i=0; i<10; i++) {
			vehiculos.add(new Vehiculo());
		}
		new App().menu();
	}

	private void menu() {
		int opcion = 0;

		do {
			System.out.println("1-Escribir datos en CSV.");
			System.out.println("2-leer datos de CSV.");
			System.out.println("3-Pasar CSV a JSON.");
			System.out.println("4-Pasar CSV a DAT.");
			System.out.println("5-Salir.");
			System.out.println("Elige una opcion");
			opcion=prompt.nextInt();
			prompt.nextLine();
			switch (opcion) {
			case 1:
				escribirDatosEnCSV();
				break;

			case 2:
				leerDatosEnCSV();
				break;

			case 3:
				pasarCSV_A_JSON();
				break;

			case 4:
				pasarCSV_A_DAT();
				break;
			case 5:
				System.out.println("Hasta luego");
				break;
			default:
				System.out.println("fuera de rango");
				break;
			}

		} while (opcion != 5);
	}

	private void escribirDatosEnCSV() {
		System.out.println("Introduce la ruta del archivo en el que vas a dejar los datos:");
		String ruta=prompt.nextLine();
		
		File arch=comprobarArchivo(ruta);
		
		try {
			FileWriter escribirArch=new FileWriter(arch);
			
			BufferedWriter escribir=new BufferedWriter(escribirArch);
			
			vehiculos.forEach(v->{
				try {
					escribir.write(v.toString()+"\n");
				} catch (IOException e) {
					System.out.println("ERROR al escribir los datos en foreach");
					e.printStackTrace();
				}
			});
			
			escribir.close();
			escribirArch.close();
			
			System.out.println("Datos introducidos correctamente");
		} catch (IOException e) {
			System.out.println("ERROR al utilizar el fichero");
			e.printStackTrace();
		}
	}

	private void leerDatosEnCSV() {
		System.out.println("Introduce la ruta del archivo en el que quieres leer:");
		String ruta=prompt.nextLine();
		
		File arch=comprobarArchivo(ruta);
		
		try {
			FileReader leerArch=new FileReader(arch);
			
			BufferedReader leer=new BufferedReader(leerArch);
			
			String linea;
			while ((linea=leer.readLine())!=null) {
				
				System.out.println(linea);
			}
			
			leer.close();
			leerArch.close();
			
			System.out.println("Datos leidos correctamente");
		} catch (IOException e) {
			System.out.println("ERROR al utilizar el fichero");
			e.printStackTrace();
		}
	}
	
	private void pasarCSV_A_JSON() {
		System.out.println("Introduce la ruta del archivo .json:");
		String rutaJSON=prompt.nextLine();
		
		System.out.println("Introduce la ruta del archivo .txt:");
		String rutaCSV=prompt.nextLine();
		
		File archJSON=comprobarArchivo(rutaJSON);
		File archCSV=comprobarArchivo(rutaCSV);
		try {
			FileReader leerArch=new FileReader(archCSV);
			
			BufferedReader leer=new BufferedReader(leerArch);
			
			
			List <Vehiculo> vehiculosCSV=new ArrayList<Vehiculo>();
			String linea;
			while ((linea=leer.readLine())!=null) {
				
				String filaVehiculo[]=linea.split(";");
				Vehiculo vehiculo=new Vehiculo(filaVehiculo[0], filaVehiculo[1], filaVehiculo[2],Integer.parseInt(filaVehiculo[3]), LocalDate.parse(filaVehiculo[4]));
				
				vehiculosCSV.add(vehiculo);
				
			}
			
			leer.close();
			leerArch.close();
			ObjectMapper mapper=new ObjectMapper();
			
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			
			Vehiculos datosVehiculos=new Vehiculos(vehiculosCSV);
			
			mapper.writeValue(archJSON,datosVehiculos);
			
			
			System.out.println("Datos pasados a .Json correctamente");
		
		} catch (IOException e) {
			System.out.println("ERROR al utilizar el fichero");
			e.printStackTrace();
		}
	}
	
	private File comprobarArchivo(String ruta) {
		File arch=new File(ruta);
		
		if(arch.isDirectory()) {
			arch=null;
		}else if(arch.exists()) {
			try {
				arch.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return arch;
	}
	
	private void pasarCSV_A_DAT() {
		System.out.println("Introduce la ruta del archivo .dat:");
		String rutaDAT=prompt.nextLine();
		
		System.out.println("Introduce la ruta del archivo .txt:");
		String rutaCSV=prompt.nextLine();
		
		File archDAT=comprobarArchivo(rutaDAT);
		File archCSV=comprobarArchivo(rutaCSV);
		try {
			FileReader leerArch=new FileReader(archCSV);
			
			BufferedReader leer=new BufferedReader(leerArch);
			
			FileOutputStream escribir= new FileOutputStream(archDAT,true);
			
			ObjectOutputStream objArch=new ObjectOutputStream(escribir);
			
			
			String linea;
			while ((linea=leer.readLine())!=null) {
				String filaVehiculo[]=linea.split(";");
				Vehiculo vehiculo=new Vehiculo(filaVehiculo[0], filaVehiculo[1], filaVehiculo[2],Integer.parseInt(filaVehiculo[3]), LocalDate.parse(filaVehiculo[4]));
				
				
				objArch.writeObject(vehiculo);
				
				
			}
			
			objArch.close();
			escribir.close();
			leer.close();
			leerArch.close();
			System.out.println("Datos pasados a .Dat correctamente");
		} catch (IOException e) {
			System.out.println("ERROR al utilizar el fichero");
			e.printStackTrace();
		}
	}

}
