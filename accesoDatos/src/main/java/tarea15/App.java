package tarea15;

import tarea15.db.DatabaseManager;
import tarea15.interfaces.DataBaseInterface;
import tarea15.interfaces.UserInterface;

public class App {

	public static void main(String[] args) {
		DataBaseInterface conexion = new DatabaseManager();
		UserInterface ui = new Terminal_UI(conexion);
		
		new App().ejecutar(conexion, ui);
		
	}
	
	public void ejecutar(DataBaseInterface db,UserInterface ui) {
		ui.menu(db);
	}
}
