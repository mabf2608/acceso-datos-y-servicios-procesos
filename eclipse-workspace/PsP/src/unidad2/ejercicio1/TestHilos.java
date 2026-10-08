package unidad2.ejercicio1;

import java.util.Scanner;

public class TestHilos {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	try {
    		String cliente;
        	
        	System.out.println("Introduce el nombre del primer cliente: ");
        	cliente = sc.nextLine();
            TareaHilo cliente1 = new TareaHilo(cliente);
            
            System.out.println("Introduce el nombre del segundo cliente: ");
        	cliente = sc.nextLine();
            TareaHilo cliente2 = new TareaHilo(cliente);
            
            System.out.println("Introduce el nombre del tercer cliente: ");
        	cliente = sc.nextLine();
            TareaHilo cliente3 = new TareaHilo(cliente);

            // 2. Crear los objetos Thread, pasando la tarea (Runnable) y el nombre
            Thread c1 = new Thread(cliente1);
            Thread c2 = new Thread(cliente2);
            Thread c3 = new Thread(cliente3);

            // 3. Iniciar la ejecución de los hilos
            // LLAMAR A start() ES FUNDAMENTAL, NO a run()
            System.out.println("Iniciando hilos, el hilo principal (main) continúa su ejecución.   \n");
            c1.start();
            c2.start();
            c3.start();

            
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
    	
    }
}