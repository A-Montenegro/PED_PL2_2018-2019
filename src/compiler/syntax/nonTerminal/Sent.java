package compiler.syntax.nonTerminal;

import java.util.List;

import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;

public abstract class Sent extends NonTerminal {
	private List<QuadrupleIF> code;
	private TemporalIF temporal;
	
	public Sent() {
		super();
	}
	
	public List<QuadrupleIF> getCode(){
		return code;
	}
	
	public void  setCode(List<QuadrupleIF> code){
		this.code= code;
	}
	
	public TemporalIF getTemporal() {
		return temporal;
	}
	
	public void setTemporal(TemporalIF temporal) {
		this.temporal= temporal;
	}
}
