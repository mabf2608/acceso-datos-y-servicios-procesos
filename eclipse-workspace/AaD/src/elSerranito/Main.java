package elSerranito;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
	private static final String FICHERO = "pedidos.dat";
	public static void main(String[] args) {
		
		try (Scanner sc = new Scanner(System.in)){
			File f = new File(FICHERO);
			System.out.println("Introduce el nombre del cliente: ");
			String nCliente = sc.nextLine().trim();
			
			File fPedidosCliente = new File ("pedidos-" + nCliente + ".dat");
			Pedido[] pedidos = Pedido.listar(f);

			double costeTotal = 0.00;
			
			for (Pedido p : pedidos)
			{
				if (p.getCliente().trim().equalsIgnoreCase(nCliente.trim()))
				{
					p.grabar(fPedidosCliente);
					costeTotal += p.getPrecio();
				}
			}
			
			Properties config = new Properties();
			try (FileInputStream fis = new FileInputStream("config.properties")) {
			    config.load(fis);
			    String nombreRestaurante = config.getProperty("restaurante.nombre");
				int aforoRestaurante = Integer.parseInt(config.getProperty("restaurante.aforo").trim());
				String moneda = config.getProperty("restaurante.moneda");
				double descuento = Double.parseDouble(config.getProperty("restaurante.descuento"));
				double costeDescuento = costeTotal - (costeTotal*descuento);
				
				System.out.println("En " + nombreRestaurante + " esperamos que hayas disfutado de tu comida.");
				System.out.println("Nuestro aforo total es " + aforoRestaurante + " y aceptamos pagos en " + moneda +".");
				System.out.println("Factura: ");
				System.out.println("Has consumido un total de " + costeTotal + " euros, y con el descuento se te queda en " + costeDescuento + " euros") ;
			} catch (FileNotFoundException e) {
			    System.err.println("No se encontró config.properties: " + e.getMessage());
			    return;
			} catch (IOException e) {
			    e.printStackTrace();
			    return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
