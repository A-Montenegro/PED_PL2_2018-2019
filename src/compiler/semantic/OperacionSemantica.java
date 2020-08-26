package compiler.semantic;

import java.util.ArrayList;
import java.util.Stack;
import compiler.CompilerContext;
import compiler.semantic.symbol.SymbolConstant;
import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolProcedure;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypeFunction;
import compiler.semantic.type.TypeProcedure;
import compiler.semantic.type.TypeRecord;
import compiler.semantic.type.TypeSimple;
import compiler.syntax.nonTerminal.Sent;
import compiler.syntax.nonTerminal.SentIf;
import compiler.syntax.nonTerminal.SentReturn;
import compiler.syntax.nonTerminal.Var;
import compiler.syntax.nonTerminal.VarSeqDeIds;
import compiler.syntax.nonTerminal.VarsLista;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;

/***
 * Clase que se encarga de todas las operaciones que se hacen en el análisis semántico.
 * @author Alberto Martínez Montenegro
 */
public class OperacionSemantica {
	private ErrorSemantico errorSemantico;
	private ScopeManagerIF scopeManager;
	private ScopeIF scope;
	private SymbolTableIF sTable;
	private TypeTableIF tTable;
	
	/***
	 * Constructor de la clase, toma los valores de los ámbitos y las tablas en el momento de la creación del objeto.
	 */
	public OperacionSemantica() {
		errorSemantico= new ErrorSemantico();
		scopeManager= CompilerContext.getScopeManager ();
		scope= scopeManager.getCurrentScope();
		sTable= scope.getSymbolTable();
		tTable= scope.getTypeTable();
	}
	
	/***
	 * 
	 * @return Devuelve la instancia correspondiente de la clase ErrorSemantico.
	 */
	public ErrorSemantico getErrorSemantico() {
		return errorSemantico;
	}
	
	/***
	 * Método que añade los tipos primitivos a la tabla de tipos para incializarla. Sólo se invoca al crear el ámbito global.
	 */
	public void inicializarTablaTipos() {
		TypeTableIF tTable = scope.getTypeTable();
		tTable.addType(new TypeSimple(scope,"ENTERO"));
		tTable.addType(new TypeSimple(scope,"LOGICO"));
	}
	
	/***
	 * Método que añade la lista de parámetros que se le pasa a la tabla de símbolos.
	 * @param listaParametros
	 * @param numeroLinea
	 */
	public void addSecuenciaParametrosTablaSimbolos(VarsLista listaParametros,int numeroLinea) {
		Stack<Var> pilaParametros= new Stack<Var>();
		pilaParametros.addAll(listaParametros.getVarsLista());
		Var var;
		while(!pilaParametros.isEmpty()) {
			var=pilaParametros.pop();
			addSecuenciaSimbolosTablaSimbolos(var.getVarSeqDeIds(),var.getTipo(),2,numeroLinea);
		}
	}
	
	/***
	 * Método que añade una secuencia de símbolos(secuencia representada por un objeto de la clase VarSeqDeIds), a la tabla de símbolos.
	 * @param varSeqDeIds
	 * @param tipoVarSeqDeIds
	 * @param tipoDeDeclaracion
	 * @param numeroLinea
	 */
	public void addSecuenciaSimbolosTablaSimbolos(VarSeqDeIds varSeqDeIds,TypeIF tipoVarSeqDeIds,int tipoDeDeclaracion , int numeroLinea) {
		Stack<String> pilaVariables= new Stack<String>();
		pilaVariables.addAll(varSeqDeIds.getNombresVariables());
		while(!pilaVariables.isEmpty()) {
			addSimboloTablaSimbolos(pilaVariables.pop(),tipoVarSeqDeIds,tipoDeDeclaracion, numeroLinea);
		}
	}
	
