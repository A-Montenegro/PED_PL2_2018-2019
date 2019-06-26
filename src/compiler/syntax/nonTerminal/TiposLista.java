package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import compiler.semantic.type.TypeRecord;

/**
 * 
 * Clase que representa a una serie de tipos.
 * @author Alberto Martínez Montenegro
 */
public class TiposLista extends NonTerminal {
	private ArrayList<TypeRecord> tiposLista;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param primerTipoRegisto
	 */
	public TiposLista(TypeRecord primerTipoRegisto) {
		super();
		tiposLista= new ArrayList<TypeRecord>();
		tiposLista.add(primerTipoRegisto);
	}
	
	/**
	 * 
	 * @param tipoRegistro
	 */
	public void addTipo(TypeRecord tipoRegistro) {
		tiposLista.add(tipoRegistro);
	}
}
