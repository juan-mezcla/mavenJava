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
	private LocalDate fechaExport=LocalDate.now().plusYears(1);
	private List<Vehiculo> vehiculos;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public Vehiculos(List<Vehiculo> vehiculos) {
		this.totalVehiculos=vehiculos.size();
		this.vehiculos=vehiculos;
	}

}
