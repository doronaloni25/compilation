package AST;
import TYPES.*;
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
		//if the expression is not an int (it is an array[exp])
		//TODO- make sure expType is not negative!
		if(expType != TYPE_INT.getInstance()) {
			//TODO- throw exception
		}
		TYPE varType = var.SemantMe();

		if(varType == null || !varType.isArray())
		{
			//TODO- throw exception
		}
		TYPE_ARRAY array = (TYPE_ARRAY) varType;
		return array.getType();
	}
}
