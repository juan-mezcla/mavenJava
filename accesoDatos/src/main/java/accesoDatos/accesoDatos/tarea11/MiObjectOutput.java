package accesoDatos.accesoDatos.tarea11;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MiObjectOutput extends ObjectOutputStream {
	
	public MiObjectOutput(OutputStream archEscribir) throws IOException {
		super(archEscribir);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		// Evita escribir una nueva cabecera cuando el archivo ya tiene datos.
		reset();
	}

}