	/***
	 * Método que añade un símbolo a la tabla de símbolos.
	 * @param nombreSimbolo
	 * @param tipoSimbolo
	 * @param tipoDeDeclaracion
	 * @param numeroLinea
	 */
	public void addSimboloTablaSimbolos(String nombreSimbolo,TypeIF tipoSimbolo, int tipoDeDeclaracion, int numeroLinea) {
		SymbolIF simbolo= createSimbolo(nombreSimbolo,tipoSimbolo, tipoDeDeclaracion);
		String cadenaTipoDeDeclaracion=createCadenaTipoDeDeclaracion(tipoDeDeclaracion);
		if (!existeTipo(tipoSimbolo.getName()))  errorSemantico.lanzarErrorPorTipoInexistente(numeroLinea);
		if(scopeManager.searchType(nombreSimbolo)!=null) if(!(simbolo instanceof SymbolProcedure)) errorSemantico.lanzarErrorPorNombreSimboloEstaEnTablaTipos(numeroLinea);
		if (sTable.containsSymbol(nombreSimbolo)){
			errorSemantico.lanzarErrorPorDeclaracionSimboloRepetido(cadenaTipoDeDeclaracion,numeroLinea);
		}
		else{
			sTable.addSymbol(simbolo);
			errorSemantico.lanzarInfoSimboloDeclarado(simbolo, cadenaTipoDeDeclaracion, numeroLinea);
		}
	}
	
	/***
	 *  Método que añade un tipo a la tabla de tipos.
	 * @param tipoRegistro
	 * @param numeroLinea
	 */
	public void addTipoTablaTipos(TypeIF tipo, int numeroLinea) {
		if(scopeManager.searchSymbol(tipo.getName())!=null) if(!(tipo instanceof TypeProcedure)) errorSemantico.lanzarErrorPorNombreTipoEstaEnTablaSimbolos(numeroLinea);
		if (scopeManager.searchType(tipo.getName())!=null){
			errorSemantico.lanzarErrorPorTipoRepetido(numeroLinea);
		}
		else{
			tTable.addType(tipo);
			if(tipo instanceof TypeRecord) errorSemantico.lanzarInfoTipoDeclarado(tipo, numeroLinea);
		}
	}
	
	/***
	 * Método que crea el símbolo correspondiente según los parámetros que se le pasen.
	 * @param nombreSimbolo
	 * @param tipoSimbolo
	 * @param tipoDeDeclaracion
	 * @return Instancia de la subclase correspondiente de SymbolIF
	 */
	public SymbolIF createSimbolo(String nombreSimbolo,TypeIF tipoSimbolo, int tipoDeDeclaracion) {
		switch(tipoDeDeclaracion) {
			case 0:
				return new SymbolConstant (scope, nombreSimbolo, tipoSimbolo);
			case 1:
				return new SymbolVariable (scope, nombreSimbolo, tipoSimbolo);
			case 2:
				return new SymbolParameter (scope, nombreSimbolo, tipoSimbolo);
			case 3:
				return new SymbolProcedure (scope, nombreSimbolo, tipoSimbolo);
			default:
				return new SymbolFunction (scope, nombreSimbolo, tipoSimbolo);
		}
	}
	
	/***
	 * Método que crea una cadena de texto a partir del número entero que se le pasa como parámetro.
	 * @param tipoDeDeclaracion
	 * @return Cadena de texto correspondiente según el entero que se le pase como parámetro.
	 */
	public String createCadenaTipoDeDeclaracion(int tipoDeDeclaracion){
		switch(tipoDeDeclaracion) {
		case 0:
			return "la constante";
		case 1:
			return "la variable";
		case 2:
			return "el parámetro";
		case 3:
			return "el procedimiento";
		default:
			return "la función";
		}
	}
	
	/***
	 * Método que devuelve 'true' si el tipo cuyo nombre se ha pasado como parámetro existe.
	 * @param nombreTipo
	 * @return 'true' si el tipo cuyo nombre se ha pasado como parámetro existe.
	 */
	public boolean existeTipo(String nombreTipo) {
		if (scopeManager.searchType(nombreTipo)==null) return false;
		return true;
	}
	
