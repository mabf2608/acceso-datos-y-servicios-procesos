package unidad2.ejercicio2;

public class TestRatones {

	public static void main(String[] args) {
    	try {
        	
            RatonesHilo raton1 = new RatonesHilo("Migue", 2);
            RatonesHilo raton2 = new RatonesHilo("Irene", 4);
            RatonesHilo raton3 = new RatonesHilo("Fred", 1);
            RatonesHilo raton4 = new RatonesHilo("Paco", 8);
            RatonesHilo raton5 = new RatonesHilo("Alicia", 3);
            RatonesHilo raton6 = new RatonesHilo("Jose", 5);

            // 2. Crear los objetos Thread, pasando la tarea (Runnable) y el nombre
            Thread r1 = new Thread(raton1);
            Thread r2 = new Thread(raton2);
            Thread r3 = new Thread(raton3);
            Thread r4 = new Thread(raton4);
            Thread r5 = new Thread(raton5);
            Thread r6 = new Thread(raton6);


            // 3. Iniciar la ejecución de los hilos
            // LLAMAR A start() ES FUNDAMENTAL, NO a run()
            r1.start();
            r2.start();
            r3.start();
            r4.start();
            r5.start();
            r6.start();
         
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
