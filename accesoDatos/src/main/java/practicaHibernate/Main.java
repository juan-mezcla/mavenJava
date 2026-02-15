/**
 * 
 */
package practicaHibernate;

/**
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GestorBiblioteca gestor = new GestorBiblioteca();
        
        // 1. Guardar autor con libros
        System.out.println("=== CREAR ===");
        gestor.guardarAutorConLibros();
        
        // 2. Consultar todos los autores
        System.out.println("\n=== LEER TODOS ===");
        gestor.obtenerTodosAutores();
        
        // 3. Obtener un autor específico
        System.out.println("\n=== LEER UN AUTOR ===");
        gestor.obtenerAutor(1L);
        
        // 4. Obtener libros de un autor
        System.out.println("\n=== LEER LIBROS DE UN AUTOR ===");
        gestor.obtenerLibrosAutor(1L);
        
        // 5. Actualizar autor
        System.out.println("\n=== ACTUALIZAR ===");
        gestor.actualizarAutor(1L, "Gabriel García Márquez (Actualizado)", "nuevo@ejemplo.com");
        
        // 6. Eliminar un libro
        System.out.println("\n=== ELIMINAR LIBRO ===");
        gestor.eliminarLibro(1L);
        
        gestor.cerrar();
	}

}
