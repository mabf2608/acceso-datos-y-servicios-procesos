package unidad2.ejercicio5;

import java.util.concurrent.Semaphore;

public class Principal {

	public static void main(String[] args) {
		Semaphore sem = new Semaphore(3);
		String[] usuarios = {
			    "pepito", "jaimito", "lucia", "carlos", "marta",
			    "andres", "elena", "marcos", "sara", "david"
			};
		Thread[] hilosUsuarios = new Thread[10];
		for (int i = 0 ; i < usuarios.length ; i++)
		{
			Persona persona = new Persona(usuarios[i], sem);
			hilosUsuarios[i] = new Thread(persona);
			hilosUsuarios[i].start();
		}
		
		try {
			for (Thread hilo : hilosUsuarios) 
			{
				hilo.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("\nTodas las personas han ido al baño, hay disponibles " + sem.availablePermits() + " de ellos.");
	}

}
