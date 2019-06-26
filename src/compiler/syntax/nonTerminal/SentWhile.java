package compiler.syntax.nonTerminal;

/**
 * 
 * Clase que representa a una sentencia While
 * @author Alberto Martínez Montenegro
 */
public class SentWhile extends Sent {
	private Sentencias sentencias;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param sentencias
	 */
	public SentWhile(Sentencias sentencias) {
		super();
		this.sentencias=sentencias;
	}
	
	/**
	 * 
	 * @return
	 */
	public Sentencias getSentencias(){
		return sentencias;
	}
}
