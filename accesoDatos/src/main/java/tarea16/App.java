package tarea16;

import tarea16.db.DatabaseManager;
import tarea16.interfaces.DataBaseInterface;

public class App {
	private static DataBaseInterface conexion = new DatabaseManager();

	public static void main(String[] args) {
		Terminal_UI ui = new Terminal_UI(conexion);
		ui.menu(conexion);
		
	}
}
