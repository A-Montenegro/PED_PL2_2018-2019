package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeSimple;

/**
 * 
 * Clase que almacena el campo de un registro
 * @author Alberto Martínez Montenegro
 */
public class RegCampo extends NonTerminal {
	private String nombreVariable;
	private TypeSimple tipoPrimitivoVariable;
	private int numeroLinea;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param nombreVariable
	 * @param tipoPrimitivoVariable
	 * @param numeroLinea
	 */
	public RegCampo(String nombreVariable, TypeSimple tipoPrimitivoVariable, int numeroLinea) {
		super();
		this.nombreVariable=nombreVariable.toUpperCase();
		this.tipoPrimitivoVariable= tipoPrimitivoVariable;
		this.numeroLinea=numeroLinea;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNombreVariable() {
		return nombreVariable;
	}
	
	/**
	 * 
	 * @return
	 */
	public TypeSimple getTipoPrimitivoVariable() {
		return tipoPrimitivoVariable;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNumeroLinea() {
		return numeroLinea;
	}
	
	/**
	 * 
	 * @param nombreVariable
	 */
	public void setNombreVariable(String nombreVariable) {
		this.nombreVariable= nombreVariable;
	}
	
	/**
	 * 
	 * @param tipoPrimitivoVariable
	 */
	public void setTipoPrimitivoVariable(TypeSimple tipoPrimitivoVariable) {
		this.tipoPrimitivoVariable= tipoPrimitivoVariable;
	}
	
	/**
	 * 
	 * @param numeroLinea
	 */
	public void setNumeroLinea(int numeroLinea) {
		this.numeroLinea=numeroLinea;
	}
}