	/***
	 * Método que recupera un símbolo de la tabla de símbolos a partir del nombre que se le pasa como parámetro, lanzará un error semántico fatal si no lo encuentra.
	 * @param nombreSimbolo
	 * @param numeroLinea
	 * @return Instancia de la subclase correspondiente de SymbolIF.
	 */
	public SymbolIF recuperarSimboloDesdeTablaSimbolos(String nombreSimbolo, int numeroLinea) {
		SymbolIF simbolo= scopeManager.searchSymbol(nombreSimbolo);
    	if (simbolo==null) errorSemantico.lanzarErrorPorSimboloInexistente(numeroLinea);
    	return simbolo;
	}
	
	/***
	 * Método que recupera un tipo de la tabla de tipos a partir del nombre que se le pasa como parámetro, lanzará un error semántico fatal si no lo encuentra.
	 * @param nombreTipo
	 * @param numeroLinea
	 * @return Instancia de la subclase correspondiente de TypeIF.
	 */
	public TypeIF recuperarTipoDesdeTablaTipos(String nombreTipo, int numeroLinea) {
		TypeIF tipo= scopeManager.searchType(nombreTipo);
    	if (tipo==null) errorSemantico.lanzarErrorPorTipoInexistente(numeroLinea);
    	return tipo;
	}	
	
	/***
	 * Método que recupera un tipo registro de la tabla de tipos a partir del nombre que se le pasa como parámetro, lanzará un error semántico fatal si no lo encuentra, o si lo encuentra pero no es de tipo registro.
	 * @param nombreSimbolo
	 * @param numeroLinea
	 * @return Instancia correspondiente de TypeRecord.
	 */
	public TypeRecord recuperarTipoRegistroPorNombreDelSimbolo(String nombreSimbolo, int numeroLinea) {
		SymbolIF simbolo= recuperarSimboloDesdeTablaSimbolos(nombreSimbolo, numeroLinea);
		TypeIF tipo=simbolo.getType();
		TypeRecord tipoRegistro = null;
		if (tipo instanceof TypeRecord) {
			tipoRegistro = (TypeRecord) tipo;
		}
		else {
			errorSemantico.lanzarErrorPorNoSerTipoRegistro(numeroLinea);
		}
    	return tipoRegistro;
	}	
	
	/***
	 * Método que recupera un tipo procedimiento de la tabla de tipos a partir del nombre que se le pasa como parámetro, lanzará un error semántico fatal si no lo encuentra, o si lo encuentra pero no es de tipo procedimiento.
	 * @param nombreTipoProcedimiento
	 * @param numeroLinea
	 * @return Instancia correspondiente de TypeProcedure.
	 */
	public TypeProcedure recuperarTipoProcedimientoPorNombre(String nombreTipoProcedimiento, int numeroLinea) {
		TypeIF tipo= recuperarTipoDesdeTablaTipos(nombreTipoProcedimiento, numeroLinea);
		TypeProcedure tipoProcedimiento = null;
		if (tipo instanceof TypeProcedure) {
			tipoProcedimiento = (TypeProcedure) tipo;
		}
		else {
			errorSemantico.lanzarErrorPorNoSerTipoProcedimiento(numeroLinea);
		}
    	return tipoProcedimiento;
	}
	
	/***
	 * Método que recupera un tipo función de la tabla de tipos a partir del nombre que se le pasa como parámetro, lanzará un error semántico fatal si no lo encuentra, o si lo encuentra pero no es de tipo función.
	 * @param nombreTipoFuncion
	 * @param numeroLinea
	 * @return Instancia correspondiente de TypeFunction.
	 */
	public TypeFunction recuperarTipoFuncionPorNombre(String nombreTipoFuncion, int numeroLinea) {
		TypeIF tipo= recuperarTipoDesdeTablaTipos(nombreTipoFuncion, numeroLinea);
		TypeFunction tipoFuncion = null;
		if (tipo instanceof TypeFunction) {
			tipoFuncion = (TypeFunction) tipo;
		}
		else {
			errorSemantico.lanzarErrorPorNoSerTipoFuncion(numeroLinea);
		}
    	return tipoFuncion;
	}
	
