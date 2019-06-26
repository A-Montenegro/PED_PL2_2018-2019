package compiler.syntax.nonTerminal;

/**
 * 
 * Clase que representa una sentencia de asignación.
 * @author Alberto Martínez Montenegro
 */
public class SentAsign extends Sent {
	private String nombreVariable;
	private Expresion expresion;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param nombreVariable
	 * @param expresion
	 */
	public SentAsign (String nombreVariable, Expresion expresion) {
		super();
		this.nombreVariable= nombreVariable;
		this.expresion= expresion;
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
	public Expresion getExpresion() {
		return expresion;
	}
}
