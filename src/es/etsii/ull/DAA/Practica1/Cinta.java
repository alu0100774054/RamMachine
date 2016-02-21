package es.etsii.ull.DAA.Practica1;

import java.util.ArrayList;

public class Cinta {
	
	ArrayList<Integer> cinta;	// Cinta de la máquina
	
	public Cinta() {
		cinta = new ArrayList<Integer>();
	}
	public void setElemento(Integer elemento) {
		cinta.add(elemento);
	}
	public Integer getElemento(int idx) {
		return cinta.get(idx);
	}
	public String toString() {
		StringBuffer linea = new StringBuffer(); 
		for (int i = 0; i < cinta.size(); i++) {
			linea.append(String.valueOf(cinta.get(i)));
		}
		return linea.toString();
	}
	public void clear() {
		cinta.clear();
	}
	public int getPosicion() {
		return cinta.size();
	}
	
}
