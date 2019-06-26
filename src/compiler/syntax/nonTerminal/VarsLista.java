package compiler.syntax.nonTerminal;

import java.util.Stack;

/**
 * 
 * Clase que representa a una lista de variables.
 * @author Alberto Martínez Montenegro
 */
public class VarsLista extends NonTerminal {
	private Stack<Var> 	varsLista;
	
	/**
	 * 
	 * Constructor de clase.
	 */
	public VarsLista() {
		super();
		varsLista= new Stack<Var>();
	}
	
	/**
	 * 
	 * Constructor de clase.
	 * @param primeraVariable
	 */
	public VarsLista(Var primeraVariable) {
		super();
		varsLista= new Stack<Var>();
		varsLista.push(primeraVariable);
	}
	
	/**
	 * 
	 * @return
	 */
	public Stack<Var> getVarsLista(){
		return varsLista;
	}
	
	/**
	 * 
	 * @param var
	 */
	public void addVar(Var var) {
		varsLista.push(var);
	}
}
