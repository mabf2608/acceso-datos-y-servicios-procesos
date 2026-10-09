package unidad1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestionProductos {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Introduce el nombre del fichero (ej: productos.txt): ");
			String nombreFichero = sc.nextLine();
			//Esto inspecciona el fichero
			File fichero = new File(nombreFichero);
			if(fichero.exists()) {
				System.out.println("El fichero existe, mostrando información del fichero...");
				System.out.println("Ruta absoluta: " + fichero.getAbsolutePath());
				System.out.println("Tamaño actual: " + fichero.length() + " bytes.");
			}else {
				System.out.println("\nEl fichero todavía no existe, se creará al realizar la primera alta.");
			}
			
			int salida = 0;
			while (salida == 0) {
				System.out.println("\n--- MENÚ ---");
                System.out.println("1. Dar de alta un producto");
                System.out.println("2. Consultar productos");
                System.out.println("3. Salir");
                System.out.print("Elige una opción: ");
                int opcion = Integer.parseInt(sc.nextLine());
				
                switch (opcion) {
                
                	case 1:
	                	System.out.println("\nIntroduce el nombre del producto: ");
	                	String nombreProducto = sc.nextLine();    
	                	
	                	try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero, true))) {
	                		bw.write(nombreProducto);
	                		bw.newLine();
	                		System.out.println("\nProducto guardado con éxito!");
	                	}
	                	break;
                	case 2:
                		System.out.println("\n------Listado de productos------");
                		try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))){
                			String linea;
                			while((linea = br.readLine()) != null) {
                				System.out.println(linea);
                			}
                		}
                		System.out.println("--------------------------------");
            			break;
                	case 3:
                		System.out.println("\nSaliendo del programa...");
                		salida = 1;
                		break;
                	default:
                		System.out.println("\nOpción no válida.");
                }
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
}
