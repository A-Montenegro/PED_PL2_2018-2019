package compiler.semantic.symbol;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolVariable.
 */

// TODO: Student work
//       Include properties to characterize variables

public class SymbolVariable
    extends SymbolBase
{  
	private int address;
    /**
     * Constructor for SymbolVariable.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolVariable (ScopeIF scope, 
                           String name,
                           TypeIF type)
    {
        super (scope, name, type);
    } 
    
    /***
     * M�todo que establece la direcci�n de memoria de la variable
     * @param address
     */
    public void setAddress(int address) {
    	this.address= address;
    }
    
    /***
     * M�todo que devuelve la direcci�n de memoria de la variable
     * @return
     */
    public int getAddress() {
    	return address;
    }
}
