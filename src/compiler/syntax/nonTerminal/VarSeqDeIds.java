package compiler.syntax.nonTerminal;

import java.util.Stack;

/**
 * @author Alberto Mart�nez Montenegro
 */
public class VarSeqDeIds extends NonTerminal {
	private Stack<String> nombresVariables;
	
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
	* Apila el elemento que se le pasa como par�metro
	*/
	public void addNombreVariable(String nombreVariable){
		nombresVariables.push(nombreVariable.toUpperCase());
	}
}