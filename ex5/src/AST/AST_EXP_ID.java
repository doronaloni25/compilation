package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;

public class AST_EXP_ID extends AST_EXP
{   //calling a function
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
		    System.out.format("====================== exp ->  ID LPAREN exp RPAREN\n");
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
        TYPE found_functionT = SYMBOL_TABLE.getInstance().find(name);
        if(found_functionT == null)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        if(!found_functionT.isFunction())
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        // cast to type function
        TYPE_FUNCTION found_function = (TYPE_FUNCTION)found_functionT;
        //check if the function has the right number of arguments
        TYPE_LIST function_arguments_list = new TYPE_LIST(null, null); 
        //System.out.println("before exp != null");
        if(exp != null)
        {
            function_arguments_list.head = exp.SemantMe();
            //System.out.println("exp in func type: " + function_arguments_list.head.name);
            if(expList != null)
            {

                function_arguments_list.tail = expList.SemantMe();
            }
        }
        //System.out.println("function_arguments_list1 " +function_arguments_list.head.name);
        //System.out.println("function_arguments_list1 " +found_function.params.head.name);
        if(!HelperFunctions.compareTypeLists(function_arguments_list, found_function.params))
        {
            //System.out.println("error compare types in function args");
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        
        return found_function.returnType;
    }

    @Override
    public TEMP IRme(){
        
        TEMP  dest = TEMP_FACTORY.getInstance().getFreshTEMP();
        ArrayList<TEMP> funcArgs = new ArrayList<TEMP>();
        if(exp != null)
        {
            funcArgs.add(exp.IRme());
            if(expList != null)
            {
                expList.IRme(funcArgs);
            }
        }
        IR.getInstance().Add_IRcommand(new IRcommand_CallFunc(name, dest, funcArgs));
        return dest;

       
    }
}