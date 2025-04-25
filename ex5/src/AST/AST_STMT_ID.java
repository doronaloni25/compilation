package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import HelperFunctions.*;
import java.util.ArrayList;
public class AST_STMT_ID extends AST_STMT
{
    String name;
    AST_EXP exp;
    AST_COMMA_EXP_LIST expList;
    boolean isMethod = false;
    TYPE_CLASS_DEC classDec;
    
    public AST_STMT_ID(String name,  AST_EXP exp, AST_COMMA_EXP_LIST expList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if( exp == null)
        {
            System.out.format("====================== exp ->  ID LPARAN RPARAN SEMICOLON\n");
        }
        else if(expList == null)
        {
		    System.out.format("====================== exp ->  ID LPAREN exp RPAREN SEMICOLON\n");
        }
        else
        {
           System.out.format("====================== exp ->  ID LPAREN exp commaExpList RPAREN SEMICOLON\n");
        }

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
        this.name = name;
        this.exp = exp;
        this.expList = expList;
    }
    @Override
    public TYPE SemantMe() 
    {
        TYPE found_functionT = SYMBOL_TABLE.getInstance().find(name);
        if(found_functionT == null)
        {
            //System.out.println("func not found");
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        //check if the type returned is a function
        if(!found_functionT.isFunction()){
            //System.out.println("func is not a func");
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        // cast to type function
        TYPE_FUNCTION found_function = (TYPE_FUNCTION)found_functionT;
        this.isMethod = (found_function.classDecName != null);
        this.classDec = SYMBOL_TABLE.getInstance().inClass;
        //check if the function has the right number of arguments
        TYPE_LIST function_arguments_list = new TYPE_LIST(null, null); 
        if(exp != null)
        {
            function_arguments_list.head = exp.SemantMe();
            //System.out.println("exp in func type: " + function_arguments_list.head.name);
            if(expList != null)
            {
                function_arguments_list.tail = expList.SemantMe();
            }
        }
        if( !HelperFunctions.compareTypeLists(function_arguments_list, found_function.params))
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        
        return found_function.returnType;
    }

    @Override
    public TEMP IRme()
    {
        // Take care of special cases, PrintInt and PrintString
        if (name.equals("PrintInt")){
            TEMP t = exp.IRme();
            IR.getInstance().Add_IRcommand(new IRcommand_PrintInt(t));
            return null;
        }
        else if (name.equals("PrintString"))
        {
            TEMP t = exp.IRme();
            IR.getInstance().Add_IRcommand(new IRcommand_PrintString(t));
            return null;
        }
        // any other function call
        else{
            ArrayList<TEMP> funcArgs = new ArrayList<TEMP>();
            if(exp != null)
            {
                funcArgs.add(exp.IRme());
                if(expList != null)
                {
                    expList.IRme(funcArgs);
                }
            }
            if (this.isMethod){
                System.out.println("exp id called for a method inside a class");
                IR.getInstance().Add_IRcommand(new IRcommand_Call_Func(name, null, funcArgs, isMethod, classDec.name));
            }
            else{
                System.out.println("exp id called for a normal func call");
                IR.getInstance().Add_IRcommand(new IRcommand_Call_Func(name, null, funcArgs));
            }
        }
        return null;
    }
}