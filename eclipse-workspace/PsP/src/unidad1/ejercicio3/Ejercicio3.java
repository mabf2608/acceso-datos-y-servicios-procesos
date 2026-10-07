package unidad1.ejercicio3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Ejercicio3 {

	public static void main(String[] args) {
		try {
			ProcessBuilder pb = new ProcessBuilder("java", "-jar", "clientemusical.jar", "el fary");
			Process pMusical = pb.start();
			
			try (BufferedReader brMusical = new BufferedReader(new InputStreamReader(pMusical.getInputStream())))
			{
				String lineaMusical;
				while((lineaMusical = brMusical.readLine()) != null) 
				{
					System.out.println(lineaMusical);
				}
			}
			pMusical.waitFor();
			
			ProcessBuilder pbPy = new ProcessBuilder("python", "frases.py");
			Process pPy = pbPy.start();
			
			try (BufferedReader brPy = new BufferedReader(new InputStreamReader(pPy.getInputStream()))) 
			{
				String lineaPy;
				while((lineaPy = brPy.readLine()) != null)
				{
					System.out.println(lineaPy);
				}
			}
			pPy.waitFor();			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
