package compiler.syntax.nonTerminal;

import java.util.Stack;

/**
 * Clase que representa un conjunto de sentencias.
 * Objetivos de la clase
 * @author Alberto Martínez Montenegro
 */
public class Sentencias extends NonTerminal {
	private Stack<Sent> pilaSentencias;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param pilaSentencias
	 */
	public Sentencias(Stack<Sent> pilaSentencias) {
		super();
		this.pilaSentencias= pilaSentencias;
	}
	
	/**
	 * 
	 * Constructor de clase.
	 * @param sentencia
	 */
	public Sentencias(Sent sentencia) {
		super();
		this.pilaSentencias= new Stack<Sent>();
		addSentencia(sentencia);
	}
	
	/**
	 * 
	 * Constructor de clase.
	 */
	public Sentencias() {
		super();
		this.pilaSentencias= new Stack<Sent>();
	}
	
	/**
	 * 
	 * @return
	 */
	public Stack<Sent> getPilaSentencias(){
		return pilaSentencias;
	}
	
	/**
	 * 
	 * @param sentencia
	 */
	public void addSentencia(Sent sentencia) {
		pilaSentencias.push(sentencia);
	}
}
