package es.etsii.ull.DAA.Practica1;

public class Tag {
	private String tag;
	private int linea;
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