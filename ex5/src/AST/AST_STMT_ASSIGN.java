package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_EXP exp;
	public Boolean isNewExp;
	public String nameWithVarDecScope;
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
		// for IRme
		if (!isNewExp)
		{
			nameWithVarDecScope = HelperFunctions.getVarNameWithDecScope(((AST_VAR_SIMPLE) var).name);
		}
		return t1;
	}


	@Override
	public TEMP IRme()
	{
		IRcommand cmd;
		switch (var instanceof){
			case AST_VAR_SIMPLE:
				TEMP tExp = exp.IRme();
				cmd = new IRcommand_Store(nameWithVarDecScope, tExp);
				break;
			case AST_VAR_FIELD:
				AST_VAR_FIELD varField = (AST_VAR_FIELD) var;
				// Get temp of class instance!
				TEMP tVar = varField.var.IRme();
				String name = varField.fieldName;
				// get class type
				TYPE_CLASS_DEC classType = ((TYPE_CLASS)varField).myClassInstance.classDec;
				// get the field offset
				int offset = classType.getFieldOffset(name);
				TEMP tExp = exp.IRme();
				// TODO: check if name is needed
				cmd = new IRcommand_Store_Into_Field(tVar, tExp, offset, name);
				break;
			case AST_VAR_EXP:
				AST_VAR_EXP varExp = (AST_VAR_EXP) var;
				// get temp of array instance
				TEMP tVarExp = varExp.var.IRme();
				// get temp of array index
				TEMP tIndex = varExp.exp.IRme();
				TEMP tExp = exp.IRme();
				cmd = new IRcommand_Store_Into_Array(tVarExp, tIndex, tExp);
				break;
		}
		IR.getInstance().Add_IRcommand(cmd);
		return null;
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
