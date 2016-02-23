package es.etsii.ull.DAA.Practica1;

import java.io.File;
import java.util.Scanner;

public class CintaEntrada extends Cinta {
	
	Cinta cintaEntrada;
	String nombreFichero;
	
	public CintaEntrada() {
		cintaEntrada = new Cinta();
	}
	public int leer() {
		return cintaEntrada.getElemento(cintaEntrada.getPosicion());
	}
	public void reset() {
		cintaEntrada.clear();
	}
	public void leerFichero() {
		Scanner scan = null;
		try {
			// Leemos el contenido del fichero
			System.out.println("Leemos el contenido del fichero " + nombreFichero);
			scan = new Scanner(nombreFichero);

			// Leemos linea a linea el fichero
			while (scan.hasNextLine()) {
				String linea = scan.nextLine();// Guardamos la linea en un String
				System.out.println(linea);
				cintaEntrada.setElemento(Integer.parseInt(linea));	
			}
		} catch (Exception ex) {
			System.out.println("Mensaje: " + ex.getMessage());
		} finally {
			// Cerramos el fichero tanto si la lectura ha sido correcta o no
			try {
				if (scan != null)
					scan.close();
			} catch (Exception ex2) {
				System.out.println("Mensaje 2: " + ex2.getMessage());
			}
		}
	}
	public void setNombreFichero(String archivoEntrada) {
		// TODO Auto-generated method stub
		this.nombreFichero = archivoEntrada;
	}
}
