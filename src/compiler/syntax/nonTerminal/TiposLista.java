package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import compiler.semantic.type.TypeRecord;

public class TiposLista extends NonTerminal {
	private ArrayList<TypeRecord> tiposLista;
	
	public TiposLista(TypeRecord primerTipoRegisto) {
		super();
		tiposLista= new ArrayList<TypeRecord>();
		tiposLista.add(primerTipoRegisto);
	}
	
	public void addTipo(TypeRecord tipoRegistro) {
		tiposLista.add(tipoRegistro);
	}
}
