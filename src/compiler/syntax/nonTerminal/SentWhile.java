package compiler.syntax.nonTerminal;

import java.util.Stack;

public class SentWhile extends Sent {
	private Stack<Sent> sentencias;
	
	public SentWhile(Stack<Sent> sentencias) {
		super();
		this.sentencias=sentencias;
	}
	
	public Stack<Sent> getSentencias(){
		return sentencias;
	}
}
