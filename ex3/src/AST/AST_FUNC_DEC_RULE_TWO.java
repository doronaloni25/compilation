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
   
    if (name.equals("PrintInt") || name.equals("PrintString"))
      {
            //TODO- throw exeption
      }

    TYPE returnType = type.SemantMe();
    if(returnType == null)
    {
        //TODO- throw exeption
    }

    TYPE_CLASS_DEC classDec = SYMBOL_TABLE.getInstance.inClass();
    //function declaration inside a class
    if(classDec!=null)
    {
        
        SYMBOL_TABLE.getInstance.beginScope();
        AST_COMMA_TYPE_ID commaTypeId  = new AST_COMMA_TYPE_ID(typeTwo, nameTwo);
        AST_COMMA_TYPE_ID_LIST commaTypeIdList = new AST_COMMA_TYPE_ID_LIST(commaTypeId, null);
        //asume semantMe on commaTypeidList returns TYPE_LIST contains only the types, and doesnt check if they are already in the symbol table
        TYPE_LIST paramList = commaTypeIdList.SemantMe();
        TYPE_FUNCTION function = new TYPE_FUNCTION(returnType, name, paramList);
        SYMBOL_TABLE.getInstance().inFunction = function;

        TYPE functionReturnType = stmtList.SemantMe();
        if(HelperUtils.isInhiritedFromOrNil(functionReturnType, returnType) == false)
        {
            //TODO- throw exeption
        }
        
        classDec.addFunction(function);
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().inFunction = null;
        SYMNOL_TABLE.getInstance().enter(name, function);
        return function;
    }
    //function declaration in a global scope
    else{
            if (SYMBOL_TABLE.getInstance().find(name) != null)
            {
                //TODO - throw exception
            }
            if (SYMBOL_TABLE.getInstance().isGlobalScope() == false)
            {
                //TODO- throw exeption
            }
            SYMBOL_TABLE.getInstance.beginScope();
            AST_COMMA_TYPE_ID commaTypeId  = new AST_COMMA_TYPE_ID(typeTwo, nameTwo);
            AST_COMMA_TYPE_ID_LIST commaTypeIdList = new AST_COMMA_TYPE_ID_LIST(commaTypeId, null);
            //asume semantMe on commaTypeidList returns TYPE_LIST contains only the types, and doesnt check if they are already in the symbol table
            TYPE_LIST paramList = commaTypeIdList.SemantMe();
            TYPE_FUNCTION function = new TYPE_FUNCTION(returnType, name, paramList);
            SYMBOL_TABLE.getInstance().inFunction = function;

            TYPE functionReturnType = stmtList.SemantMe();
            if(HelperUtils.isInhiritedFromOrNil(functionReturnType, returnType) == false)
                {
                    //TODO- throw exeption
                }
            SYMBOL_TABLE.getInstance().endScope();
            SYMBOL_TABLE.getInstance().inFunction = null;
            SYMBOL_TABLE.getInstance().enter(name, function);
            return function;
        }
    }
}
