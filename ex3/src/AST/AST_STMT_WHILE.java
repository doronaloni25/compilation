package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond,AST_STMT_LIST body)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		System.out.format("=========STMT -> WHILE LPAREN exp RPAREN LBRACE stmtList RBRACE\n");

		this.cond = cond;
		this.body = body;
	}
	@Override
    public TYPE SemantMe() 
    {
		TYPE t = cond.SemantMe();
		TYPE_INT intType = TYPE_INT.getInstance();
		if(t != intType)
		{
			HelperFunctions.printError(line);
		}
		//need to open scope for the while body
		SYMBOL_TABLE.getInstance().beginScope();
		body.SemantMe();
		SYMBOL_TABLE.getInstance().endScope();
		return intType;
	}
}