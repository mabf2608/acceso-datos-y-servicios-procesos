package unidad2.ejercicio3;

public class GestorTickets {

	private static int boletosRestantes = 100;
	
	public static int venderBoletos() {
	
		if (boletosRestantes <= 0) {
			return (-1);
		}
		
		int boletoAsignado = boletosRestantes;
		
		try {
			Thread.sleep(15);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		boletosRestantes--;
		return(boletoAsignado);
		
	}
	
	public static int getBoletos() {
		return boletosRestantes;
	}
	
}
