package AST;

public class AST_FUNC_DEC_RULE_ONE extends AST_FUNC_DEC
{ 

    public AST_FUNC_DEC_RULE_ONE (AST_TYPE type,String name, AST_STMT_LIST stmtList) {
        super(type, name, stmtList);
        // Set a unique serial number/*  */
        SerialNumber = AST_Node_Serial_Number.getFresh();
        
        System.out.format("====================== funcDec -> type ID LPARAN RPARAN LBRACE STMT_LIST RBRACE \n");
    }
}