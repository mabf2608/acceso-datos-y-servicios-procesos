package repasoExamen.unidad1;

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

public class Alumno implements Serializable {
		
		private static final long serialVersionUID = 2L;
		
		private String dni;
		private String nombre;
		private double nota;
		
		public Alumno (String dni, String nombre, double nota)
		{
			this.dni = dni;
			this.nombre = nombre;
			this.nota = nota;
		}

		public String getDni() {
			return dni;
		}
		public void setDni(String dni) {
			this.dni = dni;
		}

		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public double getNota() {
			return nota;
		}
		public void setNota(double nota) {
			this.nota = nota;
		}
		
		public static Alumno[] listar(File fDat)
		{
			if (!fDat.exists())
				return new Alumno[0];
			
			List<Alumno> aList = new ArrayList<Alumno>();
			
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fDat))){
				while (true) {
					Alumno a = (Alumno) ois.readObject();
					aList.add(a);
				}
			} catch (EOFException e){
				
			} catch (IOException | ClassNotFoundException e){
				e.printStackTrace();
			}
			return aList.toArray(new Alumno[0]);
		}
		
		public static Alumno buscarAlumnoFichero(String dni, File fDat) 
		{
			if (!fDat.exists())
				return null;
			
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fDat))){
				while (true)
				{
					Alumno a = (Alumno) ois.readObject();
					if(a.getDni().trim().equalsIgnoreCase(dni.trim())) {
						return a;
					}
				}
			} catch (EOFException e) {
				return null;
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public void insertarAlumno(File fDat) 
		{
			
			List<Alumno> aList = new ArrayList<Alumno>();
			Alumno[] alumnos = Alumno.listar(fDat);
			
			if (alumnos != null)
				aList.addAll(Arrays.asList(alumnos));
			
			aList.add(this);
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fDat))) {
				for (Alumno a : aList) 
				{
					oos.writeObject(a);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public String toString() {
			return "Alumno [DNI = " + dni + ", Nombre = " + nombre + ", Nota Media = " + nota + ".]";
	}
}
