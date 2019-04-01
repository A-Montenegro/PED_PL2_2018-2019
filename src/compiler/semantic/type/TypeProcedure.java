package compiler.semantic.type;

import java.util.Stack;

import compiler.syntax.nonTerminal.ErrorSemantico;
import compiler.syntax.nonTerminal.Var;
import compiler.syntax.nonTerminal.VarsLista;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 *  Clase para los tipos procedimiento, se ha eliminado el contenido original de la clase, y se ha reescrito para adaptarla a la pr�ctica.
 *@author Alberto Mart�nez Montenegro
 */

public class TypeProcedure extends TypeBase
{   
	private VarsLista parametros;

    public TypeProcedure (ScopeIF scope, String name,VarsLista parametros)
    {
        super (scope, name);
        this.parametros= parametros;
    }
    
    public VarsLista getParametros() {
    	return parametros;
    }
    
    /***
     * M�todo que dada una pila de tipos que se le pasa como par�metro, comprueba que sean los mismos tipos que los parametros de esta instancia de 'TypeProcedure'
     * @param pilaTiposInvocacion
     * @param numeroLinea
     */
	public void comprobarCoincidenciaTiposParametros(Stack<TypeIF> pilaTiposInvocacion, int numeroLinea) {
		ErrorSemantico errorSemantico= new ErrorSemantico();
		Stack<TypeIF> pilaParametros=extraerTipos();
		Stack<TypeIF> pilaTiposPrimitivosClonada= new Stack<TypeIF>();
		pilaTiposPrimitivosClonada.addAll(pilaTiposInvocacion);
		if (pilaTiposPrimitivosClonada.size()!=pilaParametros.size()) errorSemantico.lanzarErrorPorInvocacionConParametrosIncorrectos(numeroLinea);
		while (!pilaParametros.isEmpty()) {
				String nombreParametro= pilaParametros.pop().getName();
				String nombreInvocacion= pilaTiposPrimitivosClonada.pop().getName();
				if(!nombreParametro.equals(nombreInvocacion)) errorSemantico.lanzarErrorPorInvocacionConParametrosIncorrectos(numeroLinea);
		}
	}
	
	/***
	 * M�todo que obtiene una pila de tipos a traves de la instancia de 'VarsLista' que es un campo de esta clase.
	 * @return Una pila de tipos.
	 */
	public Stack<TypeIF> extraerTipos(){
		Stack<TypeIF> pilaSalida=new Stack<TypeIF>();
		TypeIF tipo;
		for (Var var: parametros.getVarsLista()) {
			tipo= var.getTipo();
			for (@SuppressWarnings("unused") String nombreVariable: var.getVarSeqDeIds().getNombresVariables()) {
				pilaSalida.add(tipo);
			}
		}
		return pilaSalida;
	}
	
    @Override
    public int getSize ()
    {
        // TODO: Student work
        return 1;
    }
	
}
