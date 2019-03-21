package compiler.syntax.nonTerminal;

import java.util.Stack;

public class VarsLista extends NonTerminal {
	private Stack<Var> 	varsLista;
	
	public VarsLista() {
		super();
		varsLista= new Stack<Var>();
	}
	
	public VarsLista(Var primeraVariable) {
		super();
		varsLista= new Stack<Var>();
		varsLista.push(primeraVariable);
	}
	
	public Stack<Var> getVarsLista(){
		return varsLista;
	}
	
	public void addVar(Var var) {
		varsLista.push(var);
	}
}
