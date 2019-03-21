package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeSimple;

public class RegCampo extends NonTerminal {
	private String nombreVariable;
	private TypeSimple tipoPrimitivoVariable;
	private int numeroLinea;
	
	public RegCampo(String nombreVariable, TypeSimple tipoPrimitivoVariable, int numeroLinea) {
		super();
		this.nombreVariable=nombreVariable.toUpperCase();
		this.tipoPrimitivoVariable= tipoPrimitivoVariable;
		this.numeroLinea=numeroLinea;
	}
	
	public String getNombreVariable() {
		return nombreVariable;
	}
	
	public TypeSimple getTipoPrimitivoVariable() {
		return tipoPrimitivoVariable;
	}
	
	public int getNumeroLinea() {
		return numeroLinea;
	}
	
	public void setNombreVariable(String nombreVariable) {
		this.nombreVariable= nombreVariable;
	}
	
	public void setTipoPrimitivoVariable(TypeSimple tipoPrimitivoVariable) {
		this.tipoPrimitivoVariable= tipoPrimitivoVariable;
	}
	
	public void setNumeroLinea(int numeroLinea) {
		this.numeroLinea=numeroLinea;
	}
}
