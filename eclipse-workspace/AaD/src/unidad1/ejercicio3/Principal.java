package unidad1.ejercicio3;

import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		
		Vehiculo v1 = new Vehiculo("5940FXG", "Volkswagen Polo 9n 2005", 2500.0);
		Vehiculo v2 = new Vehiculo("4270CPZ", "Mazda Cx-30", 29500.99);
		Vehiculo v3 = new Vehiculo("5678HRH", "Seat Ibiza 2011", 5700.0);
		
		v1.grabar();
		v2.grabar();
		v3.grabar();
		
		Vehiculo[] vList = Vehiculo.listar();
		
		for(Vehiculo v : vList) {
			System.out.println(v.toString());
		}
		
		try (Scanner sc = new Scanner(System.in)){
			System.out.println("Introduzca la matrícula para comprobar si existe el vehículo: ");
			Vehiculo buscado = Vehiculo.buscarObjetoEnFichero(sc.nextLine().trim());
			
			if(buscado != null) {
				System.out.println(buscado.toString());
			}else{
				System.out.println("Vehiculo no encontrado, introduzca una matrícula válida.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
