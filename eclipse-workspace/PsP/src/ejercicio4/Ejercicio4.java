package ejercicio4;

public class Ejercicio4 {

	public static void main(String[] args) {
		if (args.length == 0)
		{
			System.out.println("Introduce la IP o sitio de internet al que quieras hacer ping.");
			return;
		}
		
		try 
		{
			ProcessBuilder pbPing = new ProcessBuilder("ping", "-n", "1", args[0]);
			Process pPing = pbPing.start();
			
			int exitCode = pPing.waitFor();
			
			if(exitCode == 0)
			{
				System.out.println("El servidor está activo.");
			}
			else 
			{
				System.out.println("El servidor esta desactivado o tiene Firewall");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
