package accesoDatos.tarea11;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Alumno implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int nia;
	private String nombre,apellidos,ciclo,curso,grupo;
	private char genero;
	private LocalDate fecha;
	
	static DateTimeFormatter formatoFech=DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	static int acumulador=1;
	
	public Alumno() {
		this.nia = acumulador;
		this.nombre ="nombre"+acumulador;
		this.apellidos = "apellidos"+acumulador;
		this.ciclo = "ciclo"+acumulador;
		this.curso = "curso"+acumulador;
		this.grupo = "grupo"+acumulador;
		this.genero =(acumulador%2==0)?'M':'F' ;
		this.fecha = LocalDate.now().plusMonths(acumulador);
		acumulador++;
	}
	
	@Override
	public String toString() {
		String fechaFormat=fecha.format(this.formatoFech);
		return "Alumno [nia=" + nia + ", nombre=" + nombre + ", apellidos=" + apellidos + ", ciclo=" + ciclo
				+ ", curso=" + curso + ", grupo=" + grupo + ", genero=" + genero + ", fecha=" + fechaFormat + "]";
	}

	public Alumno(int nia, String nombre, String apellidos, String ciclo, String curso, String grupo, char genero,
			LocalDate fecha) {
		
		this.nia = nia;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.ciclo = ciclo;
		this.curso = curso;
		this.grupo = grupo;
		this.genero = genero;
		this.fecha = fecha;
	}
	
	public int getNia() {
		return nia;
	}
	public void setNia(int nia) {
		this.nia = nia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getCiclo() {
		return ciclo;
	}
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getGenero() {
		return String.valueOf(genero);
	}
	public void setGenero(char genero) {
		this.genero = genero;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	
	public String getFechaString() {
		String fechaFormat=fecha.format(this.formatoFech);
		return fechaFormat;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
}


