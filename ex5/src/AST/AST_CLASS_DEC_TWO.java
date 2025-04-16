package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public class AST_CLASS_DEC_TWO extends AST_CLASS_DEC_ONE 
{
    
	public String nameTwo;


    public AST_CLASS_DEC_TWO ( String name, String nameTwo, AST_CFIELD_LIST cFieldList) 
    {
        super(name, cFieldList);

        this.nameTwo = nameTwo;
        System.out.format("====================== ClassDec -> CLASS ID EXTENDS ID2 LBRACE cFieldList RBRACE \n");
    }
    @Override
     public TYPE SemantMe() 
     {
        TYPE isFirstTime = SYMBOL_TABLE.getInstance().find(name);
        TYPE fatherT = SYMBOL_TABLE.getInstance().find(nameTwo);
        // checks this is the first decleration of the class, in the global scope and we have a father (thats a class dec)
        if(isFirstTime != null || fatherT == null || !SYMBOL_TABLE.getInstance().isGlobalScope() || !fatherT.isClassDec())
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        TYPE_CLASS_DEC fatherType = (TYPE_CLASS_DEC)fatherT;
        //give the son all father parameters
        TYPE_CLASS_DEC classType = new TYPE_CLASS_DEC(fatherType, name);
        SYMBOL_TABLE.getInstance().inClass = classType;
        classType.function_list = fatherType.function_list;
        HelperFunctions.setFunctionListInherited(classType.function_list);
        classType.data_members = fatherType.data_members;
        SYMBOL_TABLE.getInstance().enter(name, classType);
        SYMBOL_TABLE.getInstance().beginScope();
        // After opening the scope, add ancestor's fields to symbol table
        HelperFunctions.addInheritedVarsToSymbolTable(classType.data_members, classType.function_list);
        // the cFieldList will recursivly insert the fiels into the Symbol table
        cFieldList.SemantMe();
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().inClass = null;
        return classType;
     }
     //IRme is implemnte in the father class
}