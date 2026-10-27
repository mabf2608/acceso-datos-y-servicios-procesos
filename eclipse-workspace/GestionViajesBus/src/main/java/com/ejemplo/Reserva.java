package com.ejemplo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reserva {
	private int numReserva;
	private int codigoViaje;
	private int codigoCliente;
	private int plazasReservadas;
	private char estado;
	
	public Reserva() {}
	
	public Reserva(int codigoViaje, int codigoCliente, 
			int plazasReservadas, char estado) {
		this.codigoViaje = codigoViaje;
		this.codigoCliente = codigoCliente;
		this.plazasReservadas = plazasReservadas;
		this.estado = estado;
	}
	
	public Reserva(int numReserva, int codigoViaje, int codigoCliente, 
			int plazasReservadas, char estado) {
		this.numReserva = numReserva;
		this.codigoViaje = codigoViaje;
		this.codigoCliente = codigoCliente;
		this.plazasReservadas = plazasReservadas;
		this.estado = estado;
	}

	public int getNumReserva() {
		return numReserva;
	}
	public void setNumReserva(int numReserva) {
		this.numReserva = numReserva;
	}
	public int getCodigoViaje() {
		return codigoViaje;
	}
	public void setCodigoViaje(int codigoViaje) {
		this.codigoViaje = codigoViaje;
	}
	public int getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public int getPlazasReservadas() {
		return plazasReservadas;
	}
	public void setPlazasReservadas(int plazasReservadas) {
		this.plazasReservadas = plazasReservadas;
	}
	public char getEstado() {
		return estado;
	}
	public void setEstado(char estado) {
		this.estado = estado;
	}
	
	public static boolean crearReserva(String destino, int codigoCliente,
			 int plazasDeseadas) throws SQLException, IOException
	{
		Connection con = null;
		try 
		{
			con = Conexion.getConexion();
			con.setAutoCommit(false);
			
			int plazasDisponibles = -1;
			int codigoViaje = -1;
			
			String sqlConsulta = "SELECT codigo_viaje, plazas_disponibles FROM viajes WHERE destino = ?";
			try (PreparedStatement pstmtConsulta = con.prepareStatement(sqlConsulta))
			{
				pstmtConsulta.setString(1, destino);
				try (ResultSet rs = pstmtConsulta.executeQuery())
				{
					if (rs.next())
					{
						codigoViaje = rs.getInt("codigo_viaje");
						plazasDisponibles = rs.getInt("plazas_disponibles");
					}
				}
			}
			
			if (codigoViaje == -1)
			{
				con.rollback();
				return false;
			}
				
			char estado;
			if (plazasDisponibles >= plazasDeseadas)
			{
				estado = 'A';
				String sqlUpdate = "UPDATE viajes SET plazas_disponibles = plazas_disponibles - ? WHERE destino = ?";
				try(PreparedStatement pstmt = con.prepareStatement(sqlUpdate))
				{
					pstmt.setInt(1, plazasDeseadas);
					pstmt.setString(2, destino);
					pstmt.executeUpdate();
				}
			} 
			else 
			{
				estado = 'E';
			}
			
			String sqlInsert = "INSERT INTO reservas (codigo_viaje, codigo_cliente, plazas_reservadas,"
					+ " estado) VALUES (?, ?, ?, ?)";
			try (PreparedStatement pstmt = con.prepareStatement(sqlInsert))
			{
				pstmt.setInt(1, codigoViaje);
				pstmt.setInt(2, codigoCliente);
				pstmt.setInt(3, plazasDeseadas);
				pstmt.setString(4, String.valueOf(estado));
				pstmt.executeUpdate();
			}
			con.commit();
			return true;

			
		} catch (SQLException e) {
			if (con != null) 
			{
				try 
				{
					con.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			throw e;
		} finally {
			if (con != null) 
			{
                try 
                {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
	}
	
	public static boolean cancelarReserva(int numReserva, int codigoCliente) 
			throws SQLException, IOException
	{
		Connection con = null;
		try {
			con = Conexion.getConexion();
			con.setAutoCommit(false);
			
			int codigoViaje = -1;
			int plazasReservadas = -1;
			char estadoActual;
			
			String sqlConsulta = "SELECT codigo_viaje, plazas_reservadas, estado FROM "
					+ "reservas WHERE num_reserva = ? AND codigo_cliente = ?";
			try (PreparedStatement pstmtCancelar = con.prepareStatement(sqlConsulta))
			{
				pstmtCancelar.setInt(1, numReserva);
				pstmtCancelar.setInt(2, codigoCliente);
				try (ResultSet rs = pstmtCancelar.executeQuery())
				{
					if(rs.next())
					{
						codigoViaje = rs.getInt("codigo_viaje");
						plazasReservadas = rs.getInt("plazas_reservadas");
						estadoActual = rs.getString("estado").charAt(0);
					}
					else
					{
						con.rollback();
						return false;
					}
				}
			}
			
			if (estadoActual == 'C') {
				con.rollback();
				return false;
			}
			
			sqlConsulta = "UPDATE reservas SET estado = 'C' WHERE num_reserva = ?";
			try (PreparedStatement pstmtActualizarReserva = con.prepareStatement(sqlConsulta))
			{
				pstmtActualizarReserva.setInt(1, numReserva);
				pstmtActualizarReserva.executeUpdate();
			}
			
			if (estadoActual == 'A') 
			{
				sqlConsulta = "UPDATE viajes SET plazas_disponibles = plazas_disponibles + ? WHERE codigo_viaje = ?";
				try (PreparedStatement pstmtActualizarViajes = con.prepareStatement(sqlConsulta))
				{
					pstmtActualizarViajes.setInt(1, plazasReservadas);
					pstmtActualizarViajes.setInt(2, codigoViaje);
					pstmtActualizarViajes.executeUpdate();
				}
			}
			con.commit();
			return true;
			
		} catch (SQLException e) {
			if (con != null) 
			{
				try 
				{
					con.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			throw e;
		} finally {
			if (con != null) 
			{
                try 
                {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
	}

	@Override
	public String toString() {
		return "Reserva [numReserva=" + numReserva + ", codigoViaje=" + codigoViaje + ", codigoCliente=" + codigoCliente
				+ ", plazasReservadas=" + plazasReservadas + ", estado=" + estado + "]";
	}
}
