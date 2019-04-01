package compiler.syntax.nonTerminal;


public class SentIf extends Sent {
	private Sentencias sentencias;
	private Sentencias sentenciasElse;
	private boolean tieneSentenciaReturn;
	
	public SentIf(Sentencias sentencias, Sentencias sentenciasElse) {
		super();
		this.sentencias=sentencias;
		this.sentenciasElse=sentenciasElse;
		this.tieneSentenciaReturn= OperacionSemantica.comprobarExisteReturn(this.sentencias.getPilaSentencias()) && OperacionSemantica.comprobarExisteReturn(this.sentenciasElse.getPilaSentencias());
	}
	
	public Sentencias getSentencias(){
		return sentencias;
	}
	
	public Sentencias getSentenciasElse(){
		return sentenciasElse;
	}
	
	public boolean getTieneSentenciaReturn() {
		return tieneSentenciaReturn;
	}
}
