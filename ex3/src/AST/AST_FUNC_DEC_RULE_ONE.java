package AST;
import TYPES.TYPE;
public class AST_FUNC_DEC_RULE_ONE extends AST_FUNC_DEC
{ 

    public AST_FUNC_DEC_RULE_ONE (AST_TYPE type,String name, AST_STMT_LIST stmtList) {
        super(type, name, stmtList);
        public TYPE funcReturnType = null;
        // Set a unique serial number/*  */
        SerialNumber = AST_Node_Serial_Number.getFresh();
        
        System.out.format("====================== funcDec -> type ID LPARAN RPARAN LBRACE STMT_LIST RBRACE \n");
    }
@Overrride
 public TYPE SemantMe()
 {
    //TODO- check if in the global scope
    //check if not overloading
    //their names should not be PrintMe and PrintString
    SYMBOL_TABLE.getInstance.beginScope();
    funcReturnType = type.SemantMe();
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
    //no parameters are sent to the funtion
    stmtList.SemantMe();
    TYPE_FUNCTION function = new TYPE_FUNCTION(funcReturnType, name, null);
    SYMBOL_TABLE.getInstance().endScope();
    SYMBOL_TABLE.getInstance().enter(name, function);
    return function;
    

 }

//TODO - implement classSemantMe

}