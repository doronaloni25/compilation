package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
public class AST_STMT_VAR_DEC extends AST_STMT
{

	public AST_VAR_DEC varDec;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_VAR_DEC(AST_VAR_DEC varDec)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== stmt -> varDec \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.varDec = varDec;
	}
	
	@Override
    public TYPE SemantMe() 
    {
        TYPE type = varDec.SemantMe();
		if (type == null) 
		{
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
        return type;
    }

	@Override
	public TYPE IRme()
	{
		return varDec.IRme();
	}
}