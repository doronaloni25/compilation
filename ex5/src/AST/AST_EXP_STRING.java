package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;

public class AST_EXP_STRING extends AST_EXP
{
	public String s;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_STRING(String s)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> STRING\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.s = s;
	}

	public TYPE SemantMe()
	{
		return TYPE_STRING.getInstance();
	}

	public TEMP IRme(){
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstString(t,s));
		return t;
	}
}