package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
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
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		// if the return type is void, the expression should be null
		if(exp == null)
		{
			if(currentFunction.returnType != TYPE_VOID.getInstance())
			{
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
			return TYPE_VOID.getInstance();
		}
		// if the return type is not void, we should check the expression
		else
		{
			TYPE t = exp.SemantMe();
			//check if the return type is the same as the expression type or son of it
			if(!HelperFunctions.isInhiritedFromOrNil(t, currentFunction.returnType))
			{
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
			//check if it should be t
			return currentFunction.returnType;
		}
	}
}
