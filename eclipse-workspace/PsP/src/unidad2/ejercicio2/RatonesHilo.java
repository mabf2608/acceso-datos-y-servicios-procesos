package unidad2.ejercicio2;

public class RatonesHilo implements Runnable{

	private String nombreRaton;
	private int tiempoCome;
	
	public RatonesHilo(String nombre, int tiempo)
	{
		this.nombreRaton = nombre;
		this.tiempoCome = tiempo;
	}
	
	@Override
	public void run() 
	{
		System.out.println(nombreRaton + " comienza a comer...\n");
		try {
			int duracion = (int) (tiempoCome * (1000));
			Thread.sleep(duracion);
			System.out.println(nombreRaton + " ha terminado de comer, ha estado comiendo un total de " + duracion/1000 + " segundos.\n");
			
		} catch (InterruptedException e) {
			System.out.println("El raton " + nombreRaton + " no ha podido comer porque fue interrumpido.\n");
		}
		System.out.println("Se ha cerrado el hilo que daba de comer a " + nombreRaton + "\n");
	}
}
