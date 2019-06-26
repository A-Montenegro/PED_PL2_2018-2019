package compiler.syntax.nonTerminal;

import java.util.Stack;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * 
 * Clase que representa a una serie de parámetros que se usarán para definir la llamada a un procedimiento.
 * @author Alberto Martínez Montenegro
 */
public class SentCallParams extends NonTerminal {
	private Stack<TypeIF> pilaTipos;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param primerTipo
	 */
	public SentCallParams(TypeIF primerTipo) {
		super();
		pilaTipos= new Stack<TypeIF>();
		pilaTipos.push(primerTipo);
	}
	
	/**
	 * 
	 * Constructor de clase.
	 */
	public SentCallParams() {
		super();
		pilaTipos= new Stack<TypeIF>();
	}
	
	/**
	 * 
	 * @return
	 */
	public Stack<TypeIF> getPilaTipos(){
		return pilaTipos;
	}
	
	/**
	 * 
	 * @param tipoPrimitivo
	 */
	public void addTipo(TypeIF tipoPrimitivo) {
		pilaTipos.push(tipoPrimitivo);
	}
	
}
