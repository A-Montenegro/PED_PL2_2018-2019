package compiler.semantic.type;

import java.util.Iterator;

import compiler.syntax.nonTerminal.RegCampo;
import compiler.syntax.nonTerminal.RegCampos;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;

/**
 * Clase para los tipos registro, se ha eliminado el contenido original de la clase, y se ha reescrito para adaptarla a la práctica.
 * @author Alberto Martínez Montenegro
 */
public class TypeRecord
    extends TypeBase
{   
	private RegCampos registroCampos;

    /**
     * Constructor de la clase.
     * @param scope El scope del ámbito.
     * @param name El nombre del tipo.
     */
    public TypeRecord (ScopeIF scope, String name, RegCampos registroCampos)
    {   
        super (scope, name.toUpperCase());
        this.registroCampos= registroCampos;
    }
    
    public RegCampos getRegistroCampos() {
    	return registroCampos;
    }
    
    /***
     * Método que recupera una instancia de la clase RegCampo a partir del nombre.
     * @param nombreVariable
     * @return Una instancia de la clase RegCampo.
     */
    public RegCampo recuperarRegCampoPorNombre(String nombreVariable) {
    	Iterator<RegCampo> iteradorRegCampos=registroCampos.getListaRegCampos().iterator();
    	RegCampo regCampo;
    	while (iteradorRegCampos.hasNext()) {
    		regCampo=iteradorRegCampos.next();
    		if (regCampo.getNombreVariable().equals(nombreVariable)) return regCampo;
    	}
    	return null;
    }
    
    /**
     * Devuelve el tamaño del tipo.
     * @return el tamaño del tipo.
     */
    @Override
    public int getSize ()
    {
        return 1;
    }
    
}
