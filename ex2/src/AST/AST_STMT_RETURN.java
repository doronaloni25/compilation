package AST;

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
			System.out.format("====================== stmt -> RETURN exp ( %s ) SEMICOLON\n");

		}

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.exp = exp;
	}
}
