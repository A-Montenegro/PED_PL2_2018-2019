package compiler.syntax.nonTerminal;

import java.util.Stack;

/**
 * 
 * Clase que representa al cuerpo del módulo de un subprograma.
 * @author Alberto Martínez Montenegro
 */
public class Subs extends NonTerminal {
	private Stack<CuerpoModulo> pilaCuerpoModulos;
	
	/**
	 * 
	 * Constructor de clase.
	 */
	public Subs() {
		super();
		this.pilaCuerpoModulos= new Stack<CuerpoModulo>();
	}
	
	/**
	 * 
	 * @return
	 */
	public Stack<CuerpoModulo> getPilaCuerpoModulos(){
		return pilaCuerpoModulos;
	}
	
	/**
	 * 
	 * @param cuerpoModulo
	 */
	public void addCuerpoModulo(CuerpoModulo cuerpoModulo) {
		pilaCuerpoModulos.push(cuerpoModulo);
	}
}
