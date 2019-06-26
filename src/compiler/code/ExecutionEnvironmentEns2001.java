package compiler.code; 


import java.util.Arrays; 
import java.util.List; 
import compiler.CompilerContext; 
import compiler.intermediate.Label; 
import compiler.intermediate.Temporal; 
import compiler.intermediate.Value; 
import compiler.intermediate.Variable; 
import compiler.semantic.symbol.SymbolParameter; 
import compiler.semantic.symbol.SymbolProcedure;
import compiler.semantic.type.TypeProcedure;
import compiler.semantic.type.TypeSimple; 
import es.uned.lsi.compiler.code.ExecutionEnvironmentIF; 
import es.uned.lsi.compiler.code.MemoryDescriptorIF; 
import es.uned.lsi.compiler.code.RegisterDescriptorIF; 
import es.uned.lsi.compiler.intermediate.LabelFactory; 
import es.uned.lsi.compiler.intermediate.LabelIF; 
import es.uned.lsi.compiler.intermediate.OperandIF; 
import es.uned.lsi.compiler.intermediate.QuadrupleIF; 
import es.uned.lsi.compiler.semantic.ScopeIF; 
import es.uned.lsi.compiler.semantic.ScopeManagerIF; 
import es.uned.lsi.compiler.semantic.symbol.SymbolIF; 

/**
 * Clase facilitada por el equipo docente, sólo se ha modificado el método 'translate' y se han añadido tres métodos auxiliares.
 * @author Alberto Martínez Montenegro.
 */

