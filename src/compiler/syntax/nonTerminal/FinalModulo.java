package compiler.syntax.nonTerminal;

/**
 * 
 * Clase que se encarga de almacenar las sentencias de un módulo, su nombre, y el número de línea en el que termina.
 * @author Alberto Martínez Montenegro
 */
public class FinalModulo extends NonTerminal {
	private Sentencias sentencias;
	private int numeroLinea;
	private String nombreModulo;
	
	/**
	 * 
	 * Constructor de clase.
	 * @param sentencias
	 * @param nombreModulo
	 * @param numeroLinea
	 */
	public FinalModulo(Sentencias sentencias,String nombreModulo, int numeroLinea) {
		super();
		this.sentencias= sentencias;
		this.nombreModulo= nombreModulo;
		this.numeroLinea= numeroLinea;
	}
	
	/**
	 * 
	 * @return
	 */
	public Sentencias getSentencias(){
		return sentencias;
	}

	/**
	 * 
	 * @return
	 */
	public String getNombreModulo() {
		return nombreModulo;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNumeroLinea() {
		return numeroLinea;
	}
}
