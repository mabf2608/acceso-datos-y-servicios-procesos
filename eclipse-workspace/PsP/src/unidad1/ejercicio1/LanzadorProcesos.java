package unidad1.ejercicio1;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;


public class LanzadorProcesos {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try 
		{
			String home = System.getProperty("user.home");
			File fApuntes = new File(home, "apuntes.txt");

			if (!fApuntes.exists()) {
			    fApuntes.createNewFile();
			}
			
			ProcessBuilder pbNotepad = new ProcessBuilder("notepad.exe", fApuntes.getAbsolutePath());
			ProcessBuilder pbCalc = new ProcessBuilder("calc.exe");
			
			pbCalc.start();
			pbNotepad.start();
		
			System.out.println("Introduce el programa o comando a ejecutar (ej: ping google.com)");
			String input = sc.nextLine().trim();
			
			if(input.isEmpty())
			{
				System.out.println("No se ha introducido ningún comando.");
				return;
			}
			
			String[] command = input.split("\\s+");
			ProcessBuilder pbIn = new ProcessBuilder(command);
			
			pbIn.inheritIO();
			
			Process processIn = pbIn.start();
			int exitCode = processIn.waitFor();
			System.out.println("Codigo de salida: "+exitCode);
		}
		catch (IOException e)
		{
			System.err.println("Error de entrada/salida al ejecutar el proceso: " + e.getMessage());
			
		}
		catch (InterruptedException e)
		{
			System.err.println("El hilo principal de Java fue interrumpido mientras esperaba.");
		}finally {sc.close();}
	}
}
