package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.Iterator;

public class RegCampos extends NonTerminal {
	private ArrayList<RegCampo> listaRegCampos;
	
	public RegCampos(ArrayList<RegCampo> listaRegCampos) {
		super();
		this.listaRegCampos= listaRegCampos;
	}
	
	public RegCampos(RegCampo regCampo) {
		super();
		this.listaRegCampos= new ArrayList<RegCampo>();
		this.listaRegCampos.add(regCampo);
	}
	
	public ArrayList<RegCampo> getListaRegCampos(){
		return listaRegCampos;
	}
	
	public void setListaRegCampos(ArrayList<RegCampo> listaRegCampos){
		this.listaRegCampos= listaRegCampos;
	}
	
	public void addRegCampo(RegCampo regCampo) {
		comprobarNombreVariableRepetida(regCampo);
		listaRegCampos.add(regCampo);
	}
	
	public void comprobarNombreVariableRepetida (RegCampo regCampo) {
		Iterator<RegCampo> iteradorListaRegCampos= listaRegCampos.iterator();
		RegCampo siguienteRegCampo;
		while(iteradorListaRegCampos.hasNext()) {
			siguienteRegCampo= iteradorListaRegCampos.next();
			if (siguienteRegCampo.getNombreVariable().equals(regCampo.getNombreVariable())) {
				ErrorSemantico errorSemantico= new ErrorSemantico();
				errorSemantico.lanzarErrorPorRegCampoDuplicado(siguienteRegCampo.getNumeroLinea());
			}
		}
	}
}
