package unidad2.ejercicio3;

public class Vendedor implements Runnable{
	
	private String nombre;
	
	public Vendedor(String nombre) {
		this.nombre = nombre;
	}
	
	//Este método es lo que ejecuta cada hilo en paralelo
	@Override
	public void run() {
		
		while (true) {
			
			int boleto= GestorTickets.venderBoletos();
			
			if (boleto == -1) {
				System.out.println("Ya no quedan boletos disponibles, saliendo del programa... \n");
				break;
			}
			
			System.out.println(String.format("%s ha vendido el boleto número: %d.\n", nombre, boleto));
			
			try {
				Thread.sleep(15);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		Thread[] hilos = new Thread[8];
		
		for (int i = 0; i < hilos.length; i++) {
			
			Vendedor tarea = new Vendedor("Vendedor "+ (i + 1));
			
			hilos[i] = new Thread(tarea);
			
			hilos[i].start();
			
		}
		
		for (int i = 0; i < hilos.length ; i++) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fin de las ventas. Boletos restantes: " + GestorTickets.getBoletos());
	}

}