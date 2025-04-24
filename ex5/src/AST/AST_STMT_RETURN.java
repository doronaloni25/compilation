package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public class AST_STMT_RETURN extends AST_STMT {
    AST_EXP exp;
	TYPE_FUNCTION currentFunction;
	TYPE_CLASS_DEC classDec;
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
			System.out.format("====================== stmt -> RETURN exp SEMICOLON\n");

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
		// Save for IRme()
		this.currentFunction = currentFunction;
		if(currentFunction == null)
		{
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		TYPE_CLASS_DEC classDec = SYMBOL_TABLE.getInstance().inClass;
		// save for IRme()
		this.classDec = classDec;
		// if the expression is null, the return type should be void
		if(exp == null)
		{
			if(currentFunction.returnType != TYPE_VOID.getInstance())
			{
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
			return TYPE_VOID.getInstance();
		}
		// if the expression isnt null
		else
		{
			TYPE t = exp.SemantMe();
			//System.out.println("return type is " + t.name);
			//System.out.println("return type of func is " + currentFunction.returnType.name);
			//check if the return type is the same as the expression type or son of it
			if(!HelperFunctions.isInhiritedFromOrNil(t, currentFunction.returnType))
			{
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
			//check if it should be t
			return currentFunction.returnType;
		}
	}

	@Override
	public TEMP IRme()
	/*the IRFuncReturn expects to get the TEMP of the returned exp. if the func return void, then null*/ 
	{
		TEMP tExp;
		String endLabel;
		if (exp != null){
			tExp = exp.IRme();
		}
		else{
			tExp = null;
		}
		if (this.classDec != null){
			endLabel = HelperFunctions.formatMethodExitLabel(this.classDec.name, currentFunction.name);
		} 
		else{
			endLabel = HelperFunctions.formatExitLabel(currentFunction.name);
		}
		IR.getInstance().Add_IRcommand(new IRcommand_FuncReturn(tExp, endLabel));
		return tExp;
	}
}
