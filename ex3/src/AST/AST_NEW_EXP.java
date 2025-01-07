package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;

public class AST_NEW_EXP extends AST_EXP
{
    public AST_TYPE type;
    public AST_EXP exp;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_NEW_EXP(AST_TYPE type, AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if(exp == null){
            System.out.print("====================== newEXP -> NEW type\n");
        }
        else
        {
            System.out.print("====================== newEXP -> NEW type LBRACK exp RBRACK\n");
        }
		

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
        this.exp = exp;
	}
}