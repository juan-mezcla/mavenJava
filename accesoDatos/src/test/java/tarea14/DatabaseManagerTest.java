package tarea14;

import java.time.LocalDate;
import java.util.List;

import junit.framework.TestCase;

public class DatabaseManagerTest extends TestCase {

	private DataBaseInterface database;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		database = new DatabaseManager();
	}

	public void testInsertarYObtenerAlumno() {
		Alumno alumno = new Alumno(10, "Juan", "Pérez", 'M', LocalDate.of(2000, 5, 10), "DAM", "2", "A");

		database.insertar_Alumno(alumno);

		List<Alumno> alumnos = database.obtener_todos_los_alumnos();

		assertNotNull(alumnos);
		assertFalse(alumnos.isEmpty());
	}

	public void testActualizarAlumnoPorNIA() {
		Alumno alumno = new Alumno(6, "Ana", "López", 'F', LocalDate.of(2001, 3, 22), "DAW", "1", "B");

		database.insertar_Alumno(alumno);

		database.actualizar_Alumno_por_NIA(2, "Ana García");

		List<Alumno> alumnos = database.obtener_todos_los_alumnos();
		Alumno actualizado = null;

		for (Alumno a : alumnos) {
			if (a.getNia() == 2) {
				actualizado = a;
				break;
			}
		}

		assertNotNull(actualizado);
		assertEquals("Ana García", actualizado.getNombre());
	}

	public void testEliminarAlumnoPorNIA() {
		Alumno alumno = new Alumno(11, "Luis", "Martín", 'M', LocalDate.of(1999, 11, 3), "ASIR", "2", "C");

		database.insertar_Alumno(alumno);
		database.eliminar_Alumno_por_NIA(3);

		List<Alumno> alumnos = database.obtener_todos_los_alumnos();

		for (Alumno a : alumnos) {
			assertTrue(a.getNia() != 3);
		}
	}

	public void testEliminarAlumnoPorApellido() {
		Alumno alumno = new Alumno(20, "Marta", "Ruiz", 'F', LocalDate.of(2002, 7, 15), "DAM", "1", "A");

		database.insertar_Alumno(alumno);
		database.eliminar_Alumno_por_Apellido("Ruiz");

		List<Alumno> alumnos = database.obtener_todos_los_alumnos();

		for (Alumno a : alumnos) {
			assertFalse(a.getApellidos().equals("Ruiz"));
		}
	}

	public void testGuardarYLeerJson() {
		Alumno alumno = new Alumno(26, "Carlos", "Sánchez", 'M', LocalDate.of(2000, 1, 1), "DAW", "2", "D");

		database.insertar_Alumno(alumno);

		assertTrue(database.guardar_Datos_En_Json("alumnos.json"));
		assertTrue(database.leer_Datos_En_Json("alumnos.json"));
	}
}
