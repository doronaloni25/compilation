package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils.HelperUtils;
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
   
     if(SYMBOL_TABLE.getInstance().inFunction != null)
    {
        HelperUtils.printError(line);
    }
    TYPE returnType = type.SemantMe();
    if(returnType == null)
    {
        HelperUtils.printError(line);
    }

    TYPE_CLASS_DEC classDec = SYMBOL_TABLE.getInstance.inClass();
    TYPE_FUNCTION function = new TYPE_FUNCTION(returnType, name, null);
    SYMNOL_TABLE.getInstance().enter(name, function);
    //function declaration inside a class
    if(classDec!=null)
    {
        
        
        SYMBOL_TABLE.getInstance.beginScope();
        SYMBOL_TABLE.getInstance().inFunction = function;
        AST_COMMA_TYPE_ID commaTypeId  = new AST_COMMA_TYPE_ID(typeTwo, nameTwo);
        AST_COMMA_TYPE_ID_LIST commaTypeIdList = new AST_COMMA_TYPE_ID_LIST(commaTypeId, null);
        //asume semantMe on commaTypeidList returns TYPE_LIST contains only the types, and doesnt check if they are already in the symbol table
        TYPE_LIST paramList = commaTypeIdList.SemantMe();
        function.paramList = paramList;
        TYPE functionReturnType = stmtList.SemantMe();
        if(HelperUtils.isInhiritedFromOrNil(functionReturnType, returnType) == false)
        {
            HelperUtils.printError(line);
        }
        
        classDec.addFunction(function);
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().inFunction = null;
       
        return function;
    }
    //function declaration in a global scope
    else{
            if (SYMBOL_TABLE.getInstance().find(name) != null)
            {
                HelperUtils.printError(line);
            }
            if (SYMBOL_TABLE.getInstance().isGlobalScope() == false)
            {
                HelperUtils.printError(line);
            }
            if (name.equals("PrintInt") || name.equals("PrintString"))
                {
                    HelperUtils.printError(line);
                }
            
            SYMBOL_TABLE.getInstance.beginScope();
            SYMBOL_TABLE.getInstance().inFunction = function;
            AST_COMMA_TYPE_ID commaTypeId  = new AST_COMMA_TYPE_ID(typeTwo, nameTwo);
            AST_COMMA_TYPE_ID_LIST commaTypeIdList = new AST_COMMA_TYPE_ID_LIST(commaTypeId, null);
            //asume semantMe on commaTypeidList returns TYPE_LIST contains only the types, and doesnt check if they are already in the symbol table
            TYPE_LIST paramList = commaTypeIdList.SemantMe();
            function.paramList = paramList;

            TYPE functionReturnType = stmtList.SemantMe();
            if(HelperUtils.isInhiritedFromOrNil(functionReturnType, returnType) == false)
                {
                    HelperUtils.printError(line);
                }
            SYMBOL_TABLE.getInstance().endScope();
            SYMBOL_TABLE.getInstance().inFunction = null;
            return function;
        }
    }
}
