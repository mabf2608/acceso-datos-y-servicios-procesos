package com.ejemplo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

	private int codigo;
	private String nombre;
	private String telefono;
	private double saldo;
	
	public Cliente() {}
	
	public Cliente(String nombre, String telefono, double saldo) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.saldo = saldo;
	}
	
	public Cliente(int codigo, String nombre, String telefono, double saldo) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.telefono = telefono;
		this.saldo = saldo;
	}
	
	public int getCodigo() {return codigo;}
	public void setCodigo(int codigo) {this.codigo = codigo;}
	
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	
	public String getTelefono() {return telefono;}
	public void setTelefono(String telefono) {this.telefono = telefono;}
	
	public double getSaldo() {return saldo;}
	public void setSaldo(double saldo) {this.saldo = saldo;}
	
	//INSERT
	public boolean grabar() throws SQLException, IOException{
		String sqlInsert = "INSERT INTO clientes (nombre, telefono, saldo) VALUES (?, ?, ?)";
		
		try (Connection con = Conexion.getConexion();PreparedStatement pstmt = con.prepareStatement(sqlInsert)) {
	        pstmt.setString(1, nombre);
	        pstmt.setString(2, telefono);
	        pstmt.setDouble(3, saldo);
	        
	        return pstmt.executeUpdate() > 0;
	    }
	}
	
	//SELECT CON CONDICION
	public static Cliente buscar(int codigo) throws SQLException, IOException{
		String sqlSelectCliente = "SELECT * FROM clientes WHERE codigo = ?";
		try (Connection con = Conexion.getConexion();PreparedStatement pstmt = con.prepareStatement(sqlSelectCliente)){
			
			pstmt.setInt(1, codigo);
			
			try (ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return new Cliente(
							rs.getInt("codigo"),
							rs.getString("nombre"),
							rs.getString("telefono"),
							rs.getDouble("saldo")
		            );	
				}
			}
		}
		return null;
	}
	
	//UPDATE SALDO
	public boolean actualizarSaldo(double nuevoSaldo) throws SQLException, IOException{
		String sqlModifySueldo = "UPDATE clientes SET saldo = ? WHERE codigo = ?";
		try (Connection con = Conexion.getConexion(); PreparedStatement pstmt = con.prepareStatement(sqlModifySueldo)){
			pstmt.setDouble(1, nuevoSaldo);
			pstmt.setInt(2, this.codigo);
			
			int filas = pstmt.executeUpdate();
            if (filas > 0) {
                this.saldo = nuevoSaldo;
                return true;
            }
            return false;
		}
	}
	
	//DELETE CLIENTE
	public static boolean borrar(int codigoBorrar) throws SQLException, IOException{
		String sqlDeleteCliente = "DELETE FROM clientes WHERE codigo = ?";
		try (Connection con = Conexion.getConexion(); PreparedStatement pstmt = con.prepareStatement(sqlDeleteCliente)){
			pstmt.setInt(1, codigoBorrar);
			
			return pstmt.executeUpdate() > 0;
		}
	}
	
	//SELECT TODOS
	public static List<Cliente> obtenerTodos() throws SQLException, IOException{
		String sqlSelect = "SELECT * FROM clientes";
		List<Cliente> cList = new ArrayList<Cliente>();
		try (Connection con = Conexion.getConexion();
				PreparedStatement pstmt = con.prepareStatement(sqlSelect);
				ResultSet rs = pstmt.executeQuery()){
			
			while (rs.next()) {
				Cliente cliente = new Cliente(
				rs.getInt("codigo"),
				rs.getString("nombre"),
	            rs.getString("telefono"),
	            rs.getDouble("saldo"));
				cList.add(cliente);
			}
		}
		return cList;
	}
	
	@Override
	public String toString() {
		return "ID: " + codigo + " | Nombre: " + nombre + " | Teléfono: " + telefono 
                + " | Saldo: " + saldo + "€";
	}
	
}
