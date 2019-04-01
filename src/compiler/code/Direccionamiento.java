package compiler.code;

import java.util.List;

import compiler.intermediate.Temporal;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolVariable;
import compiler.syntax.nonTerminal.Axiom;
import compiler.syntax.nonTerminal.AxiomImpl;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;

public class Direccionamiento {
	private List<ScopeIF> scopes;
	private Axiom ax;
	
	public Direccionamiento(List<ScopeIF> scopes, Axiom ax) {
		this.scopes= scopes;
		this.ax= ax;
	}
	
	public void direccionar() {
	for (ScopeIF scope: scopes) {
			int direccionEstatica = 1000; //Comienzo de la zona de datos est�ticos
			int direccionRA = 2 ; //Comienzo de los datos dentro de un R.A., en funci�n del R.A. de la p�gina 2
			List<SymbolIF> symbols = scope.getSymbolTable().getSymbols();
			for (SymbolIF s: symbols) {
				if (s instanceof SymbolParameter) { //Comprobar si el s�mbolo es un par�metro y lo guardamos en el R.A. del subprograma
					((SymbolParameter)s).setAddress(direccionRA); //Guardamos la direcci�n del par�metro en SymbolParameter
					direccionRA = direccionRA + s.getType().getSize(); //Actualizamos el valor de direcci�nRA para el pr�ximo s�mbolo
				} //fin if
			} //fin for
			int incremento= 0;
			direccionRA = direccionRA + incremento; //se actualiza la posici�n del R.A. despu�s de a�adir los par�metros m�s el estado de la m�quina, enlace de control, enlace de acceso, etc.
			for (SymbolIF s: symbols) {
				if (s instanceof SymbolVariable) { //Comprobar si el s�mbolo es una variable
					if (scope.getLevel () == 0) { //Si el �mbito es el global lo guardamos en la zona de datos est�ticos
						((SymbolVariable)s).setAddress(direccionEstatica); //Guardamos la direcci�n del variable en SymbolVariable
						direccionEstatica = direccionEstatica + s.getType().getSize(); //Actualizamos el valor direccionEstatica
					} else { //Si no es global la guardamos en el R.A. de la funci�n o procedimiento correspondiente
						((SymbolVariable)s).setAddress(direccionRA); //Guardamos la direcci�n del variable en SymbolVariable
						direccionRA = direccionRA + s.getType().getSize(); //Actualizamos el valor de direcci�nRA para el pr�ximo s�mbolo
					} //fin if
				} //fin if
			} //fin for
			
			//al igual que se hicimos con las variables y los par�metros lo hacemos con los temporales
			List<TemporalIF> temporals = scope.getTemporalTable ().getTemporals();
			for (TemporalIF t: temporals) {
				if (t instanceof Temporal) {
					((Temporal)t).setAddress(direccionRA); // //Guardamos la direcci�n del temporal en Temporal
					direccionRA = direccionRA + ((Temporal)t).getSize(); //Actualizamos el valor de direcci�nRA para el pr�ximo s�mbolo
				} //fin if
			} //fin for
			// Creamos el Registro de Activaci�n del �mbito MAIN
			if (scope.getLevel()==0) {
				int tamano;
				tamano = scope.getSymbolTable().getSize() + scope.getTemporalTable().getSize() + 5;
				IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
				TemporalIF temp = ((AxiomImpl)ax).getTemporal();
				cb.addQuadruple("DATA", temp, tamano); // guarda el valor del Puntero de Pila en el registro �ndice .IX y creaci�n del R.A. de MAIN
				cb.addQuadruples(ax.getIntermediateCode ());
				ax.setIntermediateCode(cb.create());
			} //fin if
		} //fin for scope
	}
}
