package tarea16.interfaces;

import java.util.List;

import tarea16.Alumno;

public interface DataBaseInterface {

	void insertar_Alumno(Alumno alumno);
	
	void crear_grupo(String nombreGrupo);
	
	List<Integer> obtener_grupos();
	
	Alumno obtener_grupo_alumno(Alumno alumno);

	List<Alumno> obtener_todos_los_alumnos();
	
	Alumno datos_alumno_por_NIA(int nia);
	
	void cambiar_alumno_grupo(int id_alumno, int grupoNuevo);

	void actualizar_Alumno_por_NIA(int nia, String alumno);

	void eliminar_Alumno_por_NIA(int nia);

	void eliminar_Alumno_por_Apellido(String apellido);

	void mandar_Datos_De_Fichero_A_BD(String ruta);

	void guardar_Datos_En_Fichero(String ruta);

	void guardar_Datos_En_Xml(String ruta,List<Alumno> alumnos);

	boolean guardar_Datos_En_Json(String ruta,List<Alumno>alumnos);

	boolean leer_Datos_En_Json(String ruta);

	List<Alumno> leer_Datos_En_Xml(String ruta);
	
	List<String> obtener_nombre_grupos();

	void eliminar_Alumnos_por_grupo(int grupoElegido);
}
