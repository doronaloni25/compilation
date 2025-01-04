package AST;

public class AST_FUNC_DEC_RULE_TWO extends AST_FUNC_DEC
{ 
	public AST_TYPE typeTwo;
	public String nameTwo;

    public AST_FUNC_DEC_RULE_TWO (AST_TYPE type,String name, AST_TYPE typeTwo,  String nameTwo, AST_STMT_LIST stmtList) {
        super(type, name, stmtList);
        // Set a unique serial number/*  */
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.funcReturnType = null;
        this.typeTwo = typeTwo;
        this.nameTwo = nameTwo;
        System.out.format("====================== funcDec -> type ID LPAREN type ID RPAREN LBRACE STMT_LIST RBRACE \n");
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
    //name2,type2 = the parametersList
  
    TYPE_LIST paramList = type2.SemantMe();
    //one paramter
    stmtList.SemantMe();
    //TODO- check if name 2 is already declared before entering to symbol table
    SYMBOL_TABLE.getInstance().enter(name2, type2);
    TYPE_FUNCTION function = new TYPE_FUNCTION(funcReturnType, name, paramList);
    SYMBOL_TABLE.getInstance().endScope();
    SYMBOL_TABLE.getInstance().enter(name, function);
    return function;
    

 }

//TODO - implement classSemantMe

}
