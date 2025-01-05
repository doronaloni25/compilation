package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils;
public class AST_COMMA_EXP_LIST extends AST_List
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

	@Override
	public TYPE SemantMe(TYPE_FUNCTION func)
	{
		
		//comma exp list is used in function call:
		//paramListFunc- the parameters of the original function
		//paramListCheck- the parameters of the function call, that we check if they are correct
		// check if the arguments types match to the function declaration
		//TODO: check if the arguments are already in the symbol table
	
	
		TYPE_LIST paramListFunc = func.params;

		//create TYPE_LIST of paramListCheck:
		TYPE checkListhead = head.SemantMe();
		TYPE_LIST paramListCheck = new TYPE_LIST(checkListhead, tail.SemantMe());
		//make sure the arguments are correct:
		if(compareTypeLists(paramListFunc,paramListCheck) == false){
			//TODO- throw error
		}
		else
		{
			return paramListCheck;
		}

	}
	
	
}