package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.Iterator;

import compiler.semantic.ErrorSemantico;

/**
 * 
 * Clase que almacena la lista de campos de un registro
 * @author Alberto Martínez Montenegro
 */
public class RegCampos extends NonTerminal {
	private ArrayList<RegCampo> listaRegCampos;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param listaRegCampos
	 */
	public RegCampos(ArrayList<RegCampo> listaRegCampos) {
		super();
		this.listaRegCampos= listaRegCampos;
	}
	
	/**
	 * 
	 * Constructor de clase.
	 * @param regCampo
	 */
	public RegCampos(RegCampo regCampo) {
		super();
		this.listaRegCampos= new ArrayList<RegCampo>();
		this.listaRegCampos.add(regCampo);
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<RegCampo> getListaRegCampos(){
		return listaRegCampos;
	}
	
	/**
	 * 
	 * @param listaRegCampos
	 */
	public void setListaRegCampos(ArrayList<RegCampo> listaRegCampos){
		this.listaRegCampos= listaRegCampos;
	}
	
	/**
	 * 
	 * @param regCampo
	 */
	public void addRegCampo(RegCampo regCampo) {
		comprobarNombreVariableRepetida(regCampo);
		listaRegCampos.add(regCampo);
	}
	
	/**
	 * Método que comprueba si la variable que representa el campo de un registro está repetida. Generará un error semántico si lo está.
	 * @param regCampo
	 */
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
