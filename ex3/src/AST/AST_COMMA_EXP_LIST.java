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
		inFunction = SYMBOL_TABLE.getInstance().inFunction();	
		TYPE_LIST paramList;
		//comma exp list is used in function declaration
		//insert the arguments to the TYPE_FUNCTION.params
		if(inFunction != null)
		{
			TYPE t = head.SemantMe();
			paramList = new TYPE_LIST(t, null);
			if(tail != null)
			{
				AST_COMMA_EXP_LIST temp = tail;
				while(temp != null)
				{
					TYPE t2 = temp.head.SemantMe();
					paramList = new TYPE_LIST(t2, paramList);
					temp = temp.tail;
				}
			}
			func.params = paramList;
			//not sure what to return here.
			return paramList;

		}
		//comma exp list is used in function call:
		//paramListFunc- the parameters of the original function
		//paramListCheck- the parameters of the function call, that we check if they are correct
		// check if the arguments types match to the function declaration
		//TODO: check if the arguments are already in the symbol table
		else
		{
			//check if the arguments are correct
			TYPE_LIST paramListFunc = func.params;
	
			TYPE funcListHead = paramListFunc.head;
			TYPE checkListhead = head.SemantMe();
			if(isInhiritedFromOrNil(funcListHead,checkListhead) == false)
			{
				//TODO- throw exception, the arguments are not correct
			}
			TYPE_LIST paramListCheck = new TYPE_LIST(checkListhead, null);
			if(tail != null)
			{
				AST_COMMA_EXP_LIST tempCheck = tail;
				TYPE tempFunc = paramListFunc.tail;
				while(tempCheck != null && tempFunc!=null)
				{
				
					TYPE tempCheck = tempCheck.head.SemantMe();
					if(isInhiritedFromOrNil(tempCheck,tempFunc) == false)
					{
						//TODO- throw exception, the arguments are not correct
					}
					paramList = new TYPE_LIST(tempCheck, paramListCheck);
					tempCheck = tempCheck.tail;
					tempFunc = tempFunc.tail;
				}
				if(tempCheck != null || tempFunc != null)
				{
					//TODO- throw exception, the arguments are not correct
				}

		}
		//not sure what to return here
		return paramListCheck;

	}
	
	}
}