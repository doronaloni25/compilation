package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils.HelperUtils;

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
}