package compiler.syntax.nonTerminal;

import java.util.Stack;

public class SentIf extends Sent {
	private Stack<Sent> sentencias;
	private Stack<Sent> sentenciasElse;
	private boolean tieneSentenciaReturn;
	
	public SentIf(Stack<Sent> sentencias, Stack<Sent> sentenciasElse) {
		super();
		this.sentencias=sentencias;
		this.sentenciasElse=sentenciasElse;
		this.tieneSentenciaReturn= OperacionSemantica.comprobarExisteReturn(this.sentencias) && OperacionSemantica.comprobarExisteReturn(this.sentenciasElse);
	}
	
	public Stack<Sent> getSentencias(){
		return sentencias;
	}
	
	public Stack<Sent> getSentenciasElse(){
		return sentenciasElse;
	}
	
	public boolean getTieneSentenciaReturn() {
		return tieneSentenciaReturn;
	}
}
