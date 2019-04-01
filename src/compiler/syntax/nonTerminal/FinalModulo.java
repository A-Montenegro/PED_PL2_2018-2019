package compiler.syntax.nonTerminal;


public class FinalModulo extends NonTerminal {
	private Sentencias sentencias;
	private int numeroLinea;
	
	public FinalModulo(Sentencias sentencias,int numeroLinea) {
		super();
		this.sentencias= sentencias;
		this.numeroLinea= numeroLinea;
	}
	
	public Sentencias getSentencias(){
		return sentencias;
	}
	
	public int getNumeroLinea() {
		return numeroLinea;
	}
}
