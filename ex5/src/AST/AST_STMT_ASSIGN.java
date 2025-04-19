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
		if (var instanceof AST_VAR_SIMPLE)
		{
			this.data = SYMBOL_TABLE.getInstance().find_data_by_name(((AST_VAR_SIMPLE)var).name);
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
		IRcommand cmd = null;
		if (var instanceof AST_VAR_SIMPLE){
			TEMP tExp = exp.IRme();
			if( exp instanceof AST_EXP_STRING)
			{
				cmd = new IRcommand_Store(((AST_VAR_SIMPLE)var).name, tExp,  true, this.data);
			}
			//here we are int
			else
			{
				cmd = new IRcommand_Store(((AST_VAR_SIMPLE)var).name, tExp, false, this.data);
			}
			
		}
		//class field case
		else if(var instanceof AST_VAR_FIELD){
			AST_VAR_FIELD varField = (AST_VAR_FIELD) var;
			// Get temp of class instance!
			TEMP tClass = varField.var.IRme();
			String fieldName = varField.fieldName;
			// get class type
			TYPE_CLASS_DEC classType = ((TYPE_CLASS)varField.myClassInstance).classDec;
			// get the field offset
			int offset = classType.getFieldOffset(fieldName);
			TEMP tValue = exp.IRme();
			cmd = new IRcommand_Store_Into_Field(tClass, tValue, offset, fieldName);
		}
		//Array case
		else if(var instanceof AST_VAR_EXP)
		{
			AST_VAR_EXP varExp = (AST_VAR_EXP) var;
			// get temp of array instance
			TEMP tArray = varExp.var.IRme();
			// get temp of array index
			TEMP tIndex = varExp.exp.IRme();
			TEMP tValue= exp.IRme();
			cmd = new IRcommand_Store_Into_Array(tArray, tIndex, tValue);
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

	}
}
