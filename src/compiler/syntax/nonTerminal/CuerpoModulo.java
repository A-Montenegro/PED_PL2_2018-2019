package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.intermediate.QuadrupleIF;

/**
 * 
 * Clase que almacena las sentencias de un módulo y sus subprogramas
 * @author Alberto Martínez Montenegro
 */
public class CuerpoModulo extends NonTerminal {
	private Subs subs;
	private FinalModulo finalModulo;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param subs
	 * @param finalModulo
	 */
	public CuerpoModulo(Subs subs, FinalModulo finalModulo) {
		super();
		this.subs= subs;
		this.finalModulo= finalModulo;
	}
	
	/**
	 * Devuelve una instancia de la clase subs que representa los subprogramas de este módulo.
	 * @return
	 */
	public Subs getSubs() {
		return subs;
	}
	
	/**
	 * Devuelve la lista de sentencias de este módulo.
	 * @return
	 */
	public FinalModulo getFinalModulo() {
		return finalModulo;
	}
	
	/**
	 * Método recursivo que devuelve el código intermedio total de un módulo y de sus subprogramas.
	 * @return
	 */
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
