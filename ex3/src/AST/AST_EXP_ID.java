package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils.HelperUtils;

public class AST_EXP_ID extends AST_EXP
{
    String name;
    AST_EXP exp;
    AST_COMMA_EXP_LIST expList;
    public AST_EXP_ID(String name,  AST_EXP exp, AST_COMMA_EXP_LIST expList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if( exp == null)
        {
            System.out.format("====================== exp ->  ID LPARAN RPARAN\n");
        }
        else if(expList == null)
        {
		    System.out.format("====================== exp ->  ID LPAREN RPAREN\n");
        }
        else
        {
           System.out.format("====================== exp ->  ID LPAREN exp commaExpList RPAREN\n");
        }

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
        this.name = name;
        this.exp = exp;
        this.expList = expList;
    }

    public TYPE SemantMe(){
        // same as AST_STMT_ID
        TYPE found_function = SYMBOL_TABLE.getInstance().find(name);
        if(found_function == null)
        {
            HelperUtils.printError(line);
        }
        if(!found_function.isFunction())
        {
            HelperUtils.printError(line);
        }
        // cast to type function
        TYPE_FUNCTION found_function = (TYPE_FUNCTION)found_function;
        //check if the function has the right number of arguments
        TYPE_LIST function_arguments_list = new TYPE_LIST(null, null); 
        if(exp != null)
        {
            function_arguments_list.head = exp.SemantMe();
            if(expList == null)
            {
                function_arguments_list.tail = expList.SemantMe();
            }
        }
        if( !compareTypeLists(function_arguments_list, found_function.params))
        {
            HelperUtils.printError(line);
        }
        
        return found_function.returnType;
    }
}