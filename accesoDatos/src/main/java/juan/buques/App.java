package juan.buques;

import juan.buques.db.BuquesDAOImpl;
import juan.buques.interfaces.BuquesDAO;
import juan.buques.interfaces.VistaDAO;
import juan.buques.vistas.consola;

public class App {
  public static void main(String[] args) {
    BuquesDAO db=new BuquesDAOImpl();
    VistaDAO vista=new consola();
    
    Controlador controller=new Controlador(db, vista);
    controller.ejecutar();
  }
}
