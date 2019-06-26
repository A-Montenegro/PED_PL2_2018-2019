package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeSimple;

/**
 * Clase para la gestion de constantes.
 * @author Alberto Martínez Montenegro
 */
public class Cte extends NonTerminal {
	private String nombre;
	private CteValor cteValor;
	
	public Cte(String nombre, CteValor cteValor) {
		super();
		this.nombre=nombre.toUpperCase();
		this.cteValor=cteValor;
	}
	
	/**
	* @return devuelve el tipo
	*/
	public String getNombre() {
		return nombre;
	}
	
	public CteValor getCteValor() {
		return cteValor;
	}
	
	/**
	* @return devuelve el valor
	*/
	public int getValor() {
		return cteValor.getValor();
	}

	public TypeSimple getTipoValor() {
		return cteValor.getTipoValor();
	}
	
}
