package es.etsii.ull.DAA.Practica1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class MaquinaRam {
	CintaEntrada entrada;
	CintaSalida salida;
	Vector<Integer> registros;
	Programa programa;
	private int ACCUM = 0;
	private Integer MIN = 100;
	private Integer MAX = 100;
	public static enum set1 { IMMEDIATE, DIRECT, POINTER, TAGJUMP }
	public static enum set2 { LOAD, STORE, ADD, SUB, MULT, DIV, READ, WRITE, JUMP, JGTZ, JZERO, HALT }

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
	public void leer_codigo(String nombreArchivo, String archivoEntrada) {
		Vector<String> vIns = new Vector<String>();
		Vector<Tag> etiquetas = new Vector<Tag>();
		Tag tag = null;
		Vector<Instruccion> coded = new Vector<Instruccion>();
		Instruccion dummy = null;
		File fichero = new File(nombreArchivo);
		Scanner s = null;

		try {
			// Leemos el contenido del fichero
			System.out.println("... Leemos el contenido del fichero ..." + fichero.getAbsolutePath());
			s = new Scanner(fichero);

			while (s.hasNextLine()) {
				String linea = s.nextLine(); 	// Guardamos la linea en un String
				if (linea.contains(";") == false) {
					vIns.add(linea);	// Guardamos las instrucciones
				}
			}
			System.out.println(vIns.size());
			String palabra,subpalabra;
			StringTokenizer elementos, subelementos;
			Vector<String> cadenaTokenizada = new Vector<String>();
			// Para cada instrucción (linea del fichero) tokenizar
			for (int j = 0; j < vIns.size(); j++) {
				elementos = new StringTokenizer(vIns.get(j));
				while (elementos.hasMoreTokens()) {
					palabra = elementos.nextToken();
					subelementos = new StringTokenizer(palabra, ":");
					while (subelementos.hasMoreTokens()) {
						subpalabra = subelementos.nextToken();
						cadenaTokenizada.add(subpalabra);
					}
				}
			}
			int j = 0;
			for (int i = 0; i < cadenaTokenizada.size(); i++) {
				System.out.println("token " + i + " : " + cadenaTokenizada.get(i));
				//busca etiqueta
				if (cadenaTokenizada.get(i).contains("[a-z]") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es una etiqueta.");
					//tag = new Tag(cadenaTokenizada.get(i), i);
				} else if (cadenaTokenizada.get(i).contains("LOAD") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("STORE") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("ADD") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("SUB") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("MULT") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("DIV") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("READ") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("WRITE") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("JUMP") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");
					
				} else if (cadenaTokenizada.get(i).contains("JGTZ") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("JZERO") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("HALT") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un opcode");

				} else if (cadenaTokenizada.get(i).contains("=") == true) {
					System.out.println(cadenaTokenizada.get(i) + " es un IMMEDIATE");

				} else if (cadenaTokenizada.get(i).contains("=") == true) {

					System.out.println(cadenaTokenizada.get(i) + " es un POINTER");
				} else if (cadenaTokenizada.get(i).contains("[0-9]") == true) {

					System.out.println(cadenaTokenizada.get(i) + " es un registro");
				}
				else {
					System.out.println("No se reconoce la instrucción");
				}
			}
		} catch (Exception ex) {
			System.out.println("Mensaje en leer: " + ex.getMessage());
		}
		programa.setPrograma(coded, etiquetas);
		entrada.setNombreFichero(archivoEntrada);	
		entrada.leerFichero();
		salida.escribirFichero();
	}
	void run(boolean traza) throws MiExcepcion {
		Instruccion ins = null;
		ins.opcode = -1;
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
		System.out.println(entrada.toString());
	}
	public void imprimirSalida() {
		System.out.println(salida.toString());
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
				Maquina.imprimirSalida();
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
