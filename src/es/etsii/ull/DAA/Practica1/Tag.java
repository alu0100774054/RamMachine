package es.etsii.ull.DAA.Practica1;
/**
 * Implmenta una etiqueta, es capaz de almacenar su nombre y la linea en la que se encuentra.
 * @author erikbarretodevera
 *
 */
public class Tag {
	private String tag;	// etiqueta
	private int linea;	// linea donde se encuentra la etiqueta
	
	public Tag(String tag) {
		this.tag = tag;
	}
	public Tag(String tag, int ln) {
		this.tag = tag;
		linea = ln;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	public boolean iguales(Tag other) {
		return (this.getTag() == other.getTag());
	}
}