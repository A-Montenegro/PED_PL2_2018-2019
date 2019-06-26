package compiler.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import compiler.intermediate.Temporal;
import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolProcedure;
import compiler.semantic.symbol.SymbolVariable;
import compiler.syntax.nonTerminal.Axiom;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;

/***
 * Esta clase se encarga de asignar una dirección de memoria a todas las variables, parámetros y temporales.
 * Tambíen almacena datos que son útiles en el proceso de traducción, como la memoria total usada por un procedimiento, o el nivel actual de anidamiento.
 * @author Alberto Martínez Montenegro.
 *
 */
public class DireccionamientoMemoria {
	private static LinkedHashMap<String, Integer> memoriaProcedimiento = new LinkedHashMap<String, Integer>();
	private static String nombreAmbitoGlobal;
	private static int nivelActual = 0;
	private static int siguienteDireccionGlobal= 2;
	protected static final int direccionInicioVectorDisplay= 65435;
	
	/***
	 * Al invocar este método desde el 'parser.cup' se asignan todas las direcciones.
	 * @param scopeManager
	 * @param ax
	 */
	public static void direccionar(ScopeManagerIF scopeManager, Axiom ax) {
		List<ScopeIF> scopes = scopeManager.getAllScopes();
		int siguienteDireccionVariableLocal= 0;
		int siguienteDireccionParametroLocal=0;
		for (ScopeIF scope: scopes) {
			List<SymbolIF> listaSimbolos = scope.getSymbolTable().getSymbols();
			for (SymbolIF simbolo: listaSimbolos) {
				if (simbolo instanceof SymbolVariable) {
					SymbolVariable simboloVariable= (SymbolVariable) simbolo;
					if (scope.getLevel()==0) {
						simboloVariable.setAddress(siguienteDireccionGlobal);
						siguienteDireccionGlobal= siguienteDireccionGlobal + simboloVariable.getType().getSize();
					}else{
						simboloVariable.setAddress(siguienteDireccionVariableLocal + simboloVariable.getType().getSize());
						siguienteDireccionVariableLocal= siguienteDireccionVariableLocal + simboloVariable.getType().getSize();
					}
				}else if((simbolo instanceof SymbolProcedure) || (simbolo instanceof SymbolFunction) ) {
					SymbolProcedure simboloProcedimiento= (SymbolProcedure) simbolo;
					ArrayList<SymbolParameter> parametros= simboloProcedimiento.getParametros();
					for(SymbolParameter simboloParametro: parametros) {
						simboloParametro.setAddress(siguienteDireccionParametroLocal + simboloParametro.getType().getSize());
						siguienteDireccionParametroLocal= siguienteDireccionParametroLocal + simboloParametro.getType().getSize();
					}
					siguienteDireccionParametroLocal=0;
				}
			}
			List<TemporalIF> listaTemporales= scope.getTemporalTable().getTemporals();
			for(TemporalIF temporalIf: listaTemporales) {
				Temporal temporal= (Temporal) temporalIf;
				temporal.setAddress(siguienteDireccionVariableLocal + temporal.getSize());
				siguienteDireccionVariableLocal= siguienteDireccionVariableLocal + temporal.getSize();
			}
			memoriaProcedimiento.put(scope.getName(), siguienteDireccionVariableLocal);
			if (scope.getLevel()==0) {
				nombreAmbitoGlobal= scope.getName();
				IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
				cb.addQuadruple("INIT_SUB", null, null, null);
				cb.addQuadruples(ax.getIntermediateCode ());
				ax.setIntermediateCode(cb.create());
			}
			siguienteDireccionVariableLocal= 0;
		}	
	}
	
	/***
	 * Método que devuelve la memoria total usada por un procedimiento.
	 * @param nombreProcedimiento
	 * @return
	 */
	public static int getMemoriaProcedimiento(String nombreProcedimiento){
		return memoriaProcedimiento.get(nombreProcedimiento);
	}
	
	/***
	 * Método que devuelve el nombre del ámbito global.
	 * @return
	 */
	public static String getNombreAmbitoGlobal() {
		return nombreAmbitoGlobal;
	}
	
	/***
	 * Método que establece el nivel actual de anidamiento.
	 * @param nivActual
	 */
	public static void setNivelActual(int nivActual)
	{			
		nivelActual = nivActual;
	}
	
	/***
	 * Método que devuelve el nivel actual de anidamiento.
	 * @return
	 */
	public static int getNivelActual() 
	{		
		return nivelActual;
	}
	
	/***
	 * Método que devuelve la siguiente dirección global.
	 * @return
	 */
	public static int getSiguienteDireccionGlobal() 
	{		
		return siguienteDireccionGlobal;
	}
}
