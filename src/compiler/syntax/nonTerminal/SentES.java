package compiler.syntax.nonTerminal;

public class SentES extends Sent {
	
	private String procedimiento;
	private String cadenaParaImprimir;
	
	public SentES(String procedimiento) {
		super();
		this.procedimiento= procedimiento;
		this.cadenaParaImprimir=new String("");
	}
	
	public SentES(String procedimiento,String cadenaParaImprimir) {
		super();
		this.procedimiento= procedimiento;
		this.cadenaParaImprimir=cadenaParaImprimir;
	}
	
	public String getProcedimiento() {
		return procedimiento;
	}
	
	public String getCadenaParaImprimir() {
		return cadenaParaImprimir;
	}
}
