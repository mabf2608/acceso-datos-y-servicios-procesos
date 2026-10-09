package com.ejemplo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Main {

	public static void main(String[] args) {
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("config.properties")){
			props.load(fis);
			System.out.println("Fichero de propiedades cargado correctamente.");
			
			String ip = props.getProperty("db.ip");
			String puerto = props.getProperty("db.port");
			String nombreBd = props.getProperty("db.name");
			String usuario = props.getProperty("db.user");
			String password = props.getProperty("db.password");
			
			String url = "jdbc:mysql://" + ip + ":" + puerto + "/" + nombreBd + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			Connection conexion = DriverManager.getConnection(url, usuario, password);
			
			Statement stmt = conexion.createStatement();
			String sqlInsert = "INSERT INTO clientes (nombre, email, saldo, fecha_alta) VALUES ('Miguel Ángel Bonilla', 'mabf@email.com', 450.00, '2026-04-09')";
			stmt.executeUpdate(sqlInsert);
			System.out.println("Cliente insertado con éxito.");
			
			String sqlSelect = "SELECT * FROM clientes";
			ResultSet rs = stmt.executeQuery(sqlSelect);
			
			System.out.println("Listado de clientes: ");
			while (rs.next()) {
				int codigo = rs.getInt("codigo");
				String nombre = rs.getString("nombre");
	            String email = rs.getString("email");
	            double saldo = rs.getDouble("saldo");
	            
	            System.out.println();
	            System.out.println("ID: " + codigo + " | Nombre: " + nombre + " | Email: " + email + " | Saldo: " + saldo + "€");
			}
			
		} catch (IOException e) {
			System.out.println("Error al leer el fichero de configuración: " + e.getMessage());
		} catch (SQLException e)
		{
			System.out.println("Error al leer conectarse a la BBDD " + e.getMessage());
		}
	}

}
