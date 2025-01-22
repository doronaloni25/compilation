package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(AST_EXP cond,AST_STMT_LIST body)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.format("=========STMT -> IF LPAREN exp RPAREN LBRACE stmtList RBRACE\n");
		
		this.cond = cond;
		this.body = body;
	}
	@Override
    public TYPE SemantMe() 
    {
		TYPE t = cond.SemantMe();
		if (t == null)
		{
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		TYPE_INT intType = TYPE_INT.getInstance();
		if(t != intType)
		{
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		//need to open scope for the if body
		SYMBOL_TABLE.getInstance().beginScope();
		body.SemantMe();
		if (t == null)
		{
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		SYMBOL_TABLE.getInstance().endScope();
		return intType;
	}
	public TEMP IRme()
	{
		/*******************************/
		/* [1] Allocate fresh label */
		/*******************************/
		String label_end = IRcommand.getFreshLabel("if_end");

		/********************/
		/* [2] cond.IRme(); */
		/********************/
		TEMP cond_temp = cond.IRme();

		/******************************************/
		/* [3] Jump conditionally to the if end */
		/******************************************/
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(cond_temp,label_end));

		/********************/
		/* [4] body.IRme(); */
		/********************/
		body.IRme();

		/*************************************/
		/* [5] exit label for the if loop */
		/*************************************/
		IR.getInstance().Add_IRcommand(new IRcommand_Label(label_end));

		/******************/
		/* [6] return null */
		/******************/
		return null;
	}
}