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
	public TYPE SemantMe()
	{
		TYPE t = type.SemantMe();
		if(t == null)
		{
			HelperFunctions.printError(line,  this.getClass().getSimpleName());
		}
		if (exp != null){
			TYPE expType = exp.SemantMe();
			if (expType == null) {
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
			//if the expression is not an int (it is an array[exp])
			if(expType != TYPE_INT.getInstance()) {
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
			// if exp is constant, check if its not negative
			if(HelperFunctions.isConstant(exp) && ((AST_EXP_INT)exp).value < 0) {
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
			return new TYPE_ARRAY(t, null);
		}
		return t;
	}
}