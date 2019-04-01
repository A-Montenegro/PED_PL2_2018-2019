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
			int direccionEstatica = 1000; //Comienzo de la zona de datos estáticos
			int direccionRA = 2 ; //Comienzo de los datos dentro de un R.A., en función del R.A. de la página 2
			List<SymbolIF> symbols = scope.getSymbolTable().getSymbols();
			for (SymbolIF s: symbols) {
				if (s instanceof SymbolParameter) { //Comprobar si el símbolo es un parámetro y lo guardamos en el R.A. del subprograma
					((SymbolParameter)s).setAddress(direccionRA); //Guardamos la dirección del parámetro en SymbolParameter
					direccionRA = direccionRA + s.getType().getSize(); //Actualizamos el valor de direcciónRA para el próximo símbolo
				} //fin if
			} //fin for
			int incremento= 0;
			direccionRA = direccionRA + incremento; //se actualiza la posición del R.A. después de añadir los parámetros más el estado de la máquina, enlace de control, enlace de acceso, etc.
			for (SymbolIF s: symbols) {
				if (s instanceof SymbolVariable) { //Comprobar si el símbolo es una variable
					if (scope.getLevel () == 0) { //Si el ámbito es el global lo guardamos en la zona de datos estáticos
						((SymbolVariable)s).setAddress(direccionEstatica); //Guardamos la dirección del variable en SymbolVariable
						direccionEstatica = direccionEstatica + s.getType().getSize(); //Actualizamos el valor direccionEstatica
					} else { //Si no es global la guardamos en el R.A. de la función o procedimiento correspondiente
						((SymbolVariable)s).setAddress(direccionRA); //Guardamos la dirección del variable en SymbolVariable
						direccionRA = direccionRA + s.getType().getSize(); //Actualizamos el valor de direcciónRA para el próximo símbolo
					} //fin if
				} //fin if
			} //fin for
			
			//al igual que se hicimos con las variables y los parámetros lo hacemos con los temporales
			List<TemporalIF> temporals = scope.getTemporalTable ().getTemporals();
			for (TemporalIF t: temporals) {
				if (t instanceof Temporal) {
					((Temporal)t).setAddress(direccionRA); // //Guardamos la dirección del temporal en Temporal
					direccionRA = direccionRA + ((Temporal)t).getSize(); //Actualizamos el valor de direcciónRA para el próximo símbolo
				} //fin if
			} //fin for
			// Creamos el Registro de Activación del ámbito MAIN
			if (scope.getLevel()==0) {
				int tamano;
				tamano = scope.getSymbolTable().getSize() + scope.getTemporalTable().getSize() + 5;
				IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
				TemporalIF temp = ((AxiomImpl)ax).getTemporal();
				cb.addQuadruple("DATA", temp, tamano); // guarda el valor del Puntero de Pila en el registro índice .IX y creación del R.A. de MAIN
				cb.addQuadruples(ax.getIntermediateCode ());
				ax.setIntermediateCode(cb.create());
			} //fin if
		} //fin for scope
	}
}
