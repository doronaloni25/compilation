package AST;
import TYPES.TYPE;
public class AST_FUNC_DEC_RULE_ONE extends AST_FUNC_DEC
{ 

    public AST_FUNC_DEC_RULE_ONE (AST_TYPE type,String name, AST_STMT_LIST stmtList) {
        super(type, name, stmtList);
        // Set a unique serial number/*  */
        SerialNumber = AST_Node_Serial_Number.getFresh();
        
        System.out.format("====================== funcDec -> type ID LPARAN RPARAN LBRACE STMT_LIST RBRACE \n");
    }
@Overrride
 public TYPE SemantMe()
 {
    
   
    TYPE returnType = type.SemantMe();
    TYPE_FUNCTION function = new TYPE_FUNCTION(returnType, name, null);
    SYMBOL_TABLE.getInstance().enter(name, function);
    TYPE_CLASS_DEC calssDec = SYMBOL_TABLE.getInstance.inClass();

    //func declaration inside a class
    if (classDec != null)
    {
       
        SYMBOL_TABLE.getInstance.beginScope();
        SYMBOL_TABLE.getInstance().inFunction = function;
        TYPE functionReturnType = stmtList.SemantMe();
        if(HelperUtils.isInhiritedFromOrNil(functionReturnType, returnType) == false)
        {
            //TODO- throw exeption
        }
        classDec.addFunction(function);
        SYMBOL_TABLE.getInstance.endScope();
        SYMBOL_TABLE.getInstance().inFunction = null;
        return function;


    }
    //func declaration in a global scope
    else{
       
        if (SYMBOL_TABLE.getInstance().find(name) != null)
        {
            //TODO - throw exception
        }
        if (SYMBOL_TABLE.getInstance().isGlobalScope() == False)
        {
            //TODO- throw exeption
        }
        if (name.equals("PrintInt") || name.equals("PrintString"))
        {
                //TODO- throw exeption
        }
        SYMBOL_TABLE.getInstance.beginScope();
        SYMBOL_TABLE.getInstance().inFunction = function;
        TYPE functionReturnType = stmtList.SemantMe();
        if(HelperUtils.isInhiritedFromOrNil(functionReturnType, returnType) == false)
            {
                //TODO- throw exeption
            }
    
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().inFunction = null;
        return function;
        

    }
 }

}