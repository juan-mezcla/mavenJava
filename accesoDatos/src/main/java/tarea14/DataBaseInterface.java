package tarea14;

import java.util.List;

public interface DataBaseInterface {

    void insertar_Alumno(Alumno alumno);

    List<Alumno> obtener_todos_los_alumnos();

    void actualizar_Alumno_por_NIA(int NIA, String alumno);

    void eliminar_Alumno_por_NIA(int NIA);

    void eliminar_Alumno_por_Apellido(String apellido);

    void cerrarConexion();

    void mandar_Datos_De_Fichero_A_BD(String ruta);

    void guardar_Datos_En_Json(String ruta);

    void guardar_Datos_En_Fichero(String ruta);

    void guardar_Datos_En_Xml(String ruta);

    void leer_Datos_Xml_o_Json();

    void leer_Datos_En_Json(String ruta);

    void leer_Datos_En_Xml(String ruta);
     
    void mandar_Datos_De_Json_A_BD(String ruta);

    void mandar_Datos_De_Xml_A_BD(String ruta);

    

}
