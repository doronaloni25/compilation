package AST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
public class AST_STMT_RETURN extends AST_STMT {
    AST_EXP exp;
    public AST_STMT_RETURN(AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if(exp == null)
		{
			System.out.format("====================== stmt -> RETURN SEMICOLON\n");
		}
		else
		{
			System.out.format("====================== stmt -> RETURN exp  SEMICOLON\n");

		}

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.exp = exp;
	}
	@Override
    public TYPE SemantMe() 
    {
		//this can be called only from a function
		TYPE_FUNCTION currentFunction = SYMBOL_TABLE.getInstance().inFunction;
		if(currentFunction == null)
		{
			//TODO: return exception with line number
		}
		// if the return type is void, the expression should be null
		if(exp == null)
		{
			if(currentFunction.returnType != TYPE_VOID.getInstance())
			{
				//TODO: return exception with line number
			}
			return new TYPE_VOID();
		}
		// if the return type is not void, we should check the expression
		else
		{
			TYPE t = exp.SemantMe();
			//check if the return type is the same as the expression type or son of it
			if(!isInhiritedFromOrNil(t, currentFunction.returnType))
			{
				//TODO: return exception with line number
			}
			//check if it should be t
			return currentFunction.returnType;
		}
	}
}
