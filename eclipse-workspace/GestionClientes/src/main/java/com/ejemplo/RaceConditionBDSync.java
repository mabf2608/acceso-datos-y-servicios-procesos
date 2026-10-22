package com.ejemplo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RaceConditionBDSync {
	
	private static final Object lock = new Object();
	public static void main(String[] args) {
			
			int codigoCliente = 2;
			int numeroHilos = 5;
			int iteracionesPorHilo = 10;
			double incremento = 10.0;
			
			try {
				Cliente encontrado = Cliente.buscar(codigoCliente);
				if(encontrado == null) {
					
					System.out.println("El ID que has mandado no corresponde a ningún Cliente, saliendo del programa...");
					return;
				}
				
				double saldo = encontrado.getSaldo();
				double saldoEsperado = saldo + (numeroHilos * iteracionesPorHilo * incremento);
				System.out.println(String.format("Se espera que el nuevo saldo sea: %.2f", saldoEsperado));
				
				System.out.println("=== TEST DE RACE CONDITION EN BD ===");
	            System.out.println("Cliente ID: " + codigoCliente);
	            System.out.println("Saldo inicial en BD: " + saldo + "€");
	            System.out.println("Hilos que se van a lanzar: " + numeroHilos);
	            System.out.println("Aportación total esperada: " + (saldoEsperado - saldo) + "€ (" + (iteracionesPorHilo * incremento) + "€ por cada hilo)");
	            System.out.println("Saldo final esperado: " + saldoEsperado + "€\n");
				
				List<Thread> hilos = new ArrayList<Thread>();
				
				for (int i = 0 ; i < numeroHilos ; i++) {
					final int numHilo = i;
					Thread hilo = new Thread(() -> {
						
						try {
							for (int j = 0; j < iteracionesPorHilo; j++) {
								synchronized (lock) {
									Cliente actual = Cliente.buscar(codigoCliente);
									if(actual != null) {
										double saldoNuevo = actual.getSaldo() + incremento;
										actual.actualizarSaldo(saldoNuevo);
									}
								}
							}
						} catch (IOException | SQLException e) {
							e.printStackTrace();
						} 
						System.out.println("Hilo: "+ numHilo + " finalizado.");
					});
					hilos.add(hilo);
					hilo.start();
				}
				
				for (Thread h : hilos) {
					h.join();
				}
				
				Cliente clienteActualizado = Cliente.buscar(codigoCliente);
				double saldoActualizado = clienteActualizado.getSaldo();
				
				System.out.println("\nSaldo inicial: " + saldo + "€.");
				System.out.println("Saldo esperado: " + saldoEsperado + "€.");
				System.out.println("Saldo real obtenido: "+ saldoActualizado + "€.");
				
				if(saldoActualizado < saldoEsperado) {
					System.out.println(String.format("Se ha detectado una Race Condition y se han perdido: %.2f€. %n", (saldoEsperado - saldoActualizado)));
				}else {
					System.out.println("No ha habido colisiones en esta ejecución.");
				}
				
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

}
