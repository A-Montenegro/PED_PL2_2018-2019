package compiler.syntax.nonTerminal;

import java.util.ArrayList;

/**
 * 
 * Clase que almacena una lista de constantes.
 * @author Alberto Martínez Montenegro
 */
public class CtesLista extends NonTerminal {
	private ArrayList<Cte> ctesLista;
	
	/**
	 * Constructos de clase.
	 * @param primeraConstante
	 */
	public CtesLista(Cte primeraConstante) {
		super();
		ctesLista= new ArrayList<Cte>();
		ctesLista.add(primeraConstante);
	}

	/**
	 * Método que añade una constante.
	 * @param constante
	 */
	public void addCte(Cte constante) {
		ctesLista.add(constante);
	}
}
