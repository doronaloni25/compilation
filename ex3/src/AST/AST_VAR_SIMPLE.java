package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
public class AST_VAR_SIMPLE extends AST_VAR
{
	/************************/
	/* simple variable name */
	/************************/
	public String name;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(String name)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n(%s)",name));
	}
	@Override
	public TYPE SemantMe() {
		// if the variable is accessed within class, need to make sure it is defined
		TYPE_CLASS_DEC classDec = SYMBOL_TABLE.getInstance().inClass();
		if (classDec != null){
			TYPE_CLASS_VAR_DEC fieldDec = classDec.fieldInClass(name); 
			if (fieldDec == null){
				// Attempt finding name in global scope if not found in class's ancestors scopes
				TYPE type = SYMBOL_TABLE.getInstance().find(name);
				if(type == null) {
					//TODO- throw exception, the identifier is not defined
				}
				return type;
			}
			return fieldDec.t;
		}
		// Not in class scope, check if the variable is defined
		TYPE type =SYMBOL_TABLE.getInstance().find(name) 
		if(type == null) {
			//TODO- throw exception, the identifier is not defined
		}
		return type;

	}
}