	/***
	 * Método que recupera un tipo simple de la tabla de tipos a partir del nombre que se le pasa como parámetro, lanzará un error semántico fatal si no lo encuentra, o si lo encuentra pero no es de tipo simple.
	 * @param nombreTipoSimple
	 * @param numeroLinea
	 * @return Instancia correspondiente de TypeSimple.
	 */
	public TypeSimple recuperarTipoSimplePorNombre(String nombreTipoSimple, int numeroLinea) {
		SymbolIF simbolo= recuperarSimboloDesdeTablaSimbolos(nombreTipoSimple, numeroLinea);
		TypeSimple tipoSimple = null;
		if (simbolo.getType() instanceof TypeSimple) {
			tipoSimple = (TypeSimple) simbolo.getType();
		}
		else {
			errorSemantico.lanzarErrorPorNoSerTipoSimple(numeroLinea);
		}
    	return tipoSimple;
	}
	
	/**
	 * Método que añade una serie de parametros (SymbolParameter) a una estructura que forma parte de su procedimiento (SymbolProcedure)
	 * @param parametros
	 */
	public void addSimbolosParametrosASimboloProcedimiento(VarsLista parametros) {
		ArrayList<SymbolParameter> listaParametros= new ArrayList<SymbolParameter>();
		for(Var var: parametros.getVarsLista()) {
			for(String nombreVariable: var.getVarSeqDeIds().getNombresVariables()) {
				SymbolParameter simboloParametro= (SymbolParameter) scope.getSymbolTable().getSymbol(nombreVariable);
				listaParametros.add(simboloParametro);
			}
		}
		SymbolProcedure simboloProcedimiento= (SymbolProcedure) scope.getParentScope().getSymbolTable().getSymbol(scope.getName());
		simboloProcedimiento.setParametros(listaParametros);
	}
	
	/***
	 * Método que lanza un error semántico fatal si la sentencia 'RETURN' devuelve un tipo diferente al de su función.
	 * @param tipoReturn
	 * @param numeroLinea
	 */
	public void validarSentenciaReturn(TypeSimple tipoReturn, int numeroLinea) {
		TypeFunction funcion=recuperarTipoFuncionPorNombre(scopeManager.getCurrentScope().getName(),numeroLinea);
		TypeSimple tipoFuncion= funcion.getTipoFuncion();
		String nombreTipoFuncion=tipoFuncion.getName();
		String nombreTipoReturn= tipoReturn.getName();
		if(!nombreTipoFuncion.equals(nombreTipoReturn)) errorSemantico.lanzarErrorPorSentenciaReturnIncorrecta(numeroLinea);
	}
	
	/**
	 * Método que devuelve TRUE si existe un procedimiento con el nombre que se le pasa como parámetro y FALSE en caso contrario.
	 * @param nombreProcedimiento
	 * @return
	 */
	public boolean existeProcedimientoEnAmbito(String nombreProcedimiento) {
		SymbolIF simbolo= scope.getSymbolTable().getSymbol(nombreProcedimiento);
		if (simbolo!=null && (simbolo.getType() instanceof TypeProcedure)) return true;
		return false;
	}
	
	
	/***
	 * Método estático que dada una pila de sentencias comprueba si alguna es una sentencia 'RETURN' garantizada (que no esté sujeta a condición).
	 * @param sentencias
	 * @return 'true' si hay sentencia 'RETURN' garantizada
	 */
	public static boolean comprobarExisteReturn(Stack<Sent> sentencias) {
		Stack<Sent> pilaSentencias=new Stack<Sent>();
		pilaSentencias.addAll(sentencias);
		Sent sentencia;
		while(!pilaSentencias.isEmpty()) {
			sentencia=pilaSentencias.pop();
			if(sentencia instanceof SentReturn) return true;
			if(sentencia instanceof SentIf) {
				SentIf sentif= (SentIf)sentencia;
				if(sentif.getTieneSentenciaReturn()) return true;
			}
		}
		return false;
	}
	

}
