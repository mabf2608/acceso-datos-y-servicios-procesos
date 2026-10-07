package unidad2.ejercicio1;

public class TareaHilo implements Runnable {

	private String nombreHilo;
	
	public TareaHilo(String nombre)
	{
		this.nombreHilo = nombre;
		System.out.printl("Creando " + nombreHilo);
	}
	
	@Override
	public void run() 
	{
		System.out.println("Ejecutando " + nombreHilo);
		try {
			for(int i = 1; i <= 5; i ++)
			{
				System.out.println("Hilo: " + nombreHilo + ", Contador:" + i);
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			System.out.println("El hilo " + nombreHilo + "fue interrumpido.");
		}
		System.out.println("Terminando " + nombreHilo);
	}

}
