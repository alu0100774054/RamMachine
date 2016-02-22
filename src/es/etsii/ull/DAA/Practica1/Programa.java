package es.etsii.ull.DAA.Practica1;

import java.util.ArrayList;
import java.io.IOException;
import java.lang.Enum;


public class Programa {
	private ArrayList<Instruccion> programa;
	private int pc;
	private ArrayList<Tag> etiquetas;

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
	public ArrayList<Instruccion> getPrograma() {
		return programa;
	}
	public void setPrograma(ArrayList<Instruccion> programa, ArrayList<Tag> etiquetas) {
		this.programa = programa;
		this.etiquetas = etiquetas;
	}

}
