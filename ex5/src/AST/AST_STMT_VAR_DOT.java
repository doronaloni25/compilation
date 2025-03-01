package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public class AST_STMT_VAR_DOT extends AST_STMT
{
    AST_VAR v;
    String name;
    AST_EXP exp;
    AST_COMMA_EXP_LIST expList;
    public AST_STMT_VAR_DOT(AST_VAR v,String name,  AST_EXP exp, AST_COMMA_EXP_LIST expList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if( exp == null)
        {
            System.out.format("====================== stmt -> var DOT ID LPARAN RPARAN SEMICOLON\n");
        }
        else if(expList == null)
        {
		    System.out.format("====================== stmt -> var DOT ID LPAREN exp RPAREN SEMICOLON\n");
        }
        else
        {
           System.out.format("====================== stmt -> var DOT ID LPAREN exp commaExpList  RPAREN SEMICOLON\n");
        }

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
        this.name = name;
        this.exp = exp;
        this.expList = expList;
    }
    @Override
    public TYPE SemantMe() 
    {
        // we work on "class.function() or class.function(exp, exp, exp)"

        TYPE varTypeT = v.SemantMe();
        //check if the variable is a class type
        if(varTypeT == null || !(varTypeT.isClass()))
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        // cast to type class
        TYPE_CLASS varType = (TYPE_CLASS)varTypeT;
        //check if the class has a method with the given name
        TYPE_FUNCTION found_function = varType.classDec.functionInClass(name);
        if(found_function == null)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        //check if the function has the right number of arguments
        TYPE_LIST function_arguments_list = new TYPE_LIST(null, null); 
        if(exp != null)
        {
            function_arguments_list.head = exp.SemantMe();
            if(expList != null)
            {
                function_arguments_list.tail = expList.SemantMe();
            }
        }
        if(!HelperFunctions.compareTypeLists(function_arguments_list, found_function.params))
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        return found_function.returnType;
    }
	
    @Override
        public TEMP IRme(){
        //object is the class instance
        TEMP object = var.IRme();
        ArrayList<TEMP> funcArgs = new ArrayList<TEMP>();
        if(exp != null)
        {
            funcArgs.add(exp.IRme());
            if(expList != null)
            {
                expList.IRme(funcArgs);
            }
        }
        // object is the class instance, name is the function name,
        // funcArgs is the arguments and offset in class methods table
        // null means that we dont use the return value
        IR.getInstance().Add_IRcommand(new IRcommand_Call_Method(null, object, name, funcArgs, this.classDec.getMethodOffset(name)));
        return null;
    }
}