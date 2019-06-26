package compiler.syntax.nonTerminal;

import compiler.semantic.OperacionSemantica;

/**
 * 
 * Clase que representa a una sentencia condicional.
 * @author Alberto Martínez Montenegro
 */
public class SentIf extends Sent {
	private Sentencias sentencias;
	private Sentencias sentenciasElse;
	private boolean tieneSentenciaReturn;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param sentencias
	 * @param sentenciasElse
	 */
	public SentIf(Sentencias sentencias, Sentencias sentenciasElse) {
		super();
		this.sentencias=sentencias;
		this.sentenciasElse=sentenciasElse;
		this.tieneSentenciaReturn= OperacionSemantica.comprobarExisteReturn(this.sentencias.getPilaSentencias()) && OperacionSemantica.comprobarExisteReturn(this.sentenciasElse.getPilaSentencias());
	}
	
	/**
	 * 
	 * @return
	 */
	public Sentencias getSentencias(){
		return sentencias;
	}
	
	/**
	 * 
	 * @return
	 */
	public Sentencias getSentenciasElse(){
		return sentenciasElse;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean getTieneSentenciaReturn() {
		return tieneSentenciaReturn;
	}
}
