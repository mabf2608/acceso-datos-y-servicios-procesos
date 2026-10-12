package unidad1.ejercicio2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class GestionPropiedades {

	private static Scanner sc = new Scanner(System.in);
	private static final String FICHERO = "config.properties";
	private static Properties ps = new Properties();
	
	public static void main(String[] args) {
		
		File f = new File(FICHERO);
		if (!f.exists()) {
			ps.setProperty("usuario", "admin");
			ps.setProperty("puerto", "8080");
			ps.setProperty("idioma", "es");
			ps.setProperty("server.host", "localhost");
			ps.setProperty("server.port", "8080");
			ps.setProperty("server.timeout", "3000");
			ps.setProperty("db.url", "jdbc:mysql://localhost:3306/mi_base_datos");
			ps.setProperty("db.user", "admin");
			ps.setProperty("db.password", "Secret123!");
			ps.setProperty("app.version", "1.0.0");
			
			try (FileWriter fw = new FileWriter(f)){
				ps.store(fw, "Mi configuración.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			
			try (FileReader fr = new FileReader(f)){
				ps.load(fr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int opcion = -1;
		
		do {
		
			System.out.println("--- MENU ---");
			System.out.println("1. Consulta una variable.");
			System.out.println("2. Actualiza el valor de una variable.");
			System.out.println("3. Añadir una variable nueva.");
			System.out.println("0. Salir");
			System.out.println("------------");
			opcion = Integer.parseInt(sc.nextLine());
			
			switch(opcion) {
				case 1:
					consultarVariable();
					break;
				case 2:
					actualizarVariable();
					break;
				case 3:
					anadirVariable();
					break;
				case 0:
					System.out.println("Saliendo del programa...");
					opcion = 0;
					break;
				
				default:
					System.out.println("Opción no válida, introduce un número del 0 al 3.");
			}
			
			
		}while(opcion != 0);
	}
	private static void guardarCambios(String comentario) {
		File f = new File(FICHERO);
		try (FileWriter fw = new FileWriter(f)){
			ps.store(fw, comentario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void consultarVariable() {
		System.out.println("Introduce la clave de la variable que quieres consultar: ");
		String clave = sc.nextLine();
		
		if (ps.containsKey(clave))
		{
			System.out.println(String.format("El valor de la clave %s es %s.", clave, ps.getProperty(clave)));
		}else {
			System.out.println(String.format("La clave %s no existe, vuelva a intentarlo.", clave));
		}	
	}
	
	private static void actualizarVariable() {
		System.out.println("Introduce la clave de la variable que quieres actualizar: ");
		String clave = sc.nextLine();
		
		if (ps.containsKey(clave))
		{
			System.out.println("Introduce el nuevo valor de la clave: ");
			String nuevoValor = sc.nextLine();
			ps.setProperty(clave, nuevoValor);
			System.out.println("Introduce el comentario que deseas poner en la actualización: ");
			String comentario = sc.nextLine();
			guardarCambios(comentario);
		}else {
			System.out.println(String.format("La clave %s no existe, así que no se puede modificar su valor.", clave));
		}	
	}
	
	private static void anadirVariable() {
		System.out.println("Introduce el nombre de la nueva varible (clave): ");
		String clave = sc.nextLine();

		if (ps.containsKey(clave)) {
			System.out.println(String.format("La clave %s ya existe, así que no se puede anadir un nuevo valor, prueba a modificarlo.", clave));
		} else {
			System.out.println("Introduce el  valor de la clave: ");
			String valor = sc.nextLine();
			
			ps.setProperty(clave, valor);
			System.out.println("Introduce el comentario que deseas poner en la actualización: ");
			String comentario = sc.nextLine();
			guardarCambios(comentario);
		}
	}
}
