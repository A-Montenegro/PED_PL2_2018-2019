package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeProcedure;

/**
 * 
 * Clase que representa una sentencia de llamada a procedimiento.
 * @author Alberto Martínez Montenegro
 */
public class SentCall extends Sent {
	private TypeProcedure tipoProcedimiento;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param tipoProcedimiento
	 */
	public SentCall(TypeProcedure tipoProcedimiento) {
		super();
		this.tipoProcedimiento= tipoProcedimiento;
	}
	
	/**
	 * 
	 * @return
	 */
	public TypeProcedure getTipoProcedimiento() {
		return tipoProcedimiento;
	}
}
