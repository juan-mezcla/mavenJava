/**
 * 
 */
package examen;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String matricula, marca, modelo;
    private int km;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInspenccion;

    public Vehiculo() {}

    public Vehiculo(String matricula, String marca, String modelo, int km, LocalDate fechaInspenccion) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.km = km;
        this.fechaInspenccion = fechaInspenccion;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getKm() { return km; }
    public void setKm(int km) { this.km = km; }

    public LocalDate getFechaInspenccion() { return fechaInspenccion; }
    public void setFechaInspenccion(LocalDate fechaInspenccion) { this.fechaInspenccion = fechaInspenccion; }
}

