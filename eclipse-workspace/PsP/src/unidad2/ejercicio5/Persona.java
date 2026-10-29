package unidad2.ejercicio5;

import java.util.concurrent.Semaphore;

public class Persona implements Runnable{
	private String nombre;
	private Semaphore sem;
	
	public Persona(String nombre, Semaphore sem) 
	{
		this.nombre = nombre;
		this.sem = sem;
	}
	
	@Override
	public void run()
	{
		int min = 1;
		int max = 10;
		int tiempoBanyo = (int) (Math.random() * (max - min + 1)) + min;
		System.out.println(String.format("%s va al baño.\n", nombre));
		try {
			sem.acquire();
			System.out.println(String.format("%s entra al baño.\n", nombre));
			System.out.println("Baños libres: " + sem.availablePermits());
			Thread.sleep(tiempoBanyo * 1000);
			System.out.println(String.format("%s sale del baño.\n", nombre));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			sem.release();
			System.out.println("Baños libres: " + sem.availablePermits());
		}
	}
}
