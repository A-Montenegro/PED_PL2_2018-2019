package compiler.semantic.symbol;

import java.util.ArrayList;

import compiler.semantic.type.TypeRecord;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * 
 * Clase facilitada por el equipo docente, se ha modificado para adaptarla a la pr�ctica.
 * @author Alberto Mart�nez Montenegro
 */
public class SymbolProcedure extends SymbolBase
{
   private ArrayList<SymbolParameter> parametros;
   
    /**
     * Constructor for SymbolProcedure.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolProcedure (ScopeIF scope, String name, TypeIF type)
    {
        super (scope, name.toUpperCase(), type);
    } 
    
    /***
     * M�todo que establece los par�metros de un procedimiento
     * @param parametros
     */
    public void setParametros(ArrayList<SymbolParameter> parametros) {
    	this.parametros=parametros;
    }
    
    /***
     * M�todo que devuelve los par�metros de un procedimiento
     * @return 
     */
    public ArrayList<SymbolParameter> getParametros(){
    	return parametros;
    }
    
    /***
     *  M�todo que devuelve el n�mero de par�metros de un procedimiento, considerando cada elemento de un registro como un par�metro indivivual.
     * @return
     */
    public int getSizeParametros(){
    	int size=0;
    	for(SymbolParameter simboloParametro: parametros) {
    		if(simboloParametro.getType() instanceof TypeRecord) {
    			size=size+simboloParametro.getType().getSize();
    		}else {
    			size=size+1;
    		}
    	}
    	return size;
    }
}
