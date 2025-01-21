package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
public class AST_VAR_EXP extends AST_VAR
{
	public AST_VAR var;
	public AST_EXP exp;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_EXP(AST_VAR var,AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== var -> var [ exp ]\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
	}

	/*****************************************************/
	/* The printing message for a exp var AST node */
	/*****************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST EXP VAR */
		/*************************************/
		System.out.print("AST NODE EXP VAR\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/****************************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"EXP\nVAR\n...[...]");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var       != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
	@Override
	public TYPE SemantMe() {
		TYPE expType = exp.SemantMe();
		if (expType == null) {
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		//if the expression is not an int (it is an array[exp])
		if(expType != TYPE_INT.getInstance()) {
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		// if exp is constant, check if its not negative
		if(HelperFunctions.isConstant(exp) && ((AST_EXP_INT)exp).value < 0) {
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		// the next line also takes care of verifying the var is well defined.
		TYPE varType = var.SemantMe();

		if(varType == null || !varType.isArray())
		{
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		TYPE_ARRAY array = (TYPE_ARRAY) varType;
		return array.getType();
	}

	@Override
	public TYPE IRme()
	{
		//TODO-implement for ex5
		return null;
	}
}
