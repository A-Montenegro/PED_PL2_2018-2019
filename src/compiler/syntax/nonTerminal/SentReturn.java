package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeSimple;

public class SentReturn extends Sent {
	private TypeSimple tipoReturn;
	
	public SentReturn(TypeSimple tipoReturn) {
		super();
		this.tipoReturn=tipoReturn;
	}
	public TypeSimple getTipoReturn() {
		return tipoReturn;
	}
}
