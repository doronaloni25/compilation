package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
public class AST_FUNC_DEC_RULE_ONE extends AST_FUNC_DEC
{ 

    public AST_FUNC_DEC_RULE_ONE (AST_TYPE type,String name, AST_STMT_LIST stmtList) {
        super(type, name, stmtList);
        // Set a unique serial number/*  */
        SerialNumber = AST_Node_Serial_Number.getFresh();
        
        System.out.format("====================== funcDec -> type ID LPARAN RPARAN LBRACE STMT_LIST RBRACE \n");
    }
@Override
 public TYPE SemantMe()
 {
     if(SYMBOL_TABLE.getInstance().inFunction != null)
    {
        HelperFunctions.printError(line, this.getClass().getSimpleName());
    }
   
    TYPE returnType = type.SemantMe();
    if(returnType == null)
    {
        HelperFunctions.printError(line, this.getClass().getSimpleName());
    }
    TYPE_FUNCTION function = new TYPE_FUNCTION(returnType, name, new TYPE_LIST(null, null));
    TYPE_CLASS_DEC classDec = SYMBOL_TABLE.getInstance().inClass;

    //func declaration inside a class
    if (classDec != null)
    {
        SYMBOL_TABLE.getInstance().enter(name, function);
        SYMBOL_TABLE.getInstance().beginScope();
        SYMBOL_TABLE.getInstance().inFunction = function;
        // will take care of return type matching
        stmtList.SemantMe();
        classDec.addFunction(function, line);
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().inFunction = null;
        return function;


    }
    //func declaration in a global scope
    else{
        if (SYMBOL_TABLE.getInstance().find(name) != null)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        SYMBOL_TABLE.getInstance().enter(name, function);
        if (!SYMBOL_TABLE.getInstance().isGlobalScope())
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        if (name.equals("PrintInt") || name.equals("PrintString"))
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        SYMBOL_TABLE.getInstance().beginScope();
        SYMBOL_TABLE.getInstance().inFunction = function;
        // will take care of return type matching
        stmtList.SemantMe();
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().inFunction = null;
        return function;
    }
 }

}