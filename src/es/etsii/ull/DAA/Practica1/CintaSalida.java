package es.etsii.ull.DAA.Practica1;

import java.io.FileWriter;
import java.util.Scanner;
/**
 * Hereda de Cinta e implementa una cinta que almacena los datos de salida del fichero.
 * @author erikbarretodevera
 *
 */
public class CintaSalida extends Cinta {
	
	Cinta cintaSalida;	// Cinta de salida
	
	public CintaSalida() {
		cintaSalida = new Cinta();
	}
	public void escribir(int elemento) {
		cintaSalida.setElemento(elemento);
	}
	public void reset() {
		cintaSalida.clear();
	}
	public void escribirFichero() {
		FileWriter fichero = null;
	    try {
	      fichero = new FileWriter("cintaSalida.txt");
	      for (int i = 0;i < cintaSalida.getPosicion() ;i++ ) {
	        fichero.write(String.valueOf(cintaSalida.getElemento(i)));
	      }
	      fichero.close();
	    } catch (Exception ex) {
	        System.out.println("Mensaje de la excepción: " + ex.getMessage());
	    }
	}
	public void leerFichero(String nombreFichero) {
		Scanner scan = null;
		try {
			// Leemos el contenido del fichero
			System.out.println("Leemos el contenido del fichero " + nombreFichero);
			scan = new Scanner(nombreFichero);

			// Leemos linea a linea el fichero
			while (scan.hasNextLine()) {
				String linea = scan.nextLine();// Guardamos la linea en un String
				System.out.println(linea);
				cintaSalida.setElemento(Integer.parseInt(linea));	
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

}
