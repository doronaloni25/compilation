package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
public class AST_VAR_FIELD extends AST_VAR
{
	public AST_VAR var;
	public String fieldName;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(AST_VAR var,String fieldName)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> var DOT ID\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.fieldName = fieldName;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.print("AST NODE FIELD VAR\n");

		/**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
		/**********************************************/
		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",fieldName);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FIELD\nVAR\n...->%s",fieldName));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}
	@Override
	public TYPE SemantMe() {
		// we are trying to get the type of a field in a class
		// the next line also takes care of verifying the var is well defined.
		TYPE varType = var.SemantMe();
		System.out.println("type is: " + varType.name);
		if(varType == null) {
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		//make sure the variable is a class, so you can access its fields
		if(!varType.isClass()) {
			System.out.println("not a class, vartype is: " + varType.name);
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		TYPE_CLASS classType = (TYPE_CLASS)varType;
		//check if the field is defined in the class, and if it is, return its type
		TYPE_CLASS_VAR_DEC fieldType = classType.classDec.fieldInClass(fieldName);
		System.out.println("fieldName = " + fieldName);
		System.out.println("fieldType = " + fieldType.t.name);
		if(fieldType != null) {
			return fieldType.t;
		}
		else
		{
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		return null;
	}
}

