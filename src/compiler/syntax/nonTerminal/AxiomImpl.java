package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;

/**
 * Clase que implementa a la clase abstracta Axiom facilitada por el equipo docente.
 * @author Alberto Martínez Montenegro.
 */
public class AxiomImpl extends Axiom {

	private List<QuadrupleIF> code;
	private TemporalIF temporal;
	
	/**
	 * Constructor de clase.
	 * @param cuadruplas
	 */
    public AxiomImpl (List<QuadrupleIF> cuadruplas)
    {
       super();
       code=new ArrayList<QuadrupleIF>();
       List<QuadrupleIF> cuadruplas_cadena= new ArrayList<QuadrupleIF>();
       for(QuadrupleIF cuadrupla: cuadruplas) {
    	   if(cuadrupla.getOperation().equals("CADENA")) {
    		   cuadruplas_cadena.add(cuadrupla);
    	   }
    	   else {
    		   code.add(cuadrupla);
    	   }
       } 
       for(QuadrupleIF cuadrupla: cuadruplas_cadena) {
    	   code.add(cuadrupla);
       }
    }
    
	public List<QuadrupleIF> getIntermediateCode(){
		return code;
	}
	
	public void  setIntermediateCode(List<QuadrupleIF> code){
		this.code= code;
	}
	
	public TemporalIF getTemporal() {
		return temporal;
	}
	
	public void setTemporal(TemporalIF temporal) {
		this.temporal= temporal;
	}
}
