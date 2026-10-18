package com.ejemplo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


public class Orden {
	private int codigoIncidencia;
	private int codigoCliente;
	private String asunto;
	private String descripcion;
	private Date fechaApertura;
	private int codigoTecnico;
	private String observaciones;
	private Date fechaCierre;
	private double importe;
	
	public Orden() {}
	
	public Orden(int codigoCliente, String asunto, String descripcion) {
		this.codigoCliente = codigoCliente;
		this.asunto = asunto;
		this.descripcion = descripcion;
	}
	
	public Orden(int codigoIncidencia, int codigoCliente, String asunto, String descripcion, Date fechaApertura) {
		this.codigoIncidencia = codigoIncidencia;
		this.codigoCliente = codigoCliente;
		this.asunto = asunto;
		this.descripcion = descripcion;
		this.fechaApertura = fechaApertura;
	}

	public int getCodigoIncidencia() {
		return codigoIncidencia;
	}
	public void setCodigoIncidencia(int codigoIncidencia) {
		this.codigoIncidencia = codigoIncidencia;
	}

	public int getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public int getCodigoTecnico() {
		return codigoTecnico;
	}
	public void setCodigoTecnico(int codigoTecnico) {
		this.codigoTecnico = codigoTecnico;
	}

	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	public static List<Orden> buscarAbiertasPorCliente(int codigoCliente) throws SQLException, IOException{
		String sqlSelectByClient = "SELECT codigo_incidencia, codigo_cliente, asunto, descripcion, fecha_apertura " + "FROM ordenes WHERE codigo_cliente = ? AND fecha_cierre IS NULL";
		List<Orden> lista = new ArrayList<Orden>();
		try (Connection con = Conexion.getConexion();
				PreparedStatement pstmt = con.prepareStatement(sqlSelectByClient)){
			pstmt.setInt(1, codigoCliente);
			try (ResultSet rs = pstmt.executeQuery()){
				while (rs.next()) {
					Orden orden = new Orden(
							rs.getInt("codigo_incidencia"),
							rs.getInt("codigo_cliente"),
							rs.getString("asunto"),
							rs.getString("descripcion"),
							rs.getDate("fecha_apertura")
					);
					lista.add(orden);
				}
			}
		}
		return lista;
	}
	
	public boolean abrirIncidencia () throws SQLException, IOException {
		String sqlNuevaIncidencia = "INSERT INTO ordenes (codigo_cliente, asunto, descripcion, fecha_apertura) VALUES (?, ?, ?, CURDATE())";
		try (Connection con = Conexion.getConexion();
				PreparedStatement pstmt = con.prepareStatement(sqlNuevaIncidencia)){
			pstmt.setInt(1, codigoCliente);
			pstmt.setString(2, asunto);
			pstmt.setString(3, descripcion);
			
			int filasAfectadas = pstmt.executeUpdate();
			return filasAfectadas > 0;
		}
	}
	
	public static boolean cerrarIncidencia (int codigoIncidencia, int codigoCliente, int codigoTecnico, String observaciones, double importe) throws SQLException, IOException {
		String sqlCerrarIncidencia = "UPDATE ordenes SET codigotecnico = ?, observaciones = ?, fecha_cierre = CURDATE(), importe = ? " + "WHERE codigo_incidencia = ? AND codigo_cliente = ? AND fecha_cierre IS NULL";
		try (Connection con = Conexion.getConexion();
				PreparedStatement pstmt = con.prepareStatement(sqlCerrarIncidencia)){
			pstmt.setInt(1, codigoTecnico);
			pstmt.setString(2, observaciones);
			pstmt.setDouble(3, importe);
			pstmt.setInt(4, codigoIncidencia);
			pstmt.setInt(5, codigoCliente);
			
			int filasAfectadas = pstmt.executeUpdate();
			return filasAfectadas > 0;
		}
	}
	
	@Override
	public String toString() {
		return "Incidencia #" + codigoIncidencia + " [Cliente: " + codigoCliente + "] (" + fechaApertura + ") - " + asunto + ": " + descripcion;
	}
}
