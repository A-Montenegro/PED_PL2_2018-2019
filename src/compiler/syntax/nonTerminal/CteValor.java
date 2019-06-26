package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeSimple;

/**
 * Clase que almacena el valor de una constante
 * @author Alberto Martínez Montenegro
 */
public class CteValor extends NonTerminal {
	private int valor;
	private TypeSimple tipoValor;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param valor
	 * @param tipoValor
	 */
	public CteValor(String valor,TypeSimple tipoValor) {
		super();
		switch(valor) {
		case "TRUE":
			this.valor= 1;
		break;
		case "FALSE":
			this.valor= 0;
		break;
		default:
			this.valor=Integer.parseInt(valor);
		break;
		}
		this.tipoValor= tipoValor;
	}

	/**
	* @return devuelve el valor
	*/
	public int getValor() {
		return valor;
	}
	
	/**
	 * Devuelve el tipo del valor.
	 * @return
	 */
	public TypeSimple getTipoValor() {
		return tipoValor;
	}
}
