package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.CompilerContext;
import compiler.semantic.type.TypeSimple;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;

public class Expresion extends NonTerminal {
	private TypeSimple tipoExpresion;
	private ScopeManagerIF scopeManager;
	private ScopeIF scope;
	private List<QuadrupleIF> code;
	private TemporalIF temporal;
	
	public Expresion(TypeSimple tipoExpresion) {
		super();
		this.tipoExpresion=tipoExpresion;
		scopeManager= CompilerContext.getScopeManager ();
		scope= scopeManager.getCurrentScope();
	}
	
	public TypeSimple getTipoExpresion() {
		return tipoExpresion;
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
	
	public Expresion operadorSuma(Expresion segundaExpresion, int numeroLinea) {
		TypeSimple tipoSegundaExpresion= segundaExpresion.getTipoExpresion();
		if (!tipoExpresion.getName().equals("ENTERO") || !tipoSegundaExpresion.getName().equals("ENTERO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		return this;
	}
	
	public Expresion operadorDistintoQue(Expresion segundaExpresion, int numeroLinea) {
		TypeSimple tipoSegundaExpresion= segundaExpresion.getTipoExpresion();
		if (!tipoExpresion.getName().equals(tipoSegundaExpresion.getName())){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		TypeSimple tipoSalida= new TypeSimple(scope, "LOGICO");
		return new Expresion(tipoSalida);
	}
	
	public Expresion operadorMenorQue(Expresion segundaExpresion, int numeroLinea) {
		TypeSimple tipoSegundaExpresion= segundaExpresion.getTipoExpresion();
		if (!tipoExpresion.getName().equals("ENTERO") || !tipoSegundaExpresion.getName().equals("ENTERO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		TypeSimple tipoSalida= new TypeSimple(scope, "LOGICO");
		return new Expresion(tipoSalida);
	}
	
	public Expresion operadorLogicoNOT(int numeroLinea) {
		if (!tipoExpresion.getName().equals("LOGICO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		return this;
	}	
	
	public Expresion operadorLogicoAND(Expresion segundaExpresion, int numeroLinea) {
		TypeSimple tipoSegundaExpresion= segundaExpresion.getTipoExpresion();
		if (!tipoExpresion.getName().equals("LOGICO") || !tipoSegundaExpresion.getName().equals("LOGICO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		return this;
	}
	
	public Expresion operadorDividir(Expresion segundaExpresion, int numeroLinea) {
		TypeSimple tipoSegundaExpresion= segundaExpresion.getTipoExpresion();
		if (!tipoExpresion.getName().equals("ENTERO") || !tipoSegundaExpresion.getName().equals("ENTERO")){
			ErrorSemantico errorSemantico= new ErrorSemantico();
			errorSemantico.lanzarErrorPorOpeacionConTiposIncompatibles(numeroLinea);
		}
		return this;
	}
	

}
