package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
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
        //System.out.println("rule 3 start at line " + line);
        //System.out.println("my name is " + name);
        if(SYMBOL_TABLE.getInstance().inFunction!=null)
        {
            //System.out.println("error rec function");
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        TYPE returnType = type.SemantMe();
        if(returnType == null)
        {
            //System.out.println("error return type no good");
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
            AST_COMMA_TYPE_ID_LIST commaTypeIdListFunc = new AST_COMMA_TYPE_ID_LIST(commaTypeId, commaTypeIdList);
            //asume SemantMe on commaTypeidList returns TYPE_LIST contains only the types, and doesnt check if they are already in the symbol table
            TYPE_LIST paramList = commaTypeIdListFunc.SemantMe();
            function.params = paramList;
                HelperFunctions.set_func_or_method_argument_list_data(commaTypeIdListFunc,false, true);
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
                //System.out.println("same name exists");
                HelperFunctions.printError(line, this.getClass().getSimpleName());
            }
            SYMBOL_TABLE.getInstance().enter(name, function);
            if (SYMBOL_TABLE.getInstance().isGlobalScope() == false)
            {
                //System.out.println("not global");
                HelperFunctions.printError(line, this.getClass().getSimpleName());
            }
            if (name.equals("PrintInt") || name.equals("PrintString"))
            {
                HelperFunctions.printError(line, this.getClass().getSimpleName());
            }

            SYMBOL_TABLE.getInstance().beginScope();
            SYMBOL_TABLE.getInstance().inFunction = function;
            AST_COMMA_TYPE_ID commaTypeId  = new AST_COMMA_TYPE_ID(typeTwo, nameTwo);
            AST_COMMA_TYPE_ID_LIST commaTypeIdListFunc = new AST_COMMA_TYPE_ID_LIST(commaTypeId, commaTypeIdList);
            //asume SemantMe on commaTypeidList returns TYPE_LIST contains only the types, and doesnt check if they are already in the symbol table
            TYPE_LIST paramList = commaTypeIdListFunc.SemantMe();
            function.params = paramList;
              HelperFunctions.set_func_or_method_argument_list_data(commaTypeIdListFunc,true, false);
            // will take care of return type matching
            stmtList.SemantMe();
            SYMBOL_TABLE.getInstance().endScope();
            SYMBOL_TABLE.getInstance().inFunction = null;
            //System.out.println("rule 3 done");
            this.localVariablesCount = function.localVariablesCount;
            return function;
        }
    }
     // IRme implemented in AST_FUNC_DEC
}