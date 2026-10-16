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
	
}
