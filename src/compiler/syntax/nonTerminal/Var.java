package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Clase que representa a la declaración de una variable
 * @author Alberto Martínez Montenegro
 */
public class Var extends NonTerminal {
	private VarSeqDeIds varSeqDeIds;
	private TypeIF tipo;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param varSeqDeIds
	 * @param tipo
	 */
	public Var(VarSeqDeIds varSeqDeIds, TypeIF tipo) {
		super();
		this.varSeqDeIds= varSeqDeIds;
		this.tipo= tipo;
	}
	
	/**
	 * 
	 * Constructor de clase.
	 * @param nombreVariable
	 * @param tipo
	 */
	public Var(String nombreVariable, TypeIF tipo) {
		super();
		this.varSeqDeIds= new VarSeqDeIds();
		varSeqDeIds.addNombreVariable(nombreVariable);
		this.tipo= tipo;
	}
	
	/**
	* @return devuelve el tipo
	*/
	public TypeIF getTipo() {
		return tipo;
	}

	/**
	* @return devuelve la lista de variables
	*/
	public VarSeqDeIds getVarSeqDeIds() {
		return varSeqDeIds;
	}

	/**
	 * Devuelve la primera variable de la pila de variables.
	 * @return
	 */
	public String getVariableUnitaria() {
		return varSeqDeIds.getNombresVariables().peek();
	}
}