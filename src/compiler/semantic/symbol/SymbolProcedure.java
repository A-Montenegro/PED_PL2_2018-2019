package compiler.semantic.symbol;

import java.util.ArrayList;

import compiler.semantic.type.TypeRecord;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * 
 * Clase facilitada por el equipo docente, se ha modificado para adaptarla a la práctica.
 * @author Alberto Martínez Montenegro
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
     * Método que establece los parámetros de un procedimiento
     * @param parametros
     */
    public void setParametros(ArrayList<SymbolParameter> parametros) {
    	this.parametros=parametros;
    }
    
    /***
     * Método que devuelve los parámetros de un procedimiento
     * @return 
     */
    public ArrayList<SymbolParameter> getParametros(){
    	return parametros;
    }
    
    /***
     *  Método que devuelve el número de parámetros de un procedimiento, considerando cada elemento de un registro como un parámetro indivivual.
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
