package com.ejemplo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Viaje {
	
	private int codigoViaje;
	private String destino;
	private int plazasDisponibles;
	private int totalPlazas;
	private Date fecha;
	
	public Viaje() {};
	
	public Viaje (int codigoViaje, String destino, int plazasDisponibles,
			 int totalPlazas, Date fecha) {
		this.codigoViaje = codigoViaje;
		this.destino = destino;
		this.plazasDisponibles = plazasDisponibles;
		this.totalPlazas = totalPlazas;
		this.fecha = fecha;
	}

	public int getCodigoViaje() {
		return codigoViaje;
	}
	public void setCodigoViaje(int codigoViaje) {
		this.codigoViaje = codigoViaje;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}
	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}
	public int getTotalPlazas() {
		return totalPlazas;
	}
	public void setTotalPlazas(int totalPlazas) {
		this.totalPlazas = totalPlazas;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public static int plazasLibres (String destino) throws SQLException, IOException{
		String sqlSelect = "SELECT * FROM viajes WHERE destino = ?";
		try (Connection con = Conexion.getConexion();
				PreparedStatement pstmt = con.prepareStatement(sqlSelect))
		{
			pstmt.setString(1, destino);
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (rs.next()) {
					return rs.getInt("plazas_disponibles");
				} else {
					return -1;
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return "Viaje [codigoViaje=" + codigoViaje + ", destino=" + destino + ", plazasDisponibles=" + plazasDisponibles
				+ ", totalPlazas=" + totalPlazas + ", fecha=" + fecha + "]";
	}
}
