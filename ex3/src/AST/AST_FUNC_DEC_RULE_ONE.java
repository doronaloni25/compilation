package AST;
import TYPES.TYPE;
public class AST_FUNC_DEC_RULE_ONE extends AST_FUNC_DEC
{ 

    public AST_FUNC_DEC_RULE_ONE (AST_TYPE type,String name, AST_STMT_LIST stmtList) {
        super(type, name, stmtList);
        public static TYPE funcType = null;
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
    funcType = type.SemantMe();
    if (SYMBOL_TABLE.getInstance().find(name) == null)
    {
        
    }
    stmtList.SemantMe();
    


 }


}