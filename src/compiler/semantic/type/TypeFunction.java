package compiler.semantic.type;

import compiler.syntax.nonTerminal.VarsLista;
import es.uned.lsi.compiler.semantic.ScopeIF;

/**  Clase para los tipos procedimiento y funci�n, se ha eliminado el contenido original de la clase, y se ha reescrito para adaptarla a la pr�ctica.
*@author Alberto Mart�nez Montenegro
*/
public class TypeFunction extends TypeProcedure
{   
    private TypeSimple tipoFuncion;

    public TypeFunction (ScopeIF scope, String name, VarsLista parametros,TypeSimple tipoFuncion)
    {
        super (scope, name, parametros);
        this.tipoFuncion= tipoFuncion;
    }
    
    public TypeSimple getTipoFuncion() {
    	return tipoFuncion;
    }
}
