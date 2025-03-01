package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;

public class AST_EXP_VAR_DOT extends AST_EXP
{
    AST_VAR v;
    String name;
    AST_EXP exp;
    AST_COMMA_EXP_LIST expList;
    TYPE_CLASS_DEC classDec; 
    public AST_EXP_VAR_DOT(AST_VAR v,String name,  AST_EXP exp, AST_COMMA_EXP_LIST expList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if( exp == null)
        {
            System.out.format("====================== exp -> var DOT ID LPARAN RPARAN\n");
        }
        else if(expList == null)
        {
		    System.out.format("====================== exp -> var DOT ID LPAREN exp RPAREN\n");
        }
        else
        {
           System.out.format("====================== exp -> var DOT ID LPAREN exp commaExpList RPAREN\n");
        }

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
        this.name = name;
        this.exp = exp;
        this.expList = expList;
    }

    public TYPE SemantMe(){
        // we work on "class.function() or class.function(exp, exp, exp)"

        TYPE varTypeT = v.SemantMe();
        //System.out.println("Semanting var.id(), var is: " + varTypeT.name);

        //check if the variable is a class type
        if(varTypeT == null || !(varTypeT.isClass()))
        {
            //System.out.println("error, vartype isnt a class, its actually " + varTypeT.name);
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        // cast to type class
        TYPE_CLASS varType = (TYPE_CLASS)varTypeT;
        //for IRme
        this.classDec = varType.classDec;
        //check if the class has a method with the given name
        TYPE_FUNCTION found_function = varType.classDec.functionInClass(name);
        if(found_function == null)
        {
            //System.out.println("error, function not found in var: " + name);
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

    public TEMP IRme(){
        //dest is the return value of the function
        TEMP dest = TEMP_FACTORY.getInstance().getFreshTEMP();
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
        // dest is the return value, object is the class instance, name is the function name,
        // funcArgs is the arguments and offset in class methods table
        IR.getInstance().Add_IRcommand(new IRcommand_Call_Method(dest, object, name, funcArgs, this.classDec.getMethodOffset(name)));
        return dest;
    }
}