package elSerranito;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pedido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String cliente;
	private String plato;
	private double precio;
	private String fecha;
	
	public Pedido(int id, String cliente, String plato, double precio, String fecha)
	{
		this.id = id;
		this.cliente = cliente;
		this.plato = plato;
		this.precio = precio;
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getPlato() {
		return plato;
	}
	public void setPlato(String plato) {
		this.plato = plato;
	}

	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public static Pedido[] listar(File fichero) {
    	
		if (!fichero.exists()) {
            return new Pedido[0];
        }

        List<Pedido> pList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    // Cortamos la línea por el punto y coma: 1;Ana;paella de marisco;22.50;2024-05-01
                    String[] partes = linea.split(";");
                    int id = Integer.parseInt(partes[0].trim());
                    String cliente = partes[1].trim();
                    String plato = partes[2].trim();
                    double precio = Double.parseDouble(partes[3].trim());
                    String fecha = partes[4].trim();

                    Pedido p = new Pedido(id, cliente, plato, precio, fecha);
                    pList.add(p);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return pList.toArray(new Pedido[0]);
    }
	
	public static Pedido[] listarBinario(File ficheroCliente) {
        if (!ficheroCliente.exists()) {
            return new Pedido[0];
        }

        List<Pedido> pList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroCliente))) {
            while (true) {
                Pedido p = (Pedido) ois.readObject();
                pList.add(p);
            }
        } catch (EOFException e) {
            // Fin del fichero binario
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return pList.toArray(new Pedido[0]);
    }

    public void grabar(File fichero) {
        List<Pedido> pList = new ArrayList<>();
        
        Pedido[] pedidosPrevios = Pedido.listarBinario(fichero);
        if (pedidosPrevios != null) {
            pList.addAll(Arrays.asList(pedidosPrevios));
        }

        pList.add(this);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))) {
            for (Pedido p : pList) {
                oos.writeObject(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@Override
	public String toString() {
		return "Pedido [ID = " + id + ", Cliente = " + cliente + ", Plato = " + plato + ", Precio = " + precio + ", Fecha = " + fecha + "]";
	}
}
