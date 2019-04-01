package compiler.syntax.nonTerminal;

import java.util.Stack;

public class Subs extends NonTerminal {
	private Stack<CuerpoModulo> pilaCuerpoModulos;
	
	public Subs() {
		super();
		this.pilaCuerpoModulos= new Stack<CuerpoModulo>();
	}
	
	public Stack<CuerpoModulo> getPilaCuerpoModulos(){
		return pilaCuerpoModulos;
	}
	
	public void addCuerpoModulo(CuerpoModulo cuerpoModulo) {
		pilaCuerpoModulos.push(cuerpoModulo);
	}
}
