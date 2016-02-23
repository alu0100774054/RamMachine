package es.etsii.ull.DAA.Practica1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * Implementa una máquina RAM que a partir de un programa dado opera 
 * resolviendo cualquier programa, operando con sus instrucciones.
 * @author erikbarretodevera
 *
 */
public class MaquinaRam {
	CintaEntrada entrada;
	CintaSalida salida;
	Vector<Integer> registros;
	Programa programa;
	private int ACCUM = 0;
	private Integer MIN = 100;
	private Integer MAX = 100;

	private boolean añadirEtiqueta(Tag t, Vector<Tag> etiquetas) {
		for (int i = 0; i < etiquetas.size(); i++) {
			if (etiquetas.get(i) == t) {
				return false;
			}
		}
		etiquetas.add(t);
		return true;
	}
	private boolean existeEtiqueta(Tag t, Vector<Tag> etiquetas) {
		for (int i = 0; i < etiquetas.size(); i++) {
			if (etiquetas.get(i) == t) {
				return true;
			}
		}
		return false;
	}
	private Integer obtenerEtiqueta(Tag t, Vector<Tag> etiquetas) throws MiExcepcion {
		for (int i = 0; i < etiquetas.size(); i++) {
			if (etiquetas.get(i) == t) {
				return etiquetas.get(i).getLinea();
			}
		}
		throw new MiExcepcion("error en obtener etiqueta");
	}
	private void load(int tipo, int operando) {
		if (tipo == 0) {	// IMMEDIATE
			registros.set(ACCUM , operando);
		} else if (tipo == 1) {	// DIRECT
			registros.set(ACCUM, registros.get(operando));
		} else if (tipo == 2) {	// POINTER
			registros.set(ACCUM, registros.get(registros.get(operando)));
		}
	}
	private void store(int tipo, int operando) {
		if (tipo == 1) {	// DIRECT
			if (operando >= registros.size()) {
				registros.setSize(operando + 1);
			}
			registros.set(operando, registros.get(ACCUM));
		} else if (tipo == 2) {	// POINTER
			if (operando >= registros.size()) {
				System.out.println("Intento de acceso a registros no inicializados");
			}
			if (registros.get(operando) >= registros.size()) {
				registros.setSize(registros.get(operando) + 1);
			}
			registros.set(registros.get(operando), registros.get(ACCUM));
		}
	}
	private void add(int tipo, int operando) {
		if (tipo == 0) {	// IMMEDIATE
			registros.set(ACCUM, registros.get(ACCUM) + operando);
			if (registros.get(ACCUM) >= MAX || registros.get(ACCUM) <= MIN) {
				System.out.println("Desbordamiento en ADD");
			}
		} else if (tipo == 1) {	// DIRECT
			registros.set(ACCUM, registros.get(ACCUM) + registros.get(operando));
			if (registros.get(ACCUM) >= MAX || registros.get(ACCUM) <= MIN) {
				System.out.println("Desbordamiento en ADD");
			}
		} else if (tipo == 2) {	// POINTER
			registros.set(ACCUM, registros.get(ACCUM) + registros.get(registros.get(operando)));
			if (registros.get(ACCUM) >= MAX || registros.get(ACCUM) <= MIN) {
				System.out.println("Desbordamiento en ADD");
			}
		}
	}
	private void sub(int tipo, int operando) {
		if (tipo == 0) {	// IMMEDIATE
			registros.set(ACCUM, registros.get(ACCUM) - operando);
			if (registros.get(ACCUM) >= MAX || registros.get(ACCUM) <= MIN) {
				System.out.println("Desbordamiento en SUB");
			}
		} else if (tipo == 1) {	// DIRECT
			registros.set(ACCUM, registros.get(ACCUM) - registros.get(operando));
			if (registros.get(ACCUM) >= MAX || registros.get(ACCUM) <= MIN) {
				System.out.println("Desbordamiento en SUB");
			}
		} else if (tipo == 2) {	// POINTER
			registros.set(ACCUM, registros.get(ACCUM) - registros.get(registros.get(operando)));
			if (registros.get(ACCUM) >= MAX || registros.get(ACCUM) <= MIN) {
				System.out.println("Desbordamiento en SUB");
			}
		}
	}
	private void mult(int tipo, int operando) {
		if (tipo == 0) {	// IMMEDIATE
			registros.set(ACCUM, registros.get(ACCUM) * operando);
			if (registros.get(ACCUM) >= MAX || registros.get(ACCUM) <= MIN) {
				System.out.println("Desbordamiento en MULT");
			}
		} else if (tipo == 1) {	// DIRECT
			registros.set(ACCUM, registros.get(ACCUM) * registros.get(operando));
			if (registros.get(ACCUM) >= MAX || registros.get(ACCUM) <= MIN) {
				System.out.println("Desbordamiento en MULT");
			}
		} else if (tipo == 2) {	// POINTER
			registros.set(ACCUM, registros.get(ACCUM) * registros.get(registros.get(operando)));
			if (registros.get(ACCUM) >= MAX || registros.get(ACCUM) <= MIN) {
				System.out.println("Desbordamiento en MULT");
			}
		}
	}
	private void div(int tipo, int operando) {
		if (tipo == 0) {	// IMMEDIATE
			if (operando == 0) {
				System.out.println("Se intenta dividir por cero.");
			}
			registros.set(ACCUM, registros.get(ACCUM) / operando);
		} else if (tipo == 1) {	// DIRECT
			if (registros.get(operando) == 0) {
				System.out.println("Se intenta dividir por cero.");
			}
			registros.set(ACCUM, registros.get(ACCUM) / registros.get(operando));
		} else if (tipo == 2) {	// POINTER
			if (registros.get(registros.get(operando)) == 0) {
				System.out.println("Se intenta dividir por cero.");
			}
			registros.set(ACCUM, registros.get(ACCUM) / registros.get(registros.get(operando)));
		}
	}
	private void leer(int tipo, int operando) throws MiExcepcion {

		if (tipo == 1) {
			if (operando >= registros.size()) {
				registros.setSize(operando + 1); 
			}
			registros.set(operando, entrada.leer());
		}
		else if (tipo == 2) {
			if (operando >= registros.size()) {
				System.out.println("Intento de acceso a registros no inicializados.");
				throw new MiExcepcion("error en leer de maquina");
			}
			if (registros.get(operando) >= registros.size()) {
				registros.setSize(registros.get(operando) + 1);
			}
			registros.set(registros.get(operando), entrada.leer());
		}
	}
	private void escribir(int tipo, int operando) {
		if (tipo == 0) {	// INMEDIATE
			salida.escribir(operando);
		}
		else if (tipo == 1) {	// DIRECT
			salida.escribir(registros.get(operando));
		}
		else if (tipo == 2) {	// POINTER
			salida.escribir(registros.get(registros.get(operando)));
		}
	}
	private void jgtz(int etiqueta) {
		if (registros.get(ACCUM) > 0) {
			programa.setPc(etiqueta);
		}
		// comprobar el else
	}
	private void jzero(int etiqueta) {
		if (registros.get(ACCUM) == 0) {
			programa.setPc(etiqueta);
		}
		//comprobar el else
	}
	private void halt() {
		salida.escribirFichero();
	}
	private void reset() {
		entrada.reset();
		salida.reset();
		programa.setPc(0);
		registros.clear();
		registros.add(0);
	}
	public void leer_codigo(String nombreArchivo, String archivoEntrada) throws FileNotFoundException, IOException {
		BufferedReader bf = new BufferedReader(new FileReader(nombreArchivo));
		String linea;
		String aux_etq = null;
		String aux_ins = null;
		String aux_dir = null;
		int aux_ele = 0;
		Tag etq = null;
		Instruccion ins = null;
		int ln = 0;
		Vector<Instruccion> vIns = new Vector<>();
		Vector<Tag> vTag = new Vector<>();

		while ((linea = bf.readLine()) != null) {
			
			ln++;
			String[] token = linea.split("\\s+");
			// recorrer los tokens
			for (int i = 0; i < token.length; i++) {
				// comprobar si es etiqueta
				if ((i < token.length - 1) && (token[i+1].contains(":"))) {
					aux_etq = new String(token[i]);
				}
				//comrpueba si es instruccion
				else if ((!token[i].equals(":")) && (i < token.length-1)) {
					aux_ins = new String(token[i]);
				}
				//comprueba si es nº o simbolo
				else if((i == token.length-1) && (!token[i].equals(":"))) {
					if (token[i].equals("HALT"))
						aux_ins = new String(token[i]);
					else if ((aux_ins.equals("JUMP")) || (aux_ins.equals("JZERO")) || (aux_ins.equals("JGTZ")))
						aux_dir = new String(token[i]);
					else{
						//divide el ultimo token en 2 si es necesario en direccionamiento y valor
						String prueba = token[i];
						char[] aux = prueba.toCharArray();
						if((aux[0] == '*') || (aux[0] == '=')){
							aux_dir = Character.toString(aux[0]);
							aux_ele =  Character.getNumericValue(aux[1]); //modificado
						}
						else {
							aux_ele =Character.getNumericValue(aux[0]);
						}
					}
				} 
			}
			System.out.println("tengo: " + aux_etq + "..." + aux_ins + "..." + aux_dir + "..." + aux_ele);
			/*if (aux_etq != null) {
				etq = new Tag(aux_etq, ln);
				vTag.addElement(etq);
			}
			ins = new Instruccion(pasarInstruccion(aux_ins), pasarDireccionamiento(aux_dir), aux_ele);
			vIns.addElement(ins);*/
			aux_etq = null;
			aux_ins = null;
			aux_dir = null;
			aux_ele = 0;
		}
		programa.setPrograma(vIns, vTag);
		entrada.setNombreFichero(archivoEntrada);
		entrada.leerFichero();
		salida.escribirFichero();

	}
	private int pasarDireccionamiento(String aux_dir) {
		int aux = -1;
		switch (aux_dir) {
		case "=":
			aux = 0;
			break;
		case "*":
			aux = 2;
		default:
			aux = 1;
			break;
		}
		return aux;
	}
	private int pasarInstruccion(String aux_ins) {
		int aux = -1;
		switch (aux_ins) {
		case "LOAD":
			aux = 0;
			break;
		case "STORE":
			aux = 1;
			break;
		case "ADD":
			aux = 2;
			break;
		case "SUB":
			aux = 3;
			break;
		case "MULT":
			aux = 4;
			break;
		case "DIV":
			aux = 5;
			break;
		case "READ":
			aux = 6;
			break;
		case "WRITE":
			aux = 7;
			break;
		case "JUMP":
			aux = 8;
			break;
		case "JGTZ":
			aux = 9;
			break;
		case "JZERO":
			aux = 10;
			break;
		case "HALT":
			aux = 11;
			break;

		default:
			System.out.println("error en conversos de instruccion.");
			break;
		}
		return aux;
	}
		void run(boolean traza) throws MiExcepcion {
			Instruccion ins = null;
			ins.opcode = 0;
			reset();
			while (ins.opcode != 11) {

				ins = programa.run();

				switch (ins.opcode) {
				case 0: load(ins.tipo, ins.operando); break;
				case 1: store(ins.tipo, ins.operando); break;
				case 2: add(ins.tipo, ins.operando); break;
				case 3: sub(ins.tipo, ins.operando); break;
				case 4: mult(ins.tipo, ins.operando); break;
				case 5: div(ins.tipo, ins.operando); break;
				case 6: leer(ins.tipo, ins.operando); break;
				case 7: escribir(ins.tipo, ins.operando); break;
				case 8: programa.setPc((ins.operando)); break;
				case 9: jgtz(ins.operando); break;
				case 10: jzero(ins.operando); break;
				case 11: halt(); break;
				default:
					break;
				}

				imprimirInstruccion(ins);
				System.out.println("\n");
				if (traza) {
					imprimirRegistros();
				}
			}
			System.out.println("Se escribio : " + salida.toString() + "\n");
		}
		void imprimirInstruccion(Instruccion ins) throws MiExcepcion {
			switch (ins.opcode) {
			case 0: System.out.println("LOAD "); 
			break;
			case 1: System.out.println("STORE "); 
			break;
			case 2: System.out.println("ADD "); 
			break;
			case 3: System.out.println("SUB "); 
			break;
			case 4: System.out.println("MULT "); 
			break;
			case 5: System.out.println("DIV ");
			break;
			case 6: System.out.println("READ "); 
			break;
			case 7: System.out.println("WRITE ");
			break;
			case 8: System.out.println("JUMP "); 
			break;
			case 9: System.out.println("JGTZ ");
			break;
			case 10: System.out.println("JZERO "); 
			break;
			case 11: System.out.println("HALT "); 
			break;
			}
			if (ins.tipo == 0) { // INMEDIATE
				System.out.println(" = " + ins.operando);
			} else if (ins.tipo == 1) {	// DIRECT
				System.out.println(ins.operando);
			} else if (ins.tipo == 2) {	// POINTER
				System.out.println(" * " + ins.operando);
			} else if (ins.tipo == 3) {	// TAGJUMP
				System.out.println(programa.getNombreEtiqueta(ins.operando));
			}
		}
		public void imprimirRegistros() {
			System.out.println("Imprimiendo registros ... ");
			for (int i = 0; i < registros.size(); i++) {
				System.out.println("R [ " + i + " ] : " + registros.get(i));
			}
		}
		public void imprimirPrograma() throws MiExcepcion {
			String etiqueta;
			for (int i = 0; i < programa.getPrograma().size(); i++) {
				System.out.println(i + "\t");
				imprimirInstruccion(programa.getPrograma().get(i));
				System.out.println("\n");
			}
			System.out.println("Etiquetas\n*********\n");
			for (int i = 0; i < programa.getPrograma().size(); i++) {
				try {
					etiqueta = programa.getNombreEtiqueta(i);
					System.out.println(etiqueta + " ---> linea " + i + "\n");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Error en imprimir programa : " + e);
				}
			}
		}
		public void imprimirEntrada() {
			entrada.leerFichero();
		}
		public void imprimirSalida(String nombreFichero) {
			salida.leerFichero(nombreFichero);
		}
		private static void ayuda() {
			System.out.println( "\n**********************" );
			System.out.println( "r : ver los registros" );
			System.out.println( "t : traza" );
			System.out.println( "g : go" );
			System.out.println( "s : desensamblador" );
			System.out.println( "i : ver cinta entrada" );
			System.out.println( "o : ver cinta salida" );
			System.out.println( "h : ayuda" );
			System.out.println( "x : salir" );
			System.out.println( "**********************\n" );
		}
		public static void main(String[] args) throws IOException, MiExcepcion {
			MaquinaRam Maquina = new MaquinaRam();
			String opcionMenu = "0";	// guarda la opción escogida del menú
			InputStreamReader in =  new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(in);
			try {
				System.out.println("Iniciando la máquina ...");
				Maquina.leer_codigo(args[0], args[1]);
			} catch (Exception e) {
				System.out.println("Error, no es posible inicializar la maquina");
			}

			while (!opcionMenu.equals("x")) {
				System.out.println("Introduce una opción ---> ");
				opcionMenu = br.readLine();
				switch (opcionMenu) {
				case "r":
					Maquina.imprimirRegistros();
					break;
				case "t":
					System.out.println("traza");
					Maquina.run(true);
					break;
				case "g":
					System.out.println("go");
					Maquina.run(false);
					break;
				case "i":
					System.out.println("ver cinta de entrada");
					Maquina.imprimirEntrada();
					break;
				case "o":
					System.out.println("ver cinta de salida");
					Maquina.imprimirSalida("Salida.txt");
					break;
				case "s":
					System.out.println("desensamblador");
					Maquina.imprimirPrograma();
					break;
				case "h":
					ayuda();
					break;
				case "x":
					System.out.println("Apagando la máquina ...");
					System.exit(0);
				default:
					System.out.println("Opción erronea, pruebe una de las siguientes.");
					ayuda();
					break;
				}
			}
		}

	}
