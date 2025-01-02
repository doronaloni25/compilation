package AST;


public class AST_EXP_SIMPLE extends AST_EXP
{
    AST_EXP exp;
    public AST_EXP_SIMPLE(AST_EXP exp)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== exp -> LPARAN exp RPARAN\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.exp = exp;
    }
}