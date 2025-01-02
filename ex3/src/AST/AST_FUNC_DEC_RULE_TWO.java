package AST;

public class AST_FUNC_DEC_RULE_TWO extends AST_FUNC_DEC
{ 
	public AST_TYPE typeTwo;
	public String nameTwo;

    public AST_FUNC_DEC_RULE_TWO (AST_TYPE type,String name, AST_TYPE typeTwo,  String nameTwo, AST_STMT_LIST stmtList) {
        super(type, name, stmtList);
        // Set a unique serial number/*  */
        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.typeTwo = typeTwo;
        this.nameTwo = nameTwo;
        System.out.format("====================== funcDec -> type ID LPAREN type ID RPAREN LBRACE STMT_LIST RBRACE \n");
    }
}