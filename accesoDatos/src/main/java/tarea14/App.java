package tarea14;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App {
	private static DataBaseInterface conexion = new DatabaseManager();

	public static void main(String[] args) {
		Terminal_UI ui = new Terminal_UI(conexion);
		ui.menu(conexion);
		conexion.cerrarConexion();
	}



}
