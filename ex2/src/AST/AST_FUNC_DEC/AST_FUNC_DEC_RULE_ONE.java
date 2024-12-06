package AST;

public class AST_FUNC_DEC_RULE_ONE extends AST_FUNC_DEC
{ 
	public AST_TYPE typeTwo;
	public String nameTwo;
    public AST_COMMA_TYPE_ID_LIST commaTypeIdList;

    public AST_FUNC_DEC_RULE_ONE (AST_TYPE type,String name, AST_TYPE typeTwo,  String nameTwo, AST_COMMA_TYPE_ID_LIST commaTypeIdList, AST_STMT_LIST stmtList) {
        super(type, name, stmtList);
        // Set a unique serial number/*  */
        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.typeTwo = typeTwo;
        this.nameTwo = nameTwo;
        this.commaTypeIdList = commaTypeIdList;
        System.out.format("====================== funcDec -> type( %s ) ID( %s ) LPAREN type( %s ) 
        ID( %s ) COMMA_TYPE_ID_LIST( %s ) RPAREN LBRACE STMT_LIST( %s )RBRACE \n", type, name, typeTwo, name2, commaTypeIdList, stmt_list);
    }
}