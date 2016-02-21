package es.etsii.ull.DAA.Practica1;

public class Instruccion {
	public int opcode;
	public int tipo;
	public int operando;
	public Instruccion() {
		opcode = 0;
		tipo = 0;
		operando = 0;
	}
	public int getOpcode() {
		return opcode;
	}
	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getOperando() {
		return operando;
	}
	public void setOperando(int operando) {
		this.operando = operando;
	}

}
