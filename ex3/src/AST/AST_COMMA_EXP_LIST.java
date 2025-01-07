package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils.HelperUtils;
public class AST_COMMA_EXP_LIST extends AST_LIST
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_EXP head;
	public AST_COMMA_EXP_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_COMMA_EXP_LIST(AST_EXP head, AST_COMMA_EXP_LIST tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) System.out.print("====================== AST_COMMA_EXP_LIST -> COMMA exp  AST_COMMA_EXP_LIST\n");
		if (tail == null) System.out.print("====================== AST_COMMA_EXP_LIST -> COMMA exp      \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
	}

	//semant me is implemented in AST_LIST
	
	
}