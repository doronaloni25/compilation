package AST;

public class AST_FUNC_DEC_RULE_THREE extends AST_FUNC_DEC
{ 
    public AST_TYPE typeTwo;
    public String nameTwo;
    public AST_COMMA_TYPE_ID_LIST commaTypeIdList;
    public AST_FUNC_DEC_RULE_THREE (AST_TYPE type,String name, AST_TYPE typeTwo,  String nameTwo, AST_COMMA_TYPE_ID_LIST commaTypeIdList,  AST_STMT_LIST stmtList) {
        super(type, name, stmtList);
        // Set a unique serial number/*  */
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.typeTwo = typeTwo;
        this.nameTwo = nameTwo;
        this.commaTypeIdList = commaTypeIdList;

        System.out.format("====================== funcDec -> type ID LPAREN RPAREN LBRACE STMT_LIST RBRACE \n");
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
    //name2,type2,commaTypeIdList = the parametersList.
    //assume that after semantme on paramList, paramList is already in the symbol table
    AST_COMMA_TYPE_ID_LIST paramList = new AST_COMMA_TYPE_ID_LIST(typeTwo, commaTypeIdList);
    TYPE_LIST paramListType= paramList.SemantMe();
    stmtList.SemantMe();
    SYMBOL_TABLE.getInstance().endScope();
    //enter the function to the symbol table, afrter stmstList is done
    TYPE_FUNCTION function = new TYPE_FUNCTION(funcReturnType, name, paramList);
    SYMBOL_TABLE.getInstance().enter(name, function);
    return function;
    

 }
}