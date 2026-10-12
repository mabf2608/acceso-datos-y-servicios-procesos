package unidad1.ejercicio5;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Ejercicio5 {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			int contador = 1;
			
			while (true) {
				System.out.println("Introduce el comando o subproceso a ejecutar (o escribe salir para cerrar el programa): ");
				String input = sc.nextLine().trim();
				
				if(input.equalsIgnoreCase("salir")) {
					
					System.out.println("Saliendo del programa...");
					break;
				}
				
				if(input.isEmpty()) {
					System.out.println("No has introducido ningún comando.");
					continue;
				}
				try {
					ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", input);
					
					File fSalida = new File("salida" + contador + ".txt");
					File fErrores = new File("errores" + contador + ".txt");
					
					pb.redirectOutput(fSalida);
					pb.redirectError(fErrores);
					
					Process p = pb.start();
					int exitCode = p.waitFor();
					
					System.out.println("Proceso #" + contador + " ejecutado. Código de salida: " + exitCode);
				    System.out.println("Archivos generados: " + fSalida.getName() + " y " + fErrores.getName());

				    contador++;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
