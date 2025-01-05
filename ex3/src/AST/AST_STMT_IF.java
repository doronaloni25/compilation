package AST;
import TYPES.TYPE;
import SymbolTable.SYMBOL_TABLE;
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
		TYPE_INT intType = TYPE_INT.getInstance();
		if(t != intType)
		{
			//TODO: return exception with line number
		}
		//need to open scope for the if body
		SYMBOL_TABLE.getInstance().beginScope();
		body.SemantMe();
		SYMBOL_TABLE.getInstance().endScope();
		return intType;
	}
}