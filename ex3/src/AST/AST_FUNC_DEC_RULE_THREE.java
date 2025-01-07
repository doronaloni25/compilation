package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
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

    @Override
    public TYPE SemantMe()
    {
    
        if(SYMBOL_TABLE.getInstance().inFunction!=null)
        {
            HelperFunctions.printError(line);
        }
        TYPE returnType = type.SemantMe();
        if(returnType == null)
        {
            HelperFunctions.printError(line);
        }

        TYPE_CLASS_DEC classDec = SYMBOL_TABLE.getInstance().inClass;
        TYPE_FUNCTION function = new TYPE_FUNCTION(returnType, name, null);
        SYMBOL_TABLE.getInstance().enter(name, function);

        //function declaration inside a class
        if(classDec!=null)
        {
        
            SYMBOL_TABLE.getInstance().beginScope();
            SYMBOL_TABLE.getInstance().inFunction = function;
            AST_COMMA_TYPE_ID commaTypeId  = new AST_COMMA_TYPE_ID(typeTwo, nameTwo);
            AST_COMMA_TYPE_ID_LIST commaTypeIdListFunc = new AST_COMMA_TYPE_ID_LIST(commaTypeId, commaTypeIdList);
            //asume SemantMe on commaTypeidList returns TYPE_LIST contains only the types, and doesnt check if they are already in the symbol table
            TYPE_LIST paramList = commaTypeIdListFunc.SemantMe();
            function.params = paramList;
            TYPE functionReturnType = stmtList.SemantMe();
            if(HelperFunctions.isInhiritedFromOrNil(functionReturnType, returnType) == false)
            {
                HelperFunctions.printError(line);
            }
            
            classDec.addFunction(function, line);
            SYMBOL_TABLE.getInstance().endScope();
            SYMBOL_TABLE.getInstance().inFunction = null;
        
            return function;
        }
        //function declaration in a global scope
        else{
                if (SYMBOL_TABLE.getInstance().find(name) != null)
                {
                    HelperFunctions.printError(line);
                }
                if (SYMBOL_TABLE.getInstance().isGlobalScope() == false)
                {
                    HelperFunctions.printError(line);
                }
                if (name.equals("PrintInt") || name.equals("PrintString"))
                    {
                        HelperFunctions.printError(line);
                    }

                SYMBOL_TABLE.getInstance().beginScope();
                SYMBOL_TABLE.getInstance().inFunction = function;
                AST_COMMA_TYPE_ID commaTypeId  = new AST_COMMA_TYPE_ID(typeTwo, nameTwo);
                AST_COMMA_TYPE_ID_LIST commaTypeIdListFunc = new AST_COMMA_TYPE_ID_LIST(commaTypeId, commaTypeIdList);
                //asume SemantMe on commaTypeidList returns TYPE_LIST contains only the types, and doesnt check if they are already in the symbol table
                TYPE_LIST paramList = commaTypeIdListFunc.SemantMe();
                function.params = paramList;
                TYPE functionReturnType = stmtList.SemantMe();
                if(HelperFunctions.isInhiritedFromOrNil(functionReturnType, returnType) == false)
                    {
                        HelperFunctions.printError(line);
                    }
                SYMBOL_TABLE.getInstance().endScope();
                SYMBOL_TABLE.getInstance().inFunction = null;
                return function;
        }
    }
}