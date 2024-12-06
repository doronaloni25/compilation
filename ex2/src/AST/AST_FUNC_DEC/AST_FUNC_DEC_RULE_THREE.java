package AST;

public class AST_FUNC_DEC_RULE_THREE extends AST_FUNC_DEC
{ 
    public AST_FUNC_DEC_RULE_THREE (AST_TYPE type,String name, AST_TYPE typeTwo,  String nameTwo, AST_STMT_LIST stmtList) {
        super(type, name, stmtList);
        // Set a unique serial number/*  */
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== funcDec -> type( %s ) ID( %s ) LPAREN  
         RPAREN LBRACE STMT_LIST( %s )RBRACE \n", type, name, stmt_list);
    }
}