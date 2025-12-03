/**
 * 
 */
package ra_2;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehiculo implements Serializable {
	private String matricula,marca,modelo;
	private int km;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fechaInspenccion;
	private static int cont=1;
	
	
	/**
	 * 
	 */
	public Vehiculo() {
		this.matricula = "matricula"+cont;
		this.marca = "marca"+cont;
		this.modelo = "modelo"+cont;
		this.km = 10000*cont;
		this.fechaInspenccion = LocalDate.now().minusYears(cont);
		cont++;
	}
	public Vehiculo(String matricula, String marca, String modelo, int km, LocalDate fechaInspenccion) {
		super();
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.km = km;
		this.fechaInspenccion = fechaInspenccion;
	}
	@Override
	public String toString() {
		return matricula + ";" + marca + ";" + modelo + ";" + km+ ";" + fechaInspenccion.toString();
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public int getKm() {
		return km;
	}
	public void setKm(int km) {
		this.km = km;
	}
	public LocalDate getFechaInspenccion() {
		return fechaInspenccion;
	}
	public void setFechaInspenccion(LocalDate fechaInspenccion) {
		this.fechaInspenccion = fechaInspenccion;
	}
	public static int getCont() {
		return cont;
	}
	public static void setCont(int cont) {
		Vehiculo.cont = cont;
	}
	
	

}
