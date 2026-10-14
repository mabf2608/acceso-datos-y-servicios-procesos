package unidad1.ejercicioFinal;

import java.util.Properties;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ImportarUsuarios {

	public static void main(String[] args) {
		Properties config = new Properties();
		
		try (FileInputStream fis = new FileInputStream("config.properties")) {
		    config.load(fis);
		} catch (FileNotFoundException e) {
		    System.err.println("No se encontró config.properties: " + e.getMessage());
		    return;
		} catch (IOException e) {
		    e.printStackTrace();
		    return;
		}
		
		String rutaCsv = config.getProperty("data.filepath");
        if (rutaCsv == null || rutaCsv.isBlank()) {
            System.err.println("Error: La propiedad 'data.filepath' no está definida en config.properties");
            return;
        }
		
		try (BufferedReader br = new BufferedReader(new FileReader(rutaCsv));
				BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios_activos.txt"))){
			String linea;
			while((linea = br.readLine()) != null) {
				
				if (linea.isBlank()) {
                    continue;
                }
				
				String[] campos = linea.split(",");
				
				if(campos.length >= 4 && campos[3].trim().equalsIgnoreCase("true")) {
					String salida = String.format("[%s] %s - %s", campos[0].trim(), campos[1].trim(), campos[2].trim());
					System.out.println(salida);
					bw.write(salida);
					bw.newLine();
				}
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("No se pudo encontrar el archivo CSV: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
