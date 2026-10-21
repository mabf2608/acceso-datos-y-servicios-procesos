package unidad2.ejercicio4;

public class Saludo {
	private static final Object lock = new Object();
	public static void main(String[] args) {
		System.out.println("--- VERSION CON RUN ---");
		Thread Empleado1 = new Thread(new Personal("Pepe", false));
		Thread Empleado2 = new Thread(new Personal("José", false));
		Thread Empleado3 = new Thread(new Personal("Pedro", false));
		Thread Jefe1 = new Thread(new Personal("ANTONIO", true));
		
		Empleado1.start();           
        Empleado2.start();           
        Empleado3.start();    
        try {
            Thread.sleep(1000);
            Jefe1.start();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n--- VERSION CON LAMBDA ---");
        
        String[] empleados = {"Pepe",  "José", "Pedro"};
        for (String nombre : empleados)
        {
        	new Thread(() -> {
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
        	}).start();
        }
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        new Thread(() -> {
        	System.out.println("El Jefe ANTONIO llegó.");
			synchronized (lock) 
			{
				System.out.println("--- HOLA EMPLEADOS ---");
				lock.notifyAll();
			}
        }).start();
	}
}
