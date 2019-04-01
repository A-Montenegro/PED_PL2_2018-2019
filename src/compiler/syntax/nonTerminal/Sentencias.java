package compiler.syntax.nonTerminal;

import java.util.Stack;

public class Sentencias extends NonTerminal {
	private Stack<Sent> pilaSentencias;
	
	public Sentencias(Stack<Sent> pilaSentencias) {
		super();
		this.pilaSentencias= pilaSentencias;
	}
	
	public Sentencias(Sent sentencia) {
		super();
		this.pilaSentencias= new Stack<Sent>();
		addSentencia(sentencia);
	}
	
	public Sentencias() {
		super();
		this.pilaSentencias= new Stack<Sent>();
	}
	public Stack<Sent> getPilaSentencias(){
		return pilaSentencias;
	}
	
	public void addSentencia(Sent sentencia) {
		pilaSentencias.push(sentencia);
	}
}
