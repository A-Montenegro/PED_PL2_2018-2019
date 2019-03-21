package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeSimple;

/**
 * @author Alberto Martínez Montenegro
 */
public class CteValor extends NonTerminal {
	private String valor;
	private TypeSimple tipoValor;
	
	public CteValor(String valor,TypeSimple tipoValor) {
		super();
		this.valor= valor;
		this.tipoValor= tipoValor;
	}

	/**
	* @return devuelve el valor
	*/
	public String getValor() {
		return valor;
	}
	
	public TypeSimple getTipoValor() {
		return tipoValor;
	}
}
