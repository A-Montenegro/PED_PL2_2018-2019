package compiler.syntax.nonTerminal;

public class SentAsign extends Sent {
	private String nombreVariable;
	private Expresion expresion;
	
	public SentAsign (String nombreVariable, Expresion expresion) {
		super();
		this.nombreVariable= nombreVariable;
		this.expresion= expresion;
	}
	
	public String getNombreVariable() {
		return nombreVariable;
	}
	
	public Expresion getExpresion() {
		return expresion;
	}
}
