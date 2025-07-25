package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;

public class AST_EXP_INT extends AST_EXP
{
	public int value;
	public Boolean isMinus;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_INT(int value, Boolean isMinus)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if(isMinus)
		{
			System.out.format("====================== exp -> MINUS INT\n");
		}
		else
		{
			System.out.format("====================== exp -> INT\n");
		}
		

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		if(isMinus)
		{
			this.value = (-1) * value;
		}
		else
		{
			this.value = value;
		}
		
		this.isMinus = isMinus;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST INT EXP */
		/*******************************/
		System.out.format("AST NODE INT( %d )\n",value);
	}

	public TYPE SemantMe()
	{
		return TYPE_INT.getInstance();
	}

	public TEMP IRme(){
		// create new temp and store the value in it
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(t, value));
		return t;
	}
}
