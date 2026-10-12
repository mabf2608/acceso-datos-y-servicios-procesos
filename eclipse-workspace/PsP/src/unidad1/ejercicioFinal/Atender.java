package unidad1.ejercicioFinal;

//Para escribir en el fichero de texto compartido y gestionar errores de E/S
import java.io.FileWriter;
import java.io.BufferedWriter; // Opcional, pero recomendable junto a FileWriter
import java.io.IOException;

//Para generar los números aleatorios (cliente y tiempo)
import java.util.Random;

//Para obtener y formatear la fecha y hora actual (ej: 12/10/26 10:43)
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Atender {
	
	public static void registrarAtencion(String empleado, String cliente, int segundos) {
		
		LocalDateTime ahora = LocalDateTime.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		String fechaTexto = ahora.format(formato);
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("registro.txt", true))){
			
			String str = String.format("%s %s atendió al cliente %s durante %d segundos.",fechaTexto, empleado, cliente, segundos);
			bw.write(str);
			bw.newLine();	
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		if (args.length == 0) {
			System.out.println("Introduce un nombre para el trabajador, cerrando programa...\n");
			return;
		}
		
		String empleado = args[0];
		
		String[] clientes = {
				"Miguel Angel",
				"Jorge",
				"Paco",
				"Alicia",
				"Lourdes",
				"Silvia"
		};
		
		Random random = new Random();
		
		try {
			for (int i = 0 ; i < 4 ; i++) {
				
				int indiceCliente = random.nextInt(6);
				int tiempo = 1 + random.nextInt(6);
				
				System.out.println(empleado + " esta atendiendo a " + clientes[indiceCliente] + ".\n");
				Thread.sleep(1000 * tiempo);
				registrarAtencion(empleado, clientes[indiceCliente], tiempo);
			}
			
			System.out.println(String.format("%s ha terminado de atender a sus 4 clientes.\n", empleado));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
