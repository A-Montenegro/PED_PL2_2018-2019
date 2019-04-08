package compiler.code;

import java.util.Arrays;
import java.util.List;

import compiler.intermediate.Label;
import compiler.intermediate.Temporal;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.type.TypeSimple;

import es.uned.lsi.compiler.code.ExecutionEnvironmentIF;
import es.uned.lsi.compiler.code.MemoryDescriptorIF;
import es.uned.lsi.compiler.code.RegisterDescriptorIF;
import es.uned.lsi.compiler.intermediate.LabelFactory;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.intermediate.OperandIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;

/**
 * Class for the ENS2001 Execution environment.
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
     * Translate a quadruple into a set of final code instructions. 
     * @param cuadruple The quadruple to be translated.
     * @return a quadruple into a set of final code instructions. 
     */
    @Override
    public final String translate (QuadrupleIF quadruple)
    {      
        //TODO: Student work
    	
    	/*
    	 * //resta dos operandos y guarda el valor en el registro acumulador que luego mueve al resultado.
    	if(quadruple.getOperation().equals("OPERACION QUADRUPLA")) {
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	String o2 = operacion(quadruple.getSecondOperand());
	    	String r = operacion(quadruple.getResult());
	    	b.append("instruccion ENS2001 " + o1 + ", " + o2 + "\n");
	    	b.append("instruccion ENS2001 " + ".A " + ", " + r);
	    	return b.toString();
    	}


    	//Quadruple - [VARGLOBAL z, 67, null],
    	// inicializa una variable con su valor en su dirección de memoria
    	if(quadruple.getOperation().equals("VARGLOBAL")) {
    		StringBuffer b = new StringBuffer();
			String r = operacion(quadruple.getResult());
    		String o1 = operacion(quadruple.getFirstOperand());
    		b.append("MOVE " + o1 + ", " + r);
    		return b.toString();
    	}
    	//MOVE #67, /1000
    	
    	
    	//Quadruple - [DATA null, null, null],
    	//Preparación del R.A. subprograma main
    	if(quadruple.getOperation().equals("DATA")) {
	    	StringBuffer b = new StringBuffer();
	    	b.append("MOVE " + ".SP" + ", " + ".IX" + "\n");
	    	b.append("PUSH " + "#-1" + "\n");
	    	b.append("PUSH " + ".IY" + "\n");
	    	b.append("PUSH " + ".SR");
	    	return b.toString();
    	}
    	//MOVE .SP, .IX
    	//PUSH #-1 // Introduce en la pila el valor de retorno
    	//PUSH .IY // Introduce en la pila el enlace de control
    	//PUSH .SR // Introduce en la pila el estado de la máquina
    	
    	
    	//Quadruple - [DATAPUNTERO null, 8, null],
    	//Posiciona el SP según el tamaño del R.A. del subprograma
    	if(quadruple.getOperation().equals("DATAPUNTERO")) {
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	b.append("SUB " + ".IX" + ", " + o1 + "\n");
	    	b.append("MOVE " + ".A" + ", " + ".SP");
	    	return b.toString();
    	}
    	//SUB .IX, #8
    	//MOVE .A, .SP
    	
    	
    	//Quadruple - [PRINTC T_0, L_2, null],
    	//Imprimir una cadena y dar un salto de línea
    	if(quadruple.getOperation().equals("PRINTC")) {
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	b.append("WRSTR /" + o1 + "\n");
	    	b.append("WRCHAR #10");
	    	return b.toString();
    	}
    	//WRSTR /L_2
    	//WRCHAR #10
    	
    	
    	
    	//Quadruple - [DATA_SUB null, null, null],
    	//preparación del R.A. del subprograma,
    	if(quadruple.getOperation().equals("DATA_SUB")) {
	    	StringBuffer b = new StringBuffer();
	    	b.append("MOVE " + ".SP" + ", " + ".R0" + "\n");
	    	b.append("PUSH " + "#-1" + "\n");
	    	b.append("PUSH " + ".IY" + "\n");
	    	b.append("PUSH " + ".SR");
	    	return b.toString();
    	}
    	//MOVE .SP, .R0
    	//PUSH #-1 //Introduce en la pila el valor de retorno
    	//PUSH .IY // Introduce en la pila el enlace de control
    	//PUSH .SR // Introduce en la pila el estado de la máquina
    	
    	
    	//Quadruple - [MVP T_1, z, null],
    	//mueve el puntero de una variable a un temporal
    	if(quadruple.getOperation().equals("MVP")) {
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	String r = operacion(quadruple.getResult());
	    	b.append("MOVE " + o1 + ", " + r);
	    	return b.toString();
    	}
    	//MOVE /1000, #-4[.IX]
    	
    	
    	//Quadruple - [PARAM T_1, null, null],
    	//Introduce un parámetro en la pila
    	if(quadruple.getOperation().equals("PARAM")) {
    		StringBuffer b = new StringBuffer();
    		String r = operacion(quadruple.getResult());
    		b.append("PUSH " + r);
    		return b.toString();
    		}
    	//PUSH #-4[.IX]
    	
    	
    	//Quadruple - [CALL L_decrementa, null, null],
    	//Actualizamos los registro IX y IY e invocamos al subprograma,
    	//restauramos el puntero de pila, el registro IX y IY, después de la instrucción RET
    	if(quadruple.getOperation().equals("CALL")) {
	    	StringBuffer b = new StringBuffer();
	    	String r = operacion(quadruple.getResult());
	    	b.append("MOVE " + ".IX" + ", " + ".IY" + "\n");
	    	b.append("MOVE " + ".R0" + ", " + ".IX" + "\n");
	    	b.append("CALL /" + r + "\n");
	    	b.append("MOVE " + ".IX" + ", " + ".SP" + "\n");
	    	b.append("MOVE " + "#-1[.IX]" + ", " + ".R0" + "\n");
	    	b.append("MOVE " + ".IY" + ", " + ".IX" + "\n");
	    	b.append("MOVE " + ".R0" + ", " + ".IY");
	    	return b.toString();
    	}
    	//MOVE .IX, .IY
    	//MOVE .R0, .IX
    	//CALL /L_decrementa // Introduce en la pila el contador del programa PC
    	//MOVE .IX, .SP
    	//MOVE #-1[.IX], .R0
    	//MOVE .IY, .IX
    	//MOVE .R0, .IY
    	
    	
    	//Quadruple - [PRINTC T_2, L_4, null],
    	//WRSTR /L_4
    	//WRCHAR #10
    	
    	
    	//Quadruple - [HALT null, null, null],
    	//finalizamos la ejecución de programa principal
    	if(quadruple.getOperation().equals("HALT")) {
	    	StringBuffer b = new StringBuffer();
	    	b.append("HALT");
	    	return b.toString();
    	}
    	//HALT
    	
    	
    	//Quadruple - [ETIQUETA L_decrementa, null, null],
    	//Etiquetas usadas para saltos de comienzo de subprogarmas o sentencias
    	//codicionales, bluces for.
    	if(quadruple.getOperation().equals("ETIQUETA")) {
	    	StringBuffer b = new StringBuffer();
	    	String r = operacion(quadruple.getResult());
	    	b.append(r + " : NOP ");
	    	return b.toString();
    	}
    	//L_decrementa : NOP
    	
    	
    	//Quadruple - [VAR T_6, 0, null],
    	//Introduce una variable en la pila dentro de su R.A.
    	if(quadruple.getOperation().equals("VAR")) {
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	b.append("PUSH " + o1);
	    	return b.toString();
    	}
    	//PUSH #0
    	
    	//Quadruple - [DATAPUNTERO null, 13, null],
    	//SUB .IX, #13
    	//MOVE .A, .SP
    	
    	
    	//Quadruple - [MVP T_0, x, null],
    	//MOVE #-3[.IX], #-6[.IX]
    	
    	
    	//Quadruple - [MV T_1, 1, null],
    	//mueve el valor a un temporal
    	if(quadruple.getOperation().equals("MV")) {
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	String r = operacion(quadruple.getResult());
	    	b.append("MOVE " + o1 + ", " + r);
	    	return b.toString();
    	}
    	//MOVE #1, #-7[.IX]
    	
    	
    	//Quadruple - [SUB T_2, T_0, T_1],
    	//Operación, resta dos operando y guarda el valor en el registro acumulador A
    	//mueve el valor del registro acumulador A al resultado.
    	if(quadruple.getOperation().equals("SUB")){
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	String o2 = operacion(quadruple.getSecondOperand());
	    	String r = operacion(quadruple.getResult());
	    	b.append("SUB " + o1 + ", " + o2 + "\n");
	    	b.append("MOVE " + ".A " + ", " + r);
	    	return b.toString();
    	}
    	//SUB #-6[.IX], #-7[.IX]
    	//MOVE .A , #-8[.IX]
    	
    	
    	//Quadruple - [MVA T_3, y, null],
    	//mueve el valor de la dirección de memoria de una variable a un temporal
    	if(quadruple.getOperation().equals("MVA")) {
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	String r = operacion(quadruple.getResult());
	    	//b.append(o1);   ???????
	    	b.append("MOVE " + ".A" + ", " + r);
	    	return b.toString();
    	}
    	//SUB .IX, #5
    	//MOVE .A, #-9[.IX]
    	
    	//Quadruple - [STP T_3, T_2, null],
    	//Almacena el valor de un temporal en la dirección indicada por el otro temporal
    	if(quadruple.getOperation().equals("STP")) {
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	String r = operacion(quadruple.getResult());
	    	b.append("MOVE " + r + ", " + ".R1" + "\n");
	    	b.append("MOVE " + o1 + ", " + "[.R1]");
	    	return b.toString();
    	}
    	//MOVE #-9[.IX], .R1
    	//MOVE #-8[.IX], [.R1]
    	
    	
    	//Quadruple - [PRINTC T_4, L_0, null],
    	//WRSTR /L_0
    	//WRCHAR #10
    	
    	
    	//Quadruple - [MVP T_5, y, null],
    	//MOVE #-5[.IX], #-11[.IX]
    	
    	
    	//Quadruple - [PRINTI T_5, null, null],
    	//Imprime un ENTERO y da un salto de línea
    	if(quadruple.getOperation().equals("PRINTI")) {
	    	StringBuffer b = new StringBuffer();
	    	String r = operacion(quadruple.getResult());
	    	b.append("WRINT " + r + "\n");
	    	b.append("WRCHAR #10");
	    	return b.toString();
    	}
    	//WRINT #-11[.IX]
    	//WRCHAR #10
    	
    	
    	//Quadruple - [FIN_SUB L_decrementa, 5, null],
    	//posicionamos el puntero de la pila SP y ejecutamos la instrucción de retorno.
    	if(quadruple.getOperation().equals("FIN_SUB")) {
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	b.append("SUB " + ".IX" + ", " + o1 + "\n");
	    	b.append("MOVE " + ".A" + ", " + ".SP" + "\n");
	    	b.append("RET" + "\n");
	    	return b.toString();
    	}
    	//SUB .IX, #5
    	//MOVE .A, .SP
    	//RET //extrae de la pila el contador de programa PC
    	
    	
    	//Quadruple - [CADENA "y = ", L_0, null],
    	
    	
    	//Quadruple - [CADENA "*** INVOCACION DE SUBPROGRAMAS***", L_2, null],
    	
    	
    	//Quadruple - [CADENA "FIN", L_4, null]]
    	//Etiqueta de cadena de carácteres
    	if(quadruple.getOperation().equals("CADENA")) {
	    	StringBuffer b = new StringBuffer();
	    	String o1 = operacion(quadruple.getFirstOperand());
	    	b.append(o1 + " : DATA " + quadruple.getResult() + " ");
	    	return b.toString();
    	}
    	//L_0 : DATA "y = "
    	//L_2 : DATA "*** INVOCACION DE SUBPROGRAMAS***"
    	//L_4 : DATA "FIN"
    	return "";

    
    */
    	StringBuffer stringBuffer = new StringBuffer();
    	stringBuffer.append("\n;"+quadruple+"\n");
    	if(quadruple.getOperation().equals("ETIQUETA")) {
	    	
	    	String resultado = operacion(quadruple.getResult());
	    	stringBuffer.append(resultado + " : NOP ");
	    	return stringBuffer.toString();
    	}
    	if(quadruple.getOperation().equals("DATA")) {
	    	  // ATENCIÓN TODAVÍA POR HACER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    	return stringBuffer.toString();
    	}
    	if(quadruple.getOperation().equals("HALT")) {
	    	
	    	stringBuffer.append("HALT");
	    	return stringBuffer.toString();
    	}	
    	if(quadruple.getOperation().equals("PRINTI")) {
    		
	    	String resultado = operacion(quadruple.getResult());
	    	stringBuffer.append("WRINT " + resultado + "\n");
	    	stringBuffer.append("WRCHAR #10");
	    	return stringBuffer.toString();
    	}
    	if(quadruple.getOperation().equals("MVA")) {
	    	
	    	String operando1 = operacion(quadruple.getFirstOperand());
	    	String resultado = operacion(quadruple.getResult());
	    	stringBuffer.append("MOVE " + operando1 + ", " + resultado);
	    	return stringBuffer.toString();
    	}
    	if(quadruple.getOperation().equals("MVP")) {
    		
    		String operando1 = operacion(quadruple.getFirstOperand());
    		String resultado = operacion(quadruple.getResult());
    		stringBuffer.append("MOVE " + operando1 + ", " + ".R1" + "\n");
    		stringBuffer.append("MOVE " + "[.R1]" + ", " + resultado);
    		return stringBuffer.toString();
    		}
    	if(quadruple.getOperation().equals("STP")) {
	    	
	    	String operando1 = operacion(quadruple.getFirstOperand());
	    	String resultado = operacion(quadruple.getResult());
	    	stringBuffer.append("MOVE " + resultado + ", " + ".R1" + "\n");
	    	stringBuffer.append("MOVE " + operando1 + ", " + "[.R1]");
	    	return stringBuffer.toString();
    	}
    	if(quadruple.getOperation().equals("MV")) {
	    	
	    	String operando1 = operacion(quadruple.getFirstOperand());
	    	String resultado = operacion(quadruple.getResult());
	    	stringBuffer.append("MOVE " + operando1 + ", " + resultado);
	    	return stringBuffer.toString();
    	}
    	if(quadruple.getOperation().equals("ADD")) {
	    	
	    	String operando1 = operacion(quadruple.getFirstOperand());
	    	String operando2 = operacion(quadruple.getSecondOperand());
	    	String resultado = operacion(quadruple.getResult());
	    	stringBuffer.append("ADD " + operando1 + ", " + operando2+ "\n");
	    	stringBuffer.append("MOVE " + ".A " + ", " + resultado);
	    	return stringBuffer.toString();
    	}
    	if(quadruple.getOperation().equals("AND")) {
	    	
	    	String operando1 = operacion(quadruple.getFirstOperand());
	    	String operando2 = operacion(quadruple.getSecondOperand());
	    	String resultado = operacion(quadruple.getResult());
	    	stringBuffer.append("AND " + operando1 + ", " + operando2+ "\n");
	    	stringBuffer.append("MOVE " + ".A " + ", " + resultado);
	    	return stringBuffer.toString();
    	}
    	if(quadruple.getOperation().equals("EQ")) {
    		LabelFactory lF = new LabelFactory();
    		LabelIF l1 = lF.create();
			LabelIF l2 = lF.create();
	    	String operando1 = operacion(quadruple.getFirstOperand());
	    	String operando2 = operacion(quadruple.getSecondOperand());
	    	String resultado = operacion(quadruple.getResult());
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
    		LabelFactory lF = new LabelFactory();
    		LabelIF l1 = lF.create();
			LabelIF l2 = lF.create();
	    	String operando1 = operacion(quadruple.getFirstOperand());
	    	String operando2 = operacion(quadruple.getSecondOperand());
	    	String resultado = operacion(quadruple.getResult());
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
    		LabelFactory lF = new LabelFactory();
    		LabelIF l1 = lF.create();
			LabelIF l2 = lF.create();
	    	String operando1 = operacion(quadruple.getFirstOperand());
	    	String resultado = operacion(quadruple.getResult());
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
	    	
	    	String operando1 = operacion(quadruple.getFirstOperand());
	    	String operando2 = operacion(quadruple.getSecondOperand());
	    	String resultado = operacion(quadruple.getResult());
	    	stringBuffer.append("DIV " + operando1 + ", " + operando2+ "\n");
	    	stringBuffer.append("MOVE " + ".A " + ", " + resultado);
	    	return stringBuffer.toString();
    	}
    	if(quadruple.getOperation().equals("PRINTC")) {
    		String operando1 = operacion(quadruple.getResult());
    		stringBuffer.append("WRSTR /" + operando1 + "\n");
    		stringBuffer.append("WRCHAR #10");
	    	return stringBuffer.toString();
    	}
    	if(quadruple.getOperation().equals("CADENA")) {
	    	String operando1 = operacion(quadruple.getFirstOperand());
	    	stringBuffer.append(operando1 + " : DATA " + quadruple.getResult() + " ");
	    	return stringBuffer.toString();
    	}
    	return quadruple.toString(); 	
    }
	public String operacion (OperandIF operando) {
		if(operando instanceof Variable) {
			Variable variable = (Variable)operando;
			if(variable.getScope().getLevel()==0) return "#" + variable.getAddress();
			else return "#-" + variable.getAddress() + "[.IX]";
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
		return operando.toString();
	}
	
	
}
