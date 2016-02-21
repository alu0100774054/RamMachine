package es.etsii.ull.DAA.Practica1;

import java.io.FileWriter;
import java.util.Scanner;

public class CintaSalida extends Cinta {
	
	Cinta cintaSalida;
	
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

}
