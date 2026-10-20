package repasoExamen.unidad1;

import java.util.Properties;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Principal {
	
	public static void registrarLog(String rutaLog, String accion)
	{
		if (rutaLog == null || rutaLog.isBlank()) {
            return;
        }
		String timestamp =LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaLog, true))) {
			bw.write("[" + timestamp + "] " + accion);
			bw.newLine();
		} catch (IOException e) {
			System.err.println("Error al escribir en el fichero de log: " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Properties config = new Properties();
		File fConfig = new File("config.properties");
		
		try {
			if (!fConfig.exists())
			{
				try (FileOutputStream fos = new FileOutputStream(fConfig))
				{
					config.setProperty("fichero.dat", "alumnos.dat");
                    config.setProperty("ruta.log", "actividad.log");
                    config.store(fos, "Configuracion inicial de la aplicacion");
				}
			}
			try (FileInputStream fis = new FileInputStream("config.properties")) {
				config.load(fis);
			}
		} catch (IOException e) {
			System.err.println("Error al acceder a config.properties: " + e.getMessage());
			return;
		}
		
		String rutaDat = config.getProperty("fichero.dat", "alumnos.dat");
        String rutaLog = config.getProperty("ruta.log", "actividad.log");
		
		File fDat = new File(rutaDat);
		if (fDat.exists())
		{
			System.out.println("El fichero existe, mostrando información del fichero...");
			System.out.println("Ruta absoluta: " + fDat.getAbsolutePath());
			System.out.println("Tamaño actual: " + fDat.length() + " bytes.");
		} else {
            System.out.println("El fichero de datos '" + rutaDat + "' todavía no existe.");
        }
		
		try (Scanner sc = new Scanner(System.in)){
			int salida = 0;
			while (salida == 0)
			{
				System.out.println("\n--- MENÚ ---");
	            System.out.println("1. Dar de alta a un Alumno");
	            System.out.println("2. Consultar Alumnos");
	            System.out.println("3. Registro de Actividad");
	            System.out.println("0. Salir");
	            System.out.print("Elige una opción: ");
	            
	            int opcion;
	            try {
                    opcion = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Introduce un número válido.");
                    continue;
                }
	            
	            switch(opcion) {
	            	case 1:
	            		System.out.println("\nIntroduce el DNI del Alumno: ");
	            		String dni = sc.nextLine().trim();
	            		System.out.println("Introduce el nombre completo del Alumno: ");
	            		String nombre = sc.nextLine().trim();
	            		System.out.println("Introduce la nota media del Alumno: ");
	            		double nota = Double.parseDouble(sc.nextLine().trim());
	            		
	            		Alumno a = new Alumno(dni, nombre, nota);
	            		a.insertarAlumno(fDat);
	            		
	            		registrarLog(rutaLog, "ALTA: Alumno registrado -> " + dni + " (" + nombre + ")");
                        System.out.println("¡Alumno guardado correctamente!");
	            		
	            		break;
	            	case 2:
	            		System.out.println("\n---- LISTADO DE ALUMNOS ----");
	            		Alumno[] alumnos = Alumno.listar(fDat);
	            		for (Alumno alumno : alumnos)
	            		{
	            			System.out.println(alumno);
	            		}
	            		System.out.println("---- ------------------ ----\n");
	            		registrarLog(rutaLog, "CONSULTA: Listado de alumnos visualizado ("+ alumnos.length + " encontrados).");
	            		break;
	            	case 3:
	            		System.out.println("\n---- REGISTRO DE ACTIVIDAD ----");
	            		File fLog = new File(rutaLog);
	            		
	            		if (!fLog.exists())
	            		{
	            			System.out.println("Aún no existe ningún registro de actividad.");
	            		}
	            		else
	            		{
	            			try (BufferedReader br = new BufferedReader(new FileReader(fLog))){
								String linea;
								while ((linea = br.readLine()) != null)
								{
									System.out.println(linea);
								}
							} catch (IOException e) {
								System.err.println("Error al leer el log: " + e.getMessage());
							}
	            		}
	            		break;
	            	case 0:
	            		System.out.println("\nSaliendo del programa...");
	            		registrarLog(rutaLog, "SISTEMA: Cierre de la aplicacion");
	            		salida = 1;
	            		break;
	            	default:
	            		System.out.println("\nOpción no válida.");
	            }
	            
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}

}
