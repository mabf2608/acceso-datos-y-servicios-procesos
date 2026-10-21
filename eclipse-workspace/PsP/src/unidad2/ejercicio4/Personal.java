package unidad2.ejercicio4;

public class Personal implements Runnable{
	private static final Object lock = new Object();
	private String nombre;
	private boolean esJefe;
	
	public Personal(String nombre, boolean esJefe) {
		this.nombre = nombre;
		this.esJefe = esJefe;
	}
	
	@Override
	public void run()
	{

		if (esJefe)
		{
			System.out.println("El Jefe " + nombre + " llegó.");
			synchronized (lock) 
			{
				System.out.println("--- HOLA EMPLEADOS ---");
				lock.notifyAll();
			}
		}
		else 
		{
			System.out.println("El empleado " + nombre + " llegó.");
			try 
			{
				synchronized (lock) 
				{
					lock.wait();
					System.out.println(nombre + " saludó al jefe.");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
