package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.intermediate.QuadrupleIF;

public class CuerpoModulo extends NonTerminal {
	private Subs subs;
	private FinalModulo finalModulo;
	
	public CuerpoModulo(Subs subs, FinalModulo finalModulo) {
		super();
		this.subs= subs;
		this.finalModulo= finalModulo;
	}
	
	public Subs getSubs() {
		return subs;
	}
	
	public FinalModulo getFinalModulo() {
		return finalModulo;
	}
	
	public List<QuadrupleIF> getCodigoIntermedioTotal(){
		if (subs.getPilaCuerpoModulos().isEmpty()) {
				return finalModulo.getSentencias().getIntermediateCode();
		}else{
			List<QuadrupleIF> cuadruplasAcumuladas= new ArrayList<QuadrupleIF>();
			cuadruplasAcumuladas.addAll(finalModulo.getSentencias().getIntermediateCode());
			for(CuerpoModulo cuerpoModulo: subs.getPilaCuerpoModulos()){
				
				cuadruplasAcumuladas.addAll(cuerpoModulo.getCodigoIntermedioTotal());
			}
			return cuadruplasAcumuladas;
		}
	}
}
