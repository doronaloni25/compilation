package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_EXP exp;
	public Boolean isNewExp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp, Boolean isNewExp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if(isNewExp)
		{
			System.out.format("====================== stmt -> var ASSIGN newExp SEMICOLON\n"); //case 1
		}
		else
		{
			System.out.format("====================== stmt -> var ASSIGN exp SEMICOLON\n"); //case 2
		}

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
		this.isNewExp = isNewExp;
	}

	@Override
    public TYPE SemantMe() 
    {
		TYPE t1 = var.SemantMe();
		if (t1 == null){
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		TYPE t2 = exp.SemantMe();
		if (t2 == null){
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		if (isNewExp){
			// if one of them is an array, both should be (or t2 is NIL)
			if (t1.isArray() || t2.isArray()){
				if (t2 == TYPE_NIL.getInstance()){
					return (TYPE_ARRAY)t1;
				}
				if (!(t1.isArray() && t2.isArray())){
					HelperFunctions.printError(line, this.getClass().getSimpleName());
				}
				TYPE_ARRAY t1Arr = (TYPE_ARRAY)t1;
				TYPE_ARRAY t2Arr = (TYPE_ARRAY)t2;
				if (t1Arr.type.name.equals(t2Arr.type.name)){
					return t1Arr;
				}
				else{
					HelperFunctions.printError(line, this.getClass().getSimpleName());
				}          
			}
		}

		if(!HelperFunctions.isInhiritedFromOrNil(t2, t1))
		{
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		return t1;
	}


	@Override
	public TEMP IRme()
	{
		//if case 2:
		if (!isNewExp)
		{
			TEMP tExp = exp.IRme()
			name = ((AST_VAR_SIMPLE) var).name;
			IR.getInstance().Add_IRcommand(new IRcommand_Store(name, tExp));
			return null;
		}
		else{
			//TODO- implenemt for ex5 (case1)
		}
		 
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
}
