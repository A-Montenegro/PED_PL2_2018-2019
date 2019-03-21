package compiler.syntax.nonTerminal;

import java.util.ArrayList;

public class CtesLista extends NonTerminal {
	private ArrayList<Cte> ctesLista;
	
	public CtesLista(Cte primeraConstante) {
		super();
		ctesLista= new ArrayList<Cte>();
		ctesLista.add(primeraConstante);
	}
	
	public void addCte(Cte constante) {
		ctesLista.add(constante);
	}
}
