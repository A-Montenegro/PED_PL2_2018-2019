package compiler.syntax.nonTerminal;

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
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;

/***
 * Clase que se encarga de todas las operaciones que se hacen en el an�lisis sem�ntico.
 * @author Alberto Mart�nez Montenegro
 */
public class OperacionSemantica {
	private ErrorSemantico errorSemantico;
	private ScopeManagerIF scopeManager;
	private ScopeIF scope;
	private SymbolTableIF sTable;
	private TypeTableIF tTable;
	
	/***
	 * Constructor de la clase, toma los valores de los �mbitos y las tablas en el momento de la creaci�n del objeto.
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
	 * M�todo que a�ade los tipos primitivos a la tabla de tipos para incializarla.
	 */
	public void inicializarTablaTipos() {
		TypeTableIF tTable = scope.getTypeTable();
		tTable.addType(new TypeSimple(scope,"ENTERO"));
		tTable.addType(new TypeSimple(scope,"LOGICO"));
	}
	
	/***
	 * M�todo que a�ade la lista de par�metros que se le pasa a la tabla de s�mbolos.
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
	 * M�todo que a�ade una secuencia de s�mbolos(secuencia representada por un objeto de la clase VarSeqDeIds), a la tabla de s�mbolos.
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
	 * M�todo que a�ade un s�mbolo a la tabla de s�mbolos.
	 * @param nombreSimbolo
	 * @param tipoSimbolo
	 * @param tipoDeDeclaracion
	 * @param numeroLinea
	 */
	public void addSimboloTablaSimbolos(String nombreSimbolo,TypeIF tipoSimbolo, int tipoDeDeclaracion, int numeroLinea) {
		SymbolIF simbolo= createSimbolo(nombreSimbolo,tipoSimbolo, tipoDeDeclaracion);
		String cadenaTipoDeDeclaracion=createCadenaTipoDeDeclaracion(tipoDeDeclaracion);
		if (!existeTipo(tipoSimbolo.getName()))  errorSemantico.lanzarErrorPorTipoInexistente(numeroLinea);
		if (sTable.containsSymbol(nombreSimbolo)){
			errorSemantico.lanzarErrorPorDeclaracionSimboloRepetido(cadenaTipoDeDeclaracion,numeroLinea);
		}
		else{
			sTable.addSymbol(simbolo);
			errorSemantico.lanzarInfoSimboloDeclarado(simbolo, cadenaTipoDeDeclaracion, numeroLinea);
		}
	}
	
	/***
	 *  M�todo que a�ade un tipo a la tabla de tipos.
	 * @param tipoRegistro
	 * @param numeroLinea
	 */
	public void addTipoTablaTipos(TypeIF tipo, int numeroLinea) {
		if (scopeManager.searchType(tipo.getName())!=null){
			errorSemantico.lanzarErrorPorTipoRepetido(numeroLinea);
		}
		else{
			tTable.addType(tipo);
			if(tipo instanceof TypeRecord) errorSemantico.lanzarInfoTipoDeclarado(tipo, numeroLinea);
		}
	}
	
	/***
	 * M�todo que crea el s�mbolo correspondiente seg�n los par�metros que se le pasen.
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
	 * M�todo que crea una cadena de texto a partir del n�mero entero que se le pasa como par�metro.
	 * @param tipoDeDeclaracion
	 * @return Cadena de texto correspondiente seg�n el entero que se le pase como par�metro.
	 */
	public String createCadenaTipoDeDeclaracion(int tipoDeDeclaracion){
		switch(tipoDeDeclaracion) {
		case 0:
			return "la constante";
		case 1:
			return "la variable";
		case 2:
			return "el par�metro";
		case 3:
			return "el procedimiento";
		default:
			return "la funci�n";
		}
	}
	
	/***
	 * M�todo que devuelve 'true' si el tipo cuyo nombre se ha pasado como par�metro existe.
	 * @param nombreTipo
	 * @return 'true' si el tipo cuyo nombre se ha pasado como par�metro existe.
	 */
	public boolean existeTipo(String nombreTipo) {
		if (scopeManager.searchType(nombreTipo)==null) return false;
		return true;
	}
	
	/***
	 * M�todo que recupera un s�mbolo de la tabla de s�mbolos a partir del nombre que se le pasa como par�metro, lanzar� un error sem�ntico fatal si no lo encuentra.
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
	 * M�todo que recupera un tipo de la tabla de tipos a partir del nombre que se le pasa como par�metro, lanzar� un error sem�ntico fatal si no lo encuentra.
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
	 * M�todo que recupera un tipo registro de la tabla de tipos a partir del nombre que se le pasa como par�metro, lanzar� un error sem�ntico fatal si no lo encuentra, o si lo encuentra pero no es de tipo registro.
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
	 * M�todo que recupera un tipo procedimiento de la tabla de tipos a partir del nombre que se le pasa como par�metro, lanzar� un error sem�ntico fatal si no lo encuentra, o si lo encuentra pero no es de tipo procedimiento.
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
	 * M�todo que recupera un tipo funci�n de la tabla de tipos a partir del nombre que se le pasa como par�metro, lanzar� un error sem�ntico fatal si no lo encuentra, o si lo encuentra pero no es de tipo funci�n.
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
	 * M�todo que recupera un tipo simple de la tabla de tipos a partir del nombre que se le pasa como par�metro, lanzar� un error sem�ntico fatal si no lo encuentra, o si lo encuentra pero no es de tipo simple.
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
	
	/***
	 * M�todo que lanza un error sem�ntico fatal si la sentencia 'RETURN' devuelve un tipo diferente al de su funci�n.
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
	
	/***
	 * M�todo est�tico que dada una pila de sentencias comprueba si alguna es una sentencia 'RETURN' garantizada (que no est� sujeta a condici�n).
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
				return sentif.getTieneSentenciaReturn();
			}
		}
		return false;
	}
	
}
