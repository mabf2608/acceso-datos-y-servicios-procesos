package unidad2.ejercicio1;

public class TareaHilo implements Runnable {

	private String nombreHilo;
	
	public TareaHilo(String nombre)
	{
		this.nombreHilo = nombre;
		System.out.println("Creando el hilo para atender a " + nombreHilo + ".\n");
	}
	
	@Override
	public void run() 
	{
		System.out.println("Atendiendo a " + nombreHilo + "...\n");
		try {
			int duracion = (int) (Math.random() * (30000 - 20000 + 1)) + 20000;
			Thread.sleep(duracion);
			System.out.println("Se ha terminado de atender a " + nombreHilo + ", gracias por su paciencia.");
			
		} catch (InterruptedException e) {
			System.out.println("El hilo que atendía a " + nombreHilo + ", fue interrumpido.\n");
		}
		System.out.println("Se ha cerrado el hilo que atendía a " + nombreHilo + ".\n");
	}

}
