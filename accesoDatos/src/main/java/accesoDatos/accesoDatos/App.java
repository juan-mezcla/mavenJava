package accesoDatos.accesoDatos;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class App 
{
	 public static void main(String[] args) {

	        File archJson = new File("alumnos.json");

	        try {
	            if (!archJson.exists()) {
	                archJson.createNewFile();
	            }

	            Gson gson = new GsonBuilder().setPrettyPrinting().create();

	            
	            List<Alumno> listaAlumnos = new ArrayList<>();

	            for (int i = 0; i < 5; i++) {
	                listaAlumnos.add(new Alumno()); 
	            }

	            
	            try (FileWriter escribirJson = new FileWriter(archJson)) {
	                gson.toJson(listaAlumnos, escribirJson);
	            }

	            System.out.println("Archivo JSON creado correctamentee.");
	            
	            
	            try (FileReader reader = new FileReader("alumnos.json")) {

	               
	                List<Alumno> alumnos = gson.fromJson(reader, new TypeToken<List<Alumno>>(){}.getType());

	            
	                for (Alumno a : alumnos) {
	                    System.out.println(a);
	                }

	            } catch (IOException e) {
	                e.printStackTrace();
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
