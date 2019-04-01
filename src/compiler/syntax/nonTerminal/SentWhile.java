package compiler.syntax.nonTerminal;


public class SentWhile extends Sent {
	private Sentencias sentencias;
	
	public SentWhile(Sentencias sentencias) {
		super();
		this.sentencias=sentencias;
	}
	
	public Sentencias getSentencias(){
		return sentencias;
	}
}
