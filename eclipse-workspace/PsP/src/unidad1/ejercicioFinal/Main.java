package unidad1.ejercicioFinal;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {

	public static void main(String[] args) {
		
		String[] empleados = {"Federico", "Amalia", "Miguel Angel"};
		
		Process[] filas = new Process[3];
		
		String classpath = System.getProperty("java.class.path");
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("registro.txt"))){
			
			bw.write("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			for (int i = 0; i < filas.length; i++) {
				
				ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, "unidad1.ejercicioFinal.Atender", empleados[i]);
				
				pb.inheritIO();
				
				filas[i] = pb.start();
			
			}
			
			for (int i = 0; i < filas.length; i++)
			{
				filas[i].waitFor();
			}
			
			System.out.println("Todos los empleados han terminado de atender.");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

}