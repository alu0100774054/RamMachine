package es.etsii.ull.DAA.Practica1;
/**
 * Almacena la instrucción(opcode,tipo de direccionamiento y operando)
 * @author erikbarretodevera
 *
 */
public class Instruccion {
	public int opcode; // opcode de la operacion
	public int tipo;	// tipo de direccionamiento
	public int operando;	// valor de operando
	
	public Instruccion() {
		opcode = 0;
		tipo = 0;
		operando = 0;
	}
	public Instruccion(int op) {
		opcode = op;
		tipo = 0;
		operando = 0;
	}
	public Instruccion(int op, int tp) {
		opcode = op;
		tipo = tp;
		operando = 0;
	}
	public Instruccion(int op, int tp, int ope) {
		opcode = op;
		tipo = tp;
		operando = ope;
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
