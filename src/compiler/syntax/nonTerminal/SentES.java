package compiler.syntax.nonTerminal;

/**
 * 
 * Clase que representa una sentencia de entrada/salida.
 * @author Alberto Martínez Montenegro
 */
public class SentES extends Sent {
	
	private String procedimiento;
	private String cadenaParaImprimir;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param procedimiento
	 */
	public SentES(String procedimiento) {
		super();
		this.procedimiento= procedimiento;
		this.cadenaParaImprimir=new String("");
	}
	
	/**
	 * 
	 * Constructor de clase.
	 * @param procedimiento
	 * @param cadenaParaImprimir
	 */
	public SentES(String procedimiento,String cadenaParaImprimir) {
		super();
		this.procedimiento= procedimiento;
		this.cadenaParaImprimir=cadenaParaImprimir;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getProcedimiento() {
		return procedimiento;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCadenaParaImprimir() {
		return cadenaParaImprimir;
	}
}
