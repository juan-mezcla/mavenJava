/**
 * 
 */
package examen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehiculos {
    
    private int totalVehiculos;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaExport = LocalDate.now().plusYears(1);

    private List<Vehiculo> vehiculos;

    public Vehiculos() { } 

    public Vehiculos(List<Vehiculo> vehiculos) {
        this.totalVehiculos = vehiculos.size();
        this.vehiculos = vehiculos;
    }

    public int getTotalVehiculos() {
        return totalVehiculos;
    }

    public void setTotalVehiculos(int totalVehiculos) {
        this.totalVehiculos = totalVehiculos;
    }

    public LocalDate getFechaExport() {
        return fechaExport;
    }

    public void setFechaExport(LocalDate fechaExport) {
        this.fechaExport = fechaExport;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }
}

