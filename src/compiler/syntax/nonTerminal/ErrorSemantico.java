package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolProcedure;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/***
 * Clase que se encarga de lanzar todos los mensaje de aviso y error relacionados con el análisis semántico.
 * @author Alberto Martínez Montenegro
 *
 */
public class ErrorSemantico {
	private SemanticErrorManager semanticErrorManager;
	
	/***
	 * Constructor de clase.
	 */
	public ErrorSemantico() {
		semanticErrorManager = CompilerContext.getSemanticErrorManager ();
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param cadenaTipoDeDeclaracion
	 * @param numeroLinea
	 */
	public void lanzarErrorPorDeclaracionSimboloRepetido (String cadenaTipoDeDeclaracion, int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado la declaración de una "+ cadenaTipoDeDeclaracion +" con un identificador repetido en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param simboloe
	 * @param cadenaTipoDeDeclaracion
	 * @param numeroLinea
	 */
	public void lanzarInfoSimboloDeclarado (SymbolIF simbolo,String cadenaTipoDeDeclaracion, int numeroLinea) {
		if(simbolo instanceof SymbolProcedure || simbolo instanceof SymbolFunction) {
			semanticErrorManager.semanticInfo("Se ha declarado "+ cadenaTipoDeDeclaracion +" '" + simbolo.getName() + "' en la línea " + numeroLinea);
		}else {
			semanticErrorManager.semanticInfo("Se ha declarado "+ cadenaTipoDeDeclaracion +" '" + simbolo.getName() + "' de tipo " + simbolo.getType().getName() + " en la línea " + numeroLinea);
		}
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param nombreAmbito
	 * @param numeroLinea
	 */
	public void lanzarInfoAmbitoGlobal (String nombreAmbito, int numeroLinea) {
		semanticErrorManager.semanticInfo("Se ha abierto el ámbito para el módulo global con nombre '"+ nombreAmbito +"' en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param nombreAmbito
	 * @param numeroLinea
	 */
	public void lanzarInfoNuevoAmbitoAbierto (String nombreAmbito, int numeroLinea) {
		semanticErrorManager.semanticInfo("Se ha abierto un nuevo ámbito para el procedimiento '"+ nombreAmbito +"' en la línea " + numeroLinea);
	}

	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param nombreAmbito
	 * @param numeroLinea
	 */
	public void lanzarInfoCierreAmbito (String nombreAmbito, int numeroLinea) {
		semanticErrorManager.semanticInfo("Se ha cerrado el ámbito para '"+ nombreAmbito +"' en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorTipoRepetido (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado la declaración de un tipo con un identificador repetido en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorInvocacionConParametrosIncorrectos (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado un intento de invocación de procedimiento con parámetros incorrectos en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param tipoRegistro
	 * @param numeroLinea
	 */
	public void lanzarInfoTipoDeclarado(TypeIF tipoRegistro, int numeroLinea) {
		semanticErrorManager.semanticInfo("Se ha definido un nuevo tipo registro con identificador '"+ tipoRegistro.getName() +"' en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorSimboloInexistente (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado el uso de un símbolo que no ha sido declarado en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorTipoInexistente (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado el uso de un tipo o procedimiento que no ha sido declarado en la línea " + numeroLinea);
	}

	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorExpresionConTipoNoPermitido (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado el uso de un tipo no permitido en una expresión en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorRegCampoDuplicado (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado la declaración de una variable con un identificador repetido dentro de una declaración de tipo en la línea " + numeroLinea);
	}

	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorNoSerTipoProcedimiento (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado una sentencia de llamada a procedimiento con el identificador de una función en la línea " + numeroLinea);
	}

	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorNoSerTipoFuncion (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado un intento de invocación de función con un identificador que no ha sido declarado, o no es una función en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param nombreFuncion
	 */
	public void lanzarErrorPorNoExistirReturn (String nombreFuncion) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado que la función '"+ nombreFuncion+"' o bien no tiene sentencia 'RETURN', o bien sus sentencias condicionales y bucles no la garantizan.");
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorNoSerTipoRegistro (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado la declaración un intento de acceso a registro en una variable que no existe o no es de tipo registro en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorNoSerTipoSimple (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado la declaración un intento de asignación directa a una variable de tipo registro en lugar de especificar uno de sus campos en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorNoExistirRegCampo (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado el intento de acceso a un elemento que no existe en una variable de tipo registro en la línea " + numeroLinea);
	}

	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorOpeacionConTiposIncompatibles (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado el intento de realizar una operación no permitida entre expresiones con tipos incompatibles en la línea " + numeroLinea);
	}

	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorSentenciaAsignacionIncorrecta (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado el intento de asignarle a una variable una expresión de un tipo incompatible en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorSentenciaAsignacionAConstante (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado el intento de asignarle un valor a una constante en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorSentenciaWhileIncorrecta (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado el intento establecer una expresión que no es de tipo lógico como condición de un bucle 'While' en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorSentenciaIfIncorrecta (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado el intento establecer una expresión que no es de tipo lógico como condición de una sentencia condicional 'if' en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorSentenciaWriteIntIncorrecta (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado el intento pasar una expresión que no es de tipo entero en una sentencia de E/S de tipo 'WRITEINT' en la línea " + numeroLinea);
	}
	
	/***
	 * 
	 * Método que realiza la función descrita en su nombre
	 * @param numeroLinea
	 */
	public void lanzarErrorPorSentenciaReturnIncorrecta (int numeroLinea) {
		semanticErrorManager.semanticFatalError ("Error: Se ha detectado una sentencia 'RETURN' que intenta devolver un tipo diferente al que requiere la función en la línea " + numeroLinea);
	}
	
}
