package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.CompilerContext;
import compiler.semantic.ErrorSemantico;
import compiler.semantic.type.TypeSimple;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;

/**
 * 
 * Clase que mantiene todos los atributos valores, temporales y c�digo intermedio de una expresi�n.
 * @author Alberto Mart�nez Montenegro
 */
public class Expresion extends NonTerminal {
	private TypeSimple tipoExpresion;
	private ScopeManagerIF scopeManager;
	private ScopeIF scope;
	private List<QuadrupleIF> code;
	private TemporalIF temporal;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param tipoExpresion
	 */
	public Expresion(TypeSimple tipoExpresion) {
		super();
		this.tipoExpresion=tipoExpresion;
		scopeManager= CompilerContext.getScopeManager ();
		scope= scopeManager.getCurrentScope();
	}
	
	/**
	 * 
	 * @return
	 */
	public TypeSimple getTipoExpresion() {
		return tipoExpresion;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<QuadrupleIF> getCode(){
		return code;
	}
	
	/**
	 * 
	 * @param code
	 */
	public void  setCode(List<QuadrupleIF> code){
		this.code= code;
	}
	
	/**
	 * 
	 * @return
	 */
	public TemporalIF getTemporal() {
		return temporal;
	}
	
	/**
	 * 
	 * @param temporal
	 */
	public void setTemporal(TemporalIF temporal) {
		this.temporal= temporal;
	}
	
	/**
	 * M�todo que devuelve una expresion resultado de aplicar la operaci�n suma entre la de este clase y la que se recibe como par�metro.
	 * @param segundaExpresion
	 * @param numeroLinea
	 * @return
	 */
	public Expresion operadorSuma(Expresion segundaExpresion, int numeroLinea) {
		TypeSimple tipoSegundaExpresion= segundaExpresion.getTipoExpresion();
		if (!tipoExpresion.getName().equals("ENTERO") || !tipoSegundaExpresion.getName().equals("ENTERO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		return this;
	}
	
	/**
	 * M�todo que devuelve una expresion resultado de aplicar la operaci�n distintoQue entre la de este clase y la que se recibe como par�metro.
	 * @param segundaExpresion
	 * @param numeroLinea
	 * @return
	 */
	public Expresion operadorDistintoQue(Expresion segundaExpresion, int numeroLinea) {
		TypeSimple tipoSegundaExpresion= segundaExpresion.getTipoExpresion();
		if (!tipoExpresion.getName().equals("ENTERO") || !tipoSegundaExpresion.getName().equals("ENTERO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		TypeSimple tipoSalida= new TypeSimple(scope, "LOGICO");
		return new Expresion(tipoSalida);
	}
	
	/**
	 * M�todo que devuelve una expresion resultado de aplicar la menorQue suma entre la de este clase y la que se recibe como par�metro.
	 * @param segundaExpresion
	 * @param numeroLinea
	 * @return
	 */
	public Expresion operadorMenorQue(Expresion segundaExpresion, int numeroLinea) {
		TypeSimple tipoSegundaExpresion= segundaExpresion.getTipoExpresion();
		if (!tipoExpresion.getName().equals("ENTERO") || !tipoSegundaExpresion.getName().equals("ENTERO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		TypeSimple tipoSalida= new TypeSimple(scope, "LOGICO");
		return new Expresion(tipoSalida);
	}
	
	/**
	 * M�todo que devuelve una expresion resultado de aplicar la operaci�n NOT entre la de este clase y la que se recibe como par�metro.
	 * @param numeroLinea
	 * @return
	 */
	public Expresion operadorLogicoNOT(int numeroLinea) {
		if (!tipoExpresion.getName().equals("LOGICO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		return this;
	}	
	
	/**
	 * M�todo que devuelve una expresion resultado de aplicar la operaci�n AND entre la de este clase y la que se recibe como par�metro.
	 * @param segundaExpresion
	 * @param numeroLinea
	 * @return
	 */
	public Expresion operadorLogicoAND(Expresion segundaExpresion, int numeroLinea) {
		TypeSimple tipoSegundaExpresion= segundaExpresion.getTipoExpresion();
		if (!tipoExpresion.getName().equals("LOGICO") || !tipoSegundaExpresion.getName().equals("LOGICO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		return this;
	}
	
	/**
	 * M�todo que devuelve una expresion resultado de aplicar la operaci�n dividir entre la de este clase y la que se recibe como par�metro.
	 * @param segundaExpresion
	 * @param numeroLinea
	 * @return
	 */
	public Expresion operadorDividir(Expresion segundaExpresion, int numeroLinea) {
		TypeSimple tipoSegundaExpresion= segundaExpresion.getTipoExpresion();
		if (!tipoExpresion.getName().equals("ENTERO") || !tipoSegundaExpresion.getName().equals("ENTERO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		return this;
	}
	

}
