package compiler.syntax.nonTerminal;

import java.util.Stack;

/**
 * Clase que representa a una serie de variables del mismo tipo.
 * @author Alberto Martínez Montenegro
 */
public class VarSeqDeIds extends NonTerminal {
	private Stack<String> nombresVariables;
	
	/**
	 * 
	 * Constructor de clase.
	 */
	public VarSeqDeIds() {
		super();
		this.nombresVariables=new Stack<String>();
	}
	
	/**
	* Devuelve la lista total de nombres
	*/
	public Stack<String> getNombresVariables() {
		return nombresVariables;
	}
	

	/**
	* Apila el elemento que se le pasa como parámetro
	*/
	public void addNombreVariable(String nombreVariable){
		nombresVariables.push(nombreVariable.toUpperCase());
	}
}