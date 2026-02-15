/**
 * 
 */
package practicaHibernate;

import javax.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "titulo", nullable = false)
    private String titulo;
    
    @Column(name = "isbn", unique = true)
    private String isbn;
    
    @Column(name = "año_publicacion")
    private Integer añoPublicacion;
    
    // Relación ManyToOne: muchos Libros pertenecen a un Autor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;
    
    // Constructores
    public Libro() {}
    
    public Libro(String titulo, String isbn, Integer añoPublicacion, Autor autor) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.añoPublicacion = añoPublicacion;
        this.autor = autor;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public Integer getAñoPublicacion() {
        return añoPublicacion;
    }
    
    public void setAñoPublicacion(Integer añoPublicacion) {
        this.añoPublicacion = añoPublicacion;
    }
    
    public Autor getAutor() {
        return autor;
    }
    
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", añoPublicacion=" + añoPublicacion +
                '}';
    }
}
