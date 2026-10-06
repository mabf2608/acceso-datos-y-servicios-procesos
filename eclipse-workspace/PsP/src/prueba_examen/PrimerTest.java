package prueba_examen;

import java.io.File;
import java.io.IOException;

public class PrimerTest {
	public static void main(String[] args) {
		System.out.println("Abriendo el explorador de archivos..."+"\n");
		try 
		{
			ProcessBuilder pb = new ProcessBuilder("explorer.exe", "/separate");
			File carpetaUsuario = new File(System.getProperty("user.home"));
			
			pb.directory(carpetaUsuario);
			
			System.out.println("El explorador se abrirá apuntando a " + carpetaUsuario.getAbsolutePath());
			
			Process proceso = pb.start();
			
			System.out.println("¿El proceso está activo justo ahora?" + proceso.isAlive());
			System.out.println("Esperando el código de retorno...");
			
			int	codigoSalida = proceso.waitFor();
			
			System.out.println("¿El proceso sigue vivo?" + proceso.isAlive());
			
			
			if (codigoSalida == 0)
			{
				System.out.println("¡Éxito! El proceso se ejecutó de forma limpia (Código 0).");
            } else {
                System.out.println("El proceso terminó con una alerta o código inusual: " + codigoSalida);
            }
		}
		catch (IOException e)
		{
			System.err.print("Error de entrada/salida al lanzar el programa.");
		}
		catch (InterruptedException e)
		{
			System.err.println("La espera del proceso fue interrumpida de forma inesperada.");
		}
	}
}
