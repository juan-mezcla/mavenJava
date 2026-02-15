/**
 * 
 */
package practicaHibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class GestorBiblioteca {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public GestorBiblioteca() {
        this.emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        this.em = emf.createEntityManager();
    }
    
    // CREATE: Guardar un autor con libros
    public void guardarAutorConLibros() {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            // Crear autor
            Autor autor = new Autor("Gabriel García Márquez", "gabriel@ejemplo.com");
            autor.setLibros(new ArrayList<>());
            
            // Crear libros
            Libro libro1 = new Libro("Cien años de soledad", "ISBN-001", 1967, autor);
            Libro libro2 = new Libro("El amor en los tiempos del cólera", "ISBN-002", 1985, autor);
            
            // Agregar libros al autor
            autor.getLibros().add(libro1);
            autor.getLibros().add(libro2);
            
            // Guardar solo el autor (los libros se guardan en cascada)
            em.persist(autor);
            
            tx.commit();
            System.out.println("✓ Autor y libros guardados correctamente");
            
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
    
    // READ: Obtener un autor por ID con sus libros
    public Autor obtenerAutor(Long id) {
        try {
            Autor autor = em.find(Autor.class, id);
            if (autor != null) {
                System.out.println("✓ Autor encontrado: " + autor);
                System.out.println("  Libros:");
                for (Libro libro : autor.getLibros()) {
                    System.out.println("    - " + libro.getTitulo());
                }
            }
            return autor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // READ: Obtener todos los autores
    public List<Autor> obtenerTodosAutores() {
        try {
            TypedQuery<Autor> query = em.createQuery(
                "SELECT a FROM Autor a", Autor.class
            );
            List<Autor> autores = query.getResultList();
            System.out.println("✓ Total de autores: " + autores.size());
            return autores;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // READ: Obtener todos los libros de un autor
    public List<Libro> obtenerLibrosAutor(Long autorId) {
        try {
            TypedQuery<Libro> query = em.createQuery(
                "SELECT l FROM Libro l WHERE l.autor.id = :autorId", Libro.class
            );
            query.setParameter("autorId", autorId);
            List<Libro> libros = query.getResultList();
            System.out.println("✓ Total de libros: " + libros.size());
            for (Libro libro : libros) {
                System.out.println("  - " + libro.getTitulo() + " (" + libro.getAñoPublicacion() + ")");
            }
            return libros;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // UPDATE: Actualizar un autor
    public void actualizarAutor(Long id, String nuevoNombre, String nuevoEmail) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Autor autor = em.find(Autor.class, id);
            if (autor != null) {
                autor.setNombre(nuevoNombre);
                autor.setEmail(nuevoEmail);
                em.merge(autor);
                System.out.println("✓ Autor actualizado: " + autor.getNombre());
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
    
    // DELETE: Eliminar un autor (eliminará sus libros en cascada)
    public void eliminarAutor(Long id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Autor autor = em.find(Autor.class, id);
            if (autor != null) {
                em.remove(autor);
                System.out.println("✓ Autor eliminado junto con sus libros");
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
    
    // DELETE: Eliminar un libro
    public void eliminarLibro(Long id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Libro libro = em.find(Libro.class, id);
            if (libro != null) {
                em.remove(libro);
                System.out.println("✓ Libro eliminado");
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
    
    // Cerrar conexión
    public void cerrar() {
        em.close();
        emf.close();
    }
}
