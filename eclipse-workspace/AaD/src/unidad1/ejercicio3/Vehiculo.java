package unidad1.ejercicio3;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vehiculo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String FICHERO = "vehiculos.dat";
	
	private String matricula;
	private String modelo;
	private double valor;
	
	public Vehiculo(String matricula, String modelo, double valor) {
		
		this.matricula = matricula;
		this.modelo = modelo;
		this.valor = valor;
	}
	
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getmodelo() {
        return modelo;
    }

    public void setmodelo(String marcaModelo) {
        this.modelo = marcaModelo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public static Vehiculo[] listar() {
    	File f = new File(FICHERO);
    	
    	if (!f.exists()) {
    		return new Vehiculo[0];
    	}
    	
    	List<Vehiculo> vList = new ArrayList<>();
    	
    	try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
    		
    		while(true) {
    			Vehiculo v = (Vehiculo) ois.readObject();
    			vList.add(v);
    		}
    		
    	}catch(EOFException e) {

    	}catch(IOException | ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	
    	return vList.toArray(new Vehiculo[0]);
    }
    
    public static Vehiculo buscarObjetoEnFichero(String matricula) {
    	File f = new File(FICHERO);
    	
    	if(!f.exists()) {
    		return null;
    	}
    	
    	try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
    	
    		while(true) {
    			Vehiculo v = (Vehiculo) ois.readObject();
    			if (v.getMatricula().trim().equalsIgnoreCase(matricula.trim())) {
    				return v;
    			}
    		}
    		
    	} catch(EOFException e){
    		return null;
    	} catch(IOException | ClassNotFoundException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public void grabar() {
    	List<Vehiculo> vList = new ArrayList<>();
    	Vehiculo[] vehiculos = Vehiculo.listar();
    	
    	if(vehiculos != null) {
    		vList.addAll(Arrays.asList(vehiculos));
    	}
    	
    	vList.add(this);
    	
    	try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))){
    		for(Vehiculo v : vList) {
    			oos.writeObject(v);
    		}
    	}catch (IOException e){
    		e.printStackTrace();
    	}
    }

    @Override
    public String toString() {
        return "Vehiculo [Matrícula = " + matricula + ", Marca/Modelo = " + modelo + ", Valor = " + valor + " €]";
    }
}
