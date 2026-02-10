/**
 * 
 */
package juan.buques.modelos;

/**
 * 
 */
public class Capitan {
	
private int idCapitan,numBuques;
private double millas;
private String nombreCapi;

public Capitan(int idCapitan, int numBuques, double millas, String nombreCapi) {
	super();
	this.idCapitan = idCapitan;
	this.nombreCapi = nombreCapi;
	this.numBuques = numBuques;
	this.millas = millas;
}

/**
 * @param numBuques
 * @param millas
 * @param nombreCapi
 */
public Capitan(int numBuques, long millas, String nombreCapi) {
	super();
	this.numBuques = numBuques;
	this.millas = millas;
	this.nombreCapi = nombreCapi;
}

public int getNumBuques() {
	return numBuques;
}

public void setNumBuques(int numBuques) {
	this.numBuques = numBuques;
}

public double getMillas() {
	return millas;
}

public void setMillas(long millas) {
	this.millas = millas;
}

public String getNombreCapi() {
	return nombreCapi;
}

public void setNombreCapi(String nombreCapi) {
	this.nombreCapi = nombreCapi;
}




}
