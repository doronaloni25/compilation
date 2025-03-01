package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
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

@Override
 public TYPE SemantMe()
 {
   
     if(SYMBOL_TABLE.getInstance().inFunction != null)
    {
        HelperFunctions.printError(line, this.getClass().getSimpleName());
    }
    TYPE returnType = type.SemantMe();
    if(returnType == null)
    {
        HelperFunctions.printError(line, this.getClass().getSimpleName());
    }

    TYPE_CLASS_DEC classDec = SYMBOL_TABLE.getInstance().inClass;
    TYPE_FUNCTION function = new TYPE_FUNCTION(returnType, name, new TYPE_LIST(null, null));
    //function declaration inside a class
    if(classDec!=null)
    {
        this.classDec = classDec;
        TYPE funcCheck = SYMBOL_TABLE.getInstance().findWithinScope(name);
        SYMBOL_TABLE.getInstance().enter(name, function);
        SYMBOL_TABLE.getInstance().beginScope();
        SYMBOL_TABLE.getInstance().inFunction = function;
        AST_COMMA_TYPE_ID commaTypeId  = new AST_COMMA_TYPE_ID(typeTwo, nameTwo);
        AST_COMMA_TYPE_ID_LIST commaTypeIdList = new AST_COMMA_TYPE_ID_LIST(commaTypeId, null);
        //asume SemantMe on commaTypeidList returns TYPE_LIST contains only the types, and doesnt check if they are already in the symbol table
        TYPE_LIST paramList = commaTypeIdList.SemantMe();
        function.params = paramList;
        HelperFunctions.checkValidMethod(function, funcCheck, classDec, line, this.getClass().getSimpleName());
        // will take care of return type matching
        stmtList.SemantMe();
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().inFunction = null;
        this.localVariablesCount = function.localVariablesCount;
        return function;
    }
    //function declaration in a global scope
    else{
        if (SYMBOL_TABLE.getInstance().find(name) != null)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        SYMBOL_TABLE.getInstance().enter(name, function);
        if (SYMBOL_TABLE.getInstance().isGlobalScope() == false)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        if (name.equals("PrintInt") || name.equals("PrintString"))
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        
        SYMBOL_TABLE.getInstance().beginScope();
        SYMBOL_TABLE.getInstance().inFunction = function;
        AST_COMMA_TYPE_ID commaTypeId  = new AST_COMMA_TYPE_ID(typeTwo, nameTwo);
        AST_COMMA_TYPE_ID_LIST commaTypeIdList = new AST_COMMA_TYPE_ID_LIST(commaTypeId, null);
        //asume SemantMe on commaTypeidList returns TYPE_LIST contains only the types, and doesnt check if they are already in the symbol table
        TYPE_LIST paramList = commaTypeIdList.SemantMe();
        function.params = paramList;
        // will take care of return type matching
        stmtList.SemantMe();
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().inFunction = null;
        this.localVariablesCount = function.localVariablesCount;
        return function;
        }
    }
    // IRme implemented at AST_FUNC_DEC
}
