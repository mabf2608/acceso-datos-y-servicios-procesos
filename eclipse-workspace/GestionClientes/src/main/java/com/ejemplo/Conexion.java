package com.ejemplo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

	public static Connection getConexion() throws SQLException, IOException{
		Properties props = new Properties();
		//Try con recurso File input stream para que lea el archivo config.properties
		try (FileInputStream fis = new FileInputStream("config.properties")){
			props.load(fis);
			
			String ip = props.getProperty("db.ip");
			String puerto = props.getProperty("db.port");
			String nombreBd = props.getProperty("db.name");
			String user = props.getProperty("db.user");
			String password = props.getProperty("db.password");
			
			//Aquí creamos la URL y la devolvemos junto a usuario y contraseña
			String url = "jdbc:mysql://" + ip + ":" + puerto + "/" + nombreBd 
					+ "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		
			return DriverManager.getConnection(url, user, password);
		}
	}

}
