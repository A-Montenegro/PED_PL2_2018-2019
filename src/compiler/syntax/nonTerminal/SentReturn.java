package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeSimple;

/**
 * 
 * Clase que representa a una sentencia return.
 * @author Alberto Martínez Montenegro
 */
public class SentReturn extends Sent {
	private TypeSimple tipoReturn;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param tipoReturn
	 */
	public SentReturn(TypeSimple tipoReturn) {
		super();
		this.tipoReturn=tipoReturn;
	}
	
	/**
	 * 
	 * @return
	 */
	public TypeSimple getTipoReturn() {
		return tipoReturn;
	}
}
