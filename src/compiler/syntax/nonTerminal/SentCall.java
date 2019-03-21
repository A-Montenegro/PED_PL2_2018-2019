package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeProcedure;

public class SentCall extends Sent {
	private TypeProcedure tipoProcedimiento;
	
	public SentCall(TypeProcedure tipoProcedimiento) {
		super();
		this.tipoProcedimiento= tipoProcedimiento;
	}
	
	public TypeProcedure getTipoProcedimiento() {
		return tipoProcedimiento;
	}
}
