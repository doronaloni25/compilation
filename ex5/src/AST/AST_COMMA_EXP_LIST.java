package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
import java.util.*;
public class AST_COMMA_EXP_LIST extends AST_LIST
{
	//arguments to a function

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
	@Override
    public AST_EXP getHead() 
	{
        return this.head;
    }

    @Override
    public AST_COMMA_EXP_LIST getTail() 
	{
        return this.tail;
    }
	//the IRme is implemented in AST_LIST
	public TEMP IRme(ArrayList<TEMP> funcArgs)	
	{
		if(head == null) 
		{
			return null;
		}
		TEMP t = head.IRme();
		funcArgs.add(t);
		if (tail != null) tail.IRme(funcArgs);
		return null;
	}
}