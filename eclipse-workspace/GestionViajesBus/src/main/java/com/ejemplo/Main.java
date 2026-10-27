package com.ejemplo;

import java.io.IOException;
import java.util.Scanner;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)){
			int opcion = 0;
			do 
			{
				System.out.println("----- CRUD CON DAO VIAJES -----");
				System.out.println("1. Consultar plazas disponibles por destino.");
				System.out.println("2. Realizar nueva reserva.");
				System.out.println("3. Cancelar reserva.");
				System.out.println("0. Salir");
				System.out.println("-----------------------");
				opcion = Integer.parseInt(sc.nextLine().trim());
				String destino;
				int codigoCliente;
				
				switch (opcion) 
				{
					case 1:
						System.out.println("\n Introduce el nombre del destino (ej. Cuenca, Sevilla, Madrid): ");
						destino = sc.nextLine().trim();
						int plazasDisponibles = Viaje.plazasLibres(destino);
						if (plazasDisponibles == -1)
						{
							System.out.println(String.format("El destino \"%s\" no existe, vuelva a intentarlo.\n", destino));
						}
						else
						{
							System.out.println(String.format("En el destino \"%s\" quedan libres %d plazas.\n", destino, plazasDisponibles));
						}
						break;
							
					case 2:
						System.out.println("\nIntroduce el nombre del destino (ej. Cuenca, Sevilla, Madrid): ");
						destino = sc.nextLine().trim();
						System.out.println("Introduce el codigo del cliente: ");
						codigoCliente = Integer.parseInt(sc.nextLine().trim());
						System.out.println("Introduce el número de plazas que solicita: ");
						int plazasSolicitadas = Integer.parseInt(sc.nextLine().trim());
						
						boolean exito = Reserva.crearReserva(destino, codigoCliente, plazasSolicitadas);
						if (exito)
						{
							int plazasRestantes = Viaje.plazasLibres(destino);
						    System.out.println("\nReserva tramitada con éxito.");
						    System.out.println("Plazas disponibles actuales en " + destino + ": " + plazasRestantes + "\n");
						}
						else
						{
							System.out.println(String.format("\nEl destino \"%s\" no existe, vuelva a intentarlo.\n", destino));
						}
						break;
						
					case 3:
						System.out.println("\nIntroduce el número de la reserva que desea cancelar: ");
						int codigoReserva = Integer.parseInt(sc.nextLine().trim());
						System.out.println("\nPara asegurarnos de que eres la persona correcta necesitamos conocer el ID del cliente: ");
						codigoCliente = Integer.parseInt(sc.nextLine().trim());
						boolean cancelada = Reserva.cancelarReserva(codigoReserva, codigoCliente);
						if (cancelada)
						{
							System.out.println("\nReserva cancelada con Éxito.\n");
						}
						else
						{
							System.out.println("\nNo se encontró esa reserva asociada a ese cliente, o ya está cancelada.\n");
						}
						break;
						
					case 0:
						System.out.println("\nSaliendo del programa...\n");
						opcion = 0;
						break;
						
					default:
						System.out.println("\nOpción no válida, vuelva a intentarlo. \n");
						
				}
			} while (opcion != 0);
		} catch (IOException e) {
			System.out.println("Error al leer el fichero de configuración: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al conectarse a la BBDD " + e.getMessage());
		}
	}
}
