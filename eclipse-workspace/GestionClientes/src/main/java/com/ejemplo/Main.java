//REALIZADO POR MIGUEL ÁNGEL BONILLA FERNÁNDEZ

package com.ejemplo;

import java.io.IOException;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
			
			try (Scanner sc = new Scanner(System.in)){
				int opcion = 0;
				int codigo;
				String nombre;
				String email;
				String fecha;
	            double saldo;
				
				do {
					
					System.out.println("----- CRUD CON DAO -----");
					System.out.println("1. Dar de alta a un cliente.");
					System.out.println("2. Consultar un cliente.");
					System.out.println("3. Modificar saldo.");
					System.out.println("4. Borrar cliente");
					System.out.println("5. Consultar lista de clientes.");
					System.out.println("0. Salir");
					System.out.println("-----------------------");
					
					opcion = Integer.parseInt(sc.nextLine());
					switch (opcion) {
						case 1:
							System.out.println("\nIntroduce el nombre: ");
							nombre = sc.nextLine().trim();
							System.out.println("Introduce el correo: ");
							email = sc.nextLine().trim();
							System.out.println("Introduce el saldo: (ej: 450.00)");
							saldo = Double.parseDouble(sc.nextLine().trim());
							System.out.println("Introduce la fecha (ej: 2026-04-09)");
							fecha = sc.nextLine().trim();
							
							Cliente nuevo = new Cliente(nombre, email, fecha, saldo);
							
							if (nuevo.grabar()) {
								System.out.println("\nCliente insertado con éxito.\n");
							}
							
							break;
							
						case 2:
							System.out.println("\nIntroduce el ID del cliente que quieres consultar: ");
							codigo = Integer.parseInt(sc.nextLine().trim());
							
							Cliente encontrado = Cliente.buscar(codigo);
	                        if (encontrado != null) {
	                            System.out.println("\n" + encontrado + "\n");
	                        } else {
	                            System.out.println("\nNo se encontró ningún cliente con ese ID.\n");
	                        }
	                        
							break;
							
						case 3:
							System.out.println("\nIntroduce el ID del cliente que quieres modificar el saldo: ");
							codigo = Integer.parseInt(sc.nextLine().trim());
							
							Cliente cliente = Cliente.buscar(codigo);
							if (cliente != null) {
								System.out.println("Introduce el saldo a modificar (ej: 450.00): ");
								saldo = Double.parseDouble(sc.nextLine().trim());
								
								if (cliente.actualizarSaldo(saldo)) {
	                                System.out.println("\nSaldo actualizado con éxito.\n");
	                            }
								
	                        } else {
	                            System.out.println("\nNo se encontró ningún cliente con ese ID.\n");
	                        }
							
							break;
							
						case 4:
							System.out.println("\nIntroduce el ID del cliente que quieres eliminar: ");
							codigo = Integer.parseInt(sc.nextLine().trim());
							
							if (Cliente.borrar(codigo)) {
								System.out.println("\nCliente eliminado con éxito.\n");
							} else {
								System.out.println("\nNo se encontró ningún cliente con ese ID.\n");
							}
							
							break;
							
						case 5:
							System.out.println("------ LISTADO ------");
							List<Cliente> clientes = Cliente.obtenerTodos();
							
							for(Cliente c: clientes) {
								System.out.println(c);
							}
							System.out.println();
							
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
