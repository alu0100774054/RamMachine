package es.etsii.ull.DAA.Practica1;

import java.util.ArrayList;
import java.util.Vector;
import java.io.IOException;
import java.lang.Enum;

/**
 * Estructura que contiene un conjunto de instrucciones un contador de programa
 * y guarda las etiquetas del programa
 * @author erikbarretodevera
 *
 */
public class Programa {
	private Vector<Instruccion> programa;
	private int pc;
	private Vector<Tag> etiquetas;

	public Programa() {
		pc = 0;
	}
	public Instruccion run() {
		return programa.get(pc);
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public String getNombreEtiqueta(int line) throws MiExcepcion {
		for (int i = 0; i < etiquetas.size(); i++) {
			if (etiquetas.get(i).getLinea() == line) {
				return etiquetas.get(i).getTag();
			}
		}
		throw new MiExcepcion("error en getNombreEtiqueta");
	}
	public Vector<Instruccion> getPrograma() {
		return programa;
	}
	public void setPrograma(Vector<Instruccion> coded, Vector<Tag> etiquetas2) {
		this.programa = coded;
		this.etiquetas = etiquetas2;
	}

}
