package compiler.semantic.type;

import compiler.CompilerContext;

import es.uned.lsi.compiler.code.ExecutionEnvironmentIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for TypeSimple.
 */

// TODO: Student work
//       Include properties to characterize simple types

public class TypeSimple
    extends TypeBase
{
    /**
     * Constructor for TypeSimple.
     * @param scope The declaration scope.
     */

    public TypeSimple (ScopeIF scope,String name)
    {
        super (scope, name.toUpperCase());
    }
    /**
     * M�todo que compara el nombre de la instancia de esta clase con el nombre del tipo que se le pasa como par�metro.
     * @param tipo
     * @return 'true' si los nombres coinciden
     */
     public boolean compararNombre(TypeIF tipo) {
    	 if (this.getName().equals(tipo.getName())){
    		 return true;
    	 }
    	 else {
    		 return false;
    	 }
     }
     
    /**
     * Returns the size of the type.
     * @return the size of the type.
     */
    @Override
    public int getSize ()
    {
        ExecutionEnvironmentIF environment = CompilerContext.getExecutionEnvironment ();
        return environment.getTypeSize (this);
    }
}
