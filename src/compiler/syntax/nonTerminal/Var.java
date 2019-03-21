package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * @author Alberto Martínez Montenegro
 */
public class Var extends NonTerminal {
	private VarSeqDeIds varSeqDeIds;
	private TypeIF tipo;
	
	public Var(VarSeqDeIds varSeqDeIds, TypeIF tipo) {
		super();
		this.varSeqDeIds= varSeqDeIds;
		this.tipo= tipo;
	}
	
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

	public String getVariableUnitaria() {
		return varSeqDeIds.getNombresVariables().peek();
	}
}