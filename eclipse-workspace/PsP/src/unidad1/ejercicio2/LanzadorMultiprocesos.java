package unidad1.ejercicio2;

import java.io.IOException;

public class LanzadorMultiprocesos {

	public static void main(String[] args) {
		if (args.length == 0)
		{
			System.out.println("Error: Falta indicar el comando o programa a ejectuar.");
			return;
		}
		
		try {
			ProcessBuilder pb = new ProcessBuilder(args);
			pb.inheritIO();
			Process process = pb.start();
			int exitCode = process.waitFor();
			
			System.out.println("\n-------------------------------------------");
            System.out.println("El proceso hijo ha finalizado.");
            System.out.println("Código de salida devuelto: " + exitCode);
            
            if (exitCode == 0) {
                System.out.println("Estado: Éxito (salida correcta / con eco).");
            } else {
                System.out.println("Estado: Error o respuesta negativa (sin eco / fallo).");
            }
            System.out.println("-------------------------------------------");
			
		} catch (IOException e) {
			System.err.println("Error al intentar ejecutar el comando indicado: " + e.getMessage());
		} catch (InterruptedException e) {
			System.err.println("El proceso fue interrumpido durante la espera: " + e.getMessage());
		}

	}

}
