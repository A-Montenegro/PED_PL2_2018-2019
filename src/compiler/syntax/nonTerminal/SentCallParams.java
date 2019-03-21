package compiler.syntax.nonTerminal;

import java.util.Stack;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentCallParams extends NonTerminal {
	private Stack<TypeIF> pilaTipos;
	
	public SentCallParams(TypeIF primerTipo) {
		super();
		pilaTipos= new Stack<TypeIF>();
		pilaTipos.push(primerTipo);
	}
	
	public SentCallParams() {
		super();
		pilaTipos= new Stack<TypeIF>();
	}
	
	public Stack<TypeIF> getPilaTipos(){
		return pilaTipos;
	}
	
	public void addTipo(TypeIF tipoPrimitivo) {
		pilaTipos.push(tipoPrimitivo);
	}
	
}