public class ExecutionEnvironmentEns2001 
    implements ExecutionEnvironmentIF
{    
    private final static int      MAX_ADDRESS = 65535;  
    private final static String[] REGISTERS   = {
       ".PC", ".SP", ".SR", ".IX", ".IY", ".A", 
       ".R0", ".R1", ".R2", ".R3", ".R4", 
       ".R5", ".R6", ".R7", ".R8", ".R9"
    }; 
    
    private RegisterDescriptorIF registerDescriptor; 
    private MemoryDescriptorIF   memoryDescriptor; 
    
    /**
     * Constructor for ENS2001Environment.
     */
    public ExecutionEnvironmentEns2001 ()
    {       
        super (); 
    }
    
    /**
     * Returns the size of the type within the architecture.
     * @return the size of the type within the architecture.
     */
    @Override
    public final int getTypeSize (TypeSimple type)
    {      
        return 1;   
    }
    
    /**
     * Returns the registers.
     * @return the registers.
     */
    @Override
    public final List<String> getRegisters ()
    {
        return Arrays.asList (REGISTERS); 
    }
    
    /**
     * Returns the memory size.
     * @return the memory size.
     */
    @Override
    public final int getMemorySize ()
    {
        return MAX_ADDRESS; 
    }
           
    /**
     * Returns the registerDescriptor.
     * @return Returns the registerDescriptor.
     */
    @Override
    public final RegisterDescriptorIF getRegisterDescriptor ()
    {
        return registerDescriptor; 
    }

    /**
     * Returns the memoryDescriptor.
     * @return Returns the memoryDescriptor.
     */
    @Override
    public final MemoryDescriptorIF getMemoryDescriptor ()
    {
        return memoryDescriptor; 
    }

    /**
     * Traducción de cuádruplas en código final. 
     * @param cuadruple La cuádrupla a traducir.
     * @return Conjunto de instrucciones en código final
     */
    @Override
    public final String translate (QuadrupleIF quadruple)
    {      
    	StringBuffer stringBuffer = new StringBuffer(); 
    	
    	// Cada 'if' se corresponde con una operación diferente, se abarcan todas las operaciones posibles que pueden expresar las cuádruplas.
    	// En los comentarios para ENS2001 introducidos con stringBuffer.append(";  xxxxxxxx "); puede encontrarse información detallada de las operaciones que se llevan a cabo.

    	if(quadruple.getOperation().equals("INIT_SUB")) {
    		stringBuffer.append("; ---------------------------------------\n"
    						+ 	"; Práctica Procesadores del Lenguaje II\n"
    						+ 	"; Curso 2018-2019\n"
    						+ 	"; Autor: Alberto Martínez Montenegro\n"
    						+ 	"; ---------------------------------------\n\n"
    						+	"; " + quadruple+"\n"); 
    		stringBuffer.append("; Las posiciones 2-" + (DireccionamientoMemoria.getSiguienteDireccionGlobal()-1) + " contendrán las variables globales.Por lo tanto, se hace un salto incondicional a la posición " 
    							+ DireccionamientoMemoria.getSiguienteDireccionGlobal() + ", ya que ahí es donde comienza la zona de código:\n"); 
    		stringBuffer.append("BR /" + DireccionamientoMemoria.getSiguienteDireccionGlobal() + "\nORG " + DireccionamientoMemoria.getSiguienteDireccionGlobal()+"\n"); 
    		String nombreAmbitoGlobal = DireccionamientoMemoria.getNombreAmbitoGlobal(); 
    		int memoriaUsadaProcedimientoPrincipal = DireccionamientoMemoria.getMemoriaProcedimiento(nombreAmbitoGlobal); 
    		int offset = DireccionamientoMemoria.direccionInicioVectorDisplay - (memoriaUsadaProcedimientoPrincipal + 1); 
    		stringBuffer.append("; Las direcciones del vector Display van desde la " + DireccionamientoMemoria.direccionInicioVectorDisplay + "(Display[0]), hasta la " 
    							+ "65535(Display[" + (65535 - DireccionamientoMemoria.direccionInicioVectorDisplay) + "]).\n; Ya que la memoria usada por el programa principal es de " 
    							+ memoriaUsadaProcedimientoPrincipal + ", se posiciona el puntero de pila en la dirección " + offset + ":\n"); 
    		stringBuffer.append("MOVE " + "#" + offset + "," + " .SP" + "\n"); 
    		stringBuffer.append("; Se establece el registro .IX con la dirección de inicio del vector Display para que los temporales se almacenen en las "
    							+"direcciones superiores a partir de esa dirección:\n"); 				
    		stringBuffer.append("MOVE " + "#" + DireccionamientoMemoria.direccionInicioVectorDisplay + "," + " .IX" + "\n");
    		stringBuffer.append("; Se almacena en el Display[0] el enlace de acceso al programa principal:\n"); 
    		stringBuffer.append("MOVE " + ".IX," + " [.IX]" + "\n"); 
    		return stringBuffer.toString();
    	}else{
    		stringBuffer.append("\n; "+quadruple+"\n"); 
    	}

    	if(quadruple.getOperation().equals("FIN_SUB")) {
    		stringBuffer.append("; Fin del procedimiento actual, se retorna a procedimiento llamante mediante instrucción RET:\n") ;
    		stringBuffer.append("RET\n"); 
    		return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("RETURN")) {
			stringBuffer.append("; Sentencia return de una función. El valor devuelto se encuentra en el temporal del operando resultado:\n");
			String resultado = convertirAString(quadruple.getResult()); 
			stringBuffer.append("MOVE .IX, .R1"+ "\n"); 
			stringBuffer.append("INC .R1" + "\n"); 
			stringBuffer.append("MOVE " + resultado + ", [.R1]"+ "\n"); 	
			stringBuffer.append("RET"); 
    		return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("RETF")) {
    		stringBuffer.append("; Se establece el valor de retorno de una función en el campo de retorno del RA actual.\n");
    		int numeroParametros= Integer.parseInt(quadruple.getFirstOperand().toString());
    		Temporal resultado = (Temporal)quadruple.getResult(); 
    		int desplazamiento = resultado.getAddress(); 
    		stringBuffer.append("; Primero metemos en .R1 la dirección de memoria desplazada del operando resultado respecto de .IX:\n");
    		stringBuffer.append("SUB .IX, #" + desplazamiento+ "\n"); 
    		stringBuffer.append("MOVE .A, .R1"+ "\n"); 
    		stringBuffer.append("; Después establecemos el valor de retorno en la dirección del temporal calculado en el paso anterior:\n");
    		stringBuffer.append("SUB .SP, #"+ numeroParametros + "\n"); 
    		stringBuffer.append("MOVE [.A], [.R1]"+ "\n"); 
    		return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("HALT")) {
    		stringBuffer.append("; Fin del programa, se interrumpe la ejecución.\n");
	    	stringBuffer.append("HALT"); 
	    	return stringBuffer.toString(); 
    	}	
    	
    	if(quadruple.getOperation().equals("MV")) {  	
    		stringBuffer.append("; Se guarda en el resultado el valor del primer operando.\n");
	    	String operando1 = convertirAString(quadruple.getFirstOperand()); 
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("MOVE " + operando1 + ", " + resultado); 
	    	return stringBuffer.toString(); 
    	}
 
    	if(quadruple.getOperation().equals("MVP")) {
    		stringBuffer.append("; Se guarda en el temporal resultado el valor del contenido de la dirección de memoria especificada como primer operando.\n");
    		String operando1 = convertirAString(quadruple.getFirstOperand()); 
    		String resultado = convertirAString(quadruple.getResult()); 
    		stringBuffer.append("MOVE " + operando1 + ", " + ".R1" + "\n"); 
    		stringBuffer.append("MOVE " + "[.R1]" + ", " + resultado); 
    		return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("MVA")) {
    		int desplazamientoParaElementoDeRegistro = 0;  
    		Temporal resultado = (Temporal)quadruple.getResult(); 
    		Variable operando1 = (Variable)quadruple.getFirstOperand(); 
    		if (quadruple.getSecondOperand()!=null) {
    			desplazamientoParaElementoDeRegistro = Integer.parseInt(quadruple.getSecondOperand().toString()); 
    		}
    		if(operando1.getScope().getLevel()==0)
    		{
    			stringBuffer.append("; Se hace referencia a una variable global, se usa direccionamiento directo:\n"); 
    			stringBuffer.append("MOVE #" + (operando1.getAddress() + desplazamientoParaElementoDeRegistro) + ", #-" + resultado.getAddress() + "[.IX]" + "\n"); 
    		}
    		else if(esParametro(operando1.getName(),operando1.getScope()) )
    		{		
    			if(operando1.getScope().getLevel()==DireccionamientoMemoria.getNivelActual()) {
    				stringBuffer.append("; Se hace referencia a un parámetro local, se usa un direccionamiento relativo al RA con desplazamiento de posición de registro:\n"); 
    				stringBuffer.append("ADD "+ "#" + (operando1.getAddress() + 1)  + "[.IX]" + ", #" + desplazamientoParaElementoDeRegistro + "\n"); 
    				stringBuffer.append("MOVE " + ".A" + ", " + "#-" +  + resultado.getAddress() + "[.IX]" +"\n"); 
    			}else {
    				int offset = DireccionamientoMemoria.direccionInicioVectorDisplay + operando1.getScope().getLevel(); 
    				stringBuffer.append("; Se hace referencia a un parámetro NO local, se usa un direccionamiento relativo al RA indicado por el Display[" + operando1.getScope().getLevel() + "]:\n");			
        			stringBuffer.append("MOVE #" + offset + ", .R1" + "\n");
        			stringBuffer.append("ADD [.R1], #" + (operando1.getAddress() + 1 ) + "\n"); 
        			stringBuffer.append("MOVE .A, .R1"+ "\n"); 
        			stringBuffer.append("ADD [.R1], #" + desplazamientoParaElementoDeRegistro + "\n"); 
        			stringBuffer.append("MOVE " + ".A" + ", " + "#-" +  + resultado.getAddress() + "[.IX]" +"\n");  
    			}
    		}
    		else if(operando1.getScope().getLevel()!=DireccionamientoMemoria.getNivelActual())
    		{
    			int offset = DireccionamientoMemoria.direccionInicioVectorDisplay  + operando1.getScope().getLevel() ; 
    			stringBuffer.append("; Se hace referencia a una variable NO local, se hace uso del Display[" + operando1.getScope().getLevel() + "] para encontrar la referencia dentro de su RA:\n"); 				
    			stringBuffer.append("MOVE #" + offset + ", .R1" + "\n"); 
    			stringBuffer.append("SUB [.R1], #" + (operando1.getAddress() - desplazamientoParaElementoDeRegistro) + "\n"); 
    			stringBuffer.append("MOVE .A, #-" + resultado.getAddress() + "[.IX]"+ "\n");
    		}		
    		else
    		{
    			stringBuffer.append("; Variable local. Se almacena en el temporal su referencia relativa al RA actual:\n"); 
    			stringBuffer.append("SUB .IX, #"+ (operando1.getAddress() - desplazamientoParaElementoDeRegistro)+"\n"); 
    			stringBuffer.append("MOVE .A, #-" + resultado.getAddress() + "[.IX]" + "\n"); 
    		}
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("STP")) {
    		stringBuffer.append("; Se guarda en la dirección de memoria del temporal resultado el valor del temporal especificado como primer operando.\n");
    		Temporal operando1 = (Temporal)quadruple.getFirstOperand(); 
    		Temporal resultado = (Temporal)quadruple.getResult(); 
    		stringBuffer.append("MOVE #-" + resultado.getAddress() + "[.IX]" + ", .R1" + "\n"); 
    		stringBuffer.append("MOVE #-" + operando1.getAddress() + "[.IX], [.R1]" + "\n"); 
	    	return stringBuffer.toString(); 
    	}
	
    	if(quadruple.getOperation().equals("ADD")) {
    		stringBuffer.append("; Se suman los dos operandos y se guarda el resultado en el temporal del resultado.\n");
	    	String operando1 = convertirAString(quadruple.getFirstOperand()); 
	    	String operando2 = convertirAString(quadruple.getSecondOperand()); 
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("ADD " + operando1 + ", " + operando2+ "\n"); 
	    	stringBuffer.append("MOVE " + ".A " + ", " + resultado); 
	    	return stringBuffer.toString(); 
    	}
    	    	
    	if(quadruple.getOperation().equals("AND")) {
    		stringBuffer.append("; Se hace la operación 'AND' sobre los dos operandos y se guarda el resultado en el temporal del resultado.\n");
	    	String operando1 = convertirAString(quadruple.getFirstOperand()); 
	    	String operando2 = convertirAString(quadruple.getSecondOperand()); 
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("AND " + operando1 + ", " + operando2+ "\n"); 
	    	stringBuffer.append("MOVE " + ".A " + ", " + resultado); 
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("EQ")) {
    		stringBuffer.append("; Se hace la operación 'EQ' sobre los dos operandos y se guarda el resultado en el temporal del resultado.\n");
    		LabelFactory lF = new LabelFactory(); 
    		LabelIF l1 = lF.create(); 
			LabelIF l2 = lF.create(); 
	    	String operando1 = convertirAString(quadruple.getFirstOperand()); 
	    	String operando2 = convertirAString(quadruple.getSecondOperand()); 
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("CMP " + operando1 + ", " + operando2+ "\n"); 
	    	stringBuffer.append("BNZ /" + l2 + "\n"); 
	    	stringBuffer.append("MOVE " + "#1 " + ", " + resultado + "\n"); 
	    	stringBuffer.append("BR /" + l1 + "\n"); 
	    	stringBuffer.append(l2 + ":\n"); 
	    	stringBuffer.append("MOVE " + "#0 " + ", " + resultado + "\n"); 
	    	stringBuffer.append(l1 + ":"); 
	    	return stringBuffer.toString(); 
    	}	
    	
    	if(quadruple.getOperation().equals("LS")) {
    		stringBuffer.append("; Se hace la operación 'LS' sobre los dos operandos y se guarda el resultado en el temporal del resultado.\n");
    		LabelFactory lF = new LabelFactory(); 
    		LabelIF l1 = lF.create(); 
			LabelIF l2 = lF.create(); 
	    	String operando1 = convertirAString(quadruple.getFirstOperand()); 
	    	String operando2 = convertirAString(quadruple.getSecondOperand()); 
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("CMP " + operando1 + ", " + operando2+ "\n"); 
	    	stringBuffer.append("BN /" + l2 + "\n"); 
	    	stringBuffer.append("MOVE " + "#0 " + ", " + resultado + "\n"); 
	    	stringBuffer.append("BR /" + l1 + "\n"); 
	    	stringBuffer.append(l2 + ":\n"); 
	    	stringBuffer.append("MOVE " + "#1 " + ", " + resultado + "\n"); 
	    	stringBuffer.append(l1 + ":"); 
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("NOT")) {
    		stringBuffer.append("; Se hace la operación 'NOT' sobre el primer operando y se guarda el resultado en el temporal del resultado.\n");
    		LabelFactory lF = new LabelFactory(); 
    		LabelIF l1 = lF.create(); 
			LabelIF l2 = lF.create(); 
	    	String operando1 = convertirAString(quadruple.getFirstOperand()); 
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("CMP " + operando1 + ", " + "#1" + "\n"); 
	    	stringBuffer.append("BZ /" + l2 + "\n"); 
	    	stringBuffer.append("MOVE " + "#1 " + ", " + resultado + "\n"); 
	    	stringBuffer.append("BR /" + l1 + "\n"); 
	    	stringBuffer.append(l2 + ":\n"); 
	    	stringBuffer.append("MOVE " + "#0 " + ", " + resultado + "\n"); 
	    	stringBuffer.append(l1 + ":"); 
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("DIV")) {
    		stringBuffer.append("; Se hace la operación 'DIV' sobre los dos operandos y se guarda el resultado en el temporal del resultado.\n");
	    	String operando1 = convertirAString(quadruple.getFirstOperand()); 
	    	String operando2 = convertirAString(quadruple.getSecondOperand()); 
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("DIV " + operando1 + ", " + operando2+ "\n"); 
	    	stringBuffer.append("MOVE " + ".A " + ", " + resultado); 
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("BRF")) {
    		stringBuffer.append("; Salto a la etiqueta del primer operando si el resultado es FALSE\n");
	    	String operando1 = convertirAString(quadruple.getFirstOperand()); 
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("CMP " + resultado + ", " + "#1"+ "\n"); 
	    	stringBuffer.append("BNZ /" + operando1); 
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("BR")) {
    		stringBuffer.append("; Salto incondicional hacia la etiqueta del resultado.\n");
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("BR /" + resultado); 
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("INL")) {
    		stringBuffer.append("; Se imprime la etiqueta del procedimiento:\n");
	    	String resultado = convertirAString(quadruple.getResult()); 
			if(quadruple.getFirstOperand() != null) //Si se especifica el primer operando, se está indicando que este mismo contiene el valor del nivel actual.
			{
				Value value = (Value)quadruple.getFirstOperand(); 
				Integer valorNivel = (Integer)value.getValue(); 	
				DireccionamientoMemoria.setNivelActual(valorNivel); 
				stringBuffer.append(resultado + quadruple.getSecondOperand().toString() + ":\nNOP"); 
			}
			else {
				stringBuffer.append(resultado + ":\nNOP"); 
			}
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("PARAM")) {
    		stringBuffer.append("; Almacena el contenido del resultado en la dirección apuntada por el puntero de pila:\n");
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("PUSH " + resultado); 
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("CALL")) {
    		stringBuffer.append("; Invocación de procedimiento, se siguen los pasos necesarios para preparar el RA." + "\n"); 
    		String resultado = convertirAString(quadruple.getResult()); 
    		ScopeManagerIF scopeManager= CompilerContext.getScopeManager (); 
    		SymbolProcedure simboloProcedimiento = getProcedimientoPorEtiqueta(scopeManager, (LabelIF)quadruple.getResult()); 
    		int nivelProcedimientoLlamado = simboloProcedimiento.getScope().getLevel() + 1; 
    		int nivelProcedimientoLlamante = DireccionamientoMemoria.getNivelActual(); 
			stringBuffer.append("; Paso 1- Se reserva espacio para el campo VALOR DE RETORNO:" + "\n"); 
			stringBuffer.append("PUSH #0" + "\n"); 
			stringBuffer.append("; Paso 2- Se almacena el puntero de marco del llamador en el campo FRAME POINTER:" + "\n"); 
			stringBuffer.append("PUSH .IX" + "\n"); 
			stringBuffer.append("; Paso 3-  Se actualiza el registro .IX con la dirección del nuevo RA:" + "\n"); 
			stringBuffer.append("MOVE .SP, .IX" + "\n"); 
			stringBuffer.append("; Paso 4- Se incrementa el valor de .IX en una unidad:" + "\n"); 
			stringBuffer.append("INC .IX" + "\n"); 
			stringBuffer.append("; Paso 5- Se actualiza el registro SP, posicionándolo en la zona superior a la zona ocupada por la memoria del nuevo procedimiento:" + "\n"); 
			int offset= DireccionamientoMemoria.getMemoriaProcedimiento(quadruple.getFirstOperand().toString()); 
			stringBuffer.append("SUB .SP, #" + offset + "\n"); 
			stringBuffer.append("MOVE .A, .SP" + "\n"); 
			stringBuffer.append("; Paso 6- Se actualiza el Display[" + nivelProcedimientoLlamado + "] dándole el valor del registro .IX:" + "\n"); 
			offset = DireccionamientoMemoria.direccionInicioVectorDisplay +  nivelProcedimientoLlamado;
			stringBuffer.append("MOVE .IX, /" + offset + "\n"); 
			stringBuffer.append("; Registro de Activación creado. Se ejecuta la llamada al procedimiento:" + "\n"); 
			stringBuffer.append("CALL /" + resultado + quadruple.getFirstOperand().toString() + "\n"); 
			stringBuffer.append("; Fin de la invocación del procedimiento. En los dos últimos pasos se destruye el RA creado previamente:" + "\n"); 
			stringBuffer.append("; Paso 7-  Se actualiza el Display[" + nivelProcedimientoLlamado + "] dándole el valor de la contenido de la dirección de memoria a la que apunta .IX:" + "\n"); 
			stringBuffer.append("MOVE [.IX], /" + offset + "\n"); 
			DireccionamientoMemoria.setNivelActual(nivelProcedimientoLlamante); 
			stringBuffer.append("; Paso 8- Se actualiza el registro SP:" + "\n"); 
			stringBuffer.append("MOVE .IX, .SP" + "\n"); 
			offset = 1 + simboloProcedimiento.getSizeParametros(); 
			stringBuffer.append("ADD .SP, #" + offset + "\n"); 
			stringBuffer.append("MOVE .A, .SP" + "\n"); 
			stringBuffer.append("; Paso 9- Se actaliza el registro IX con la dirección del RA del llamador:" + "\n"); 
			stringBuffer.append("MOVE [.IX], .IX" + "\n");
	    	return stringBuffer.toString();
    	}
    	
    	if(quadruple.getOperation().equals("PRINTI")) {
    		stringBuffer.append("; Imprime un número entero y a continuación un salto de línea:\n");
	    	String resultado = convertirAString(quadruple.getResult()); 
	    	stringBuffer.append("WRINT " + resultado + "\n"); 
	    	stringBuffer.append("WRCHAR #10"); 
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("PRINTC")) {
    		stringBuffer.append("; Imprime una cadena de caracteres y a continuación un salto de línea:\n");
    		String operando1 = convertirAString(quadruple.getResult()); 
    		stringBuffer.append("WRSTR /" + operando1 + "\n"); 
    		stringBuffer.append("WRCHAR #10"); 
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("PRINTLN")) {
    		stringBuffer.append("; Imprime un salto de línea:\n");
    		stringBuffer.append("WRCHAR #10"); 
	    	return stringBuffer.toString(); 
    	}
    	
    	if(quadruple.getOperation().equals("CADENA")) {
    		stringBuffer.append("; Etiqueta para cadena de texto:\n");
	    	String operando1 = convertirAString(quadruple.getFirstOperand()); 
	    	stringBuffer.append(operando1 + " : DATA " + quadruple.getResult() + " "); 
	    	return stringBuffer.toString(); 
    	}
    	
    	return "; "+quadruple.toString() + "\n; ERROR: CUÁDRUPLA DESCONOCIDA";  	
    }
    
    /***
     * Método que devuelve una determinada String en función del tipo y del nivel del operando que se le pasa como parámetro.
     * @param operando
     * @return
     */
	public String convertirAString(OperandIF operando) {
		if(operando instanceof Variable) {
			Variable variable = (Variable)operando; 
			if(variable.getScope().getLevel()==0) return "#" + variable.getAddress(); 
			else return "#" + (variable.getAddress() + 1) + "[.IX]"; 
		}
		if(operando instanceof Value){
			return "#" + ((Value)operando).getValue(); 
		}
		if(operando instanceof Temporal){
			return "#-" + ((Temporal)operando).getAddress() + "[.IX]"; 
		}
		if(operando instanceof Label){
			return ((Label)operando).getName(); 
		}
		return ""; 
	}
	
	/***
	 * Método que devuelve TRUE si el nombre que se le pasa como parámetro es un parámetro del scope que también se le pasa como parámetro.
	 * @param nombre
	 * @param sc
	 * @return
	 */
	public boolean esParametro(String nombre, ScopeIF sc) {
		for (SymbolIF simbolo: sc.getSymbolTable().getSymbols()) {
			if (simbolo instanceof SymbolParameter) if(nombre.equals(simbolo.getName())) return true; 
		}
		return false; 
	}
	
	/***
	 * Método que devuelve una instancia de la clase SymbolProcedure buscándola por nombre en todos los scopes.
	 * @param scopeManager
	 * @param nombreProcedimiento
	 * @return
	 */
	public SymbolProcedure getProcedimientoPorEtiqueta(ScopeManagerIF scopeManager, LabelIF label) {
			List<ScopeIF> scopes= scopeManager.getAllScopes(); 
			for(ScopeIF scope: scopes) {
				for(SymbolIF simboloProcecimiento: scope.getSymbolTable().getSymbols()) {
					if(simboloProcecimiento instanceof SymbolProcedure) {
						TypeProcedure tipoProcedimiento= (TypeProcedure) simboloProcecimiento.getType();
						if (label == tipoProcedimiento.getLabel()) return (SymbolProcedure)simboloProcecimiento;
					}
				}
			}
			System.exit(0); 
			return null; 
	}
	
}
