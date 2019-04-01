package compiler.syntax.nonTerminal;

import java.util.List;

import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;

public class AxiomImpl extends Axiom {

	private List<QuadrupleIF> code;
	private TemporalIF temporal;
	
    /**
     * Constructor de clase.
     */
    public AxiomImpl ()
    {
        super (); 
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
