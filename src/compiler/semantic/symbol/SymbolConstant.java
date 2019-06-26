package compiler.semantic.symbol;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * 
 * Clase facilitada por el equipo docente, se ha modificado para adaptarla a la práctica.
 * @author Alberto Martínez Montenegro
 */
public class SymbolConstant extends SymbolBase
{
    private int valor;

    /**
     * 
     * Constructor de clase.
     * @param scope
     * @param name
     * @param type
     */
    public SymbolConstant (ScopeIF scope, String name, TypeIF type)
    {
        super (scope, name.toUpperCase(), type);

    } 
    
    /**
     * 
     * @param valor
     */
    public void setValor(int valor) {
    	this.valor=valor;
    }
    
    /**
     * 
     * @return
     */
    public int getValor() {
    	return valor;
    }
}
