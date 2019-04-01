package compiler.code;
import java.util.HashMap;
/**
 * Esta clase es la encargada de mantener las cadenas de texto
 */
public class Data {
	protected static HashMap<String, String> cadenas= new HashMap<>();
	protected static int i=0;
	public HashMap<String, String> getCadenas() {
		//Devolvemos la lista
		return cadenas;
	}	
	public void setCadenas(HashMap<String, String> cadenas) {
		//Asignamos la lista de cadenas
		Data.cadenas = cadenas;
	}
	public static void setCadena(String cadena,String key){
		//Añadimos una nueva cadena 
		cadenas.put(key,cadena);
	}
	public static String  getKey() {
		//Devolvemos el primer nombre de cadena que se encuentra libre y aumentamos el contador
		return ("data"+ i++);
	}	
	public static void setKey(int i) {
		//Asignamos el numero del contador
		Data.i = i;
	}
}