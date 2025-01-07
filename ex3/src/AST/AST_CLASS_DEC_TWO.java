package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils.HelperUtils;
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
        
        SYMBOL_TABLE.getInstance().inClass = classType;
        TYPE isFirstTime = SYMBOL_TABLE.getInstance().find(name);
        TYPE fatherType = SYMBOL_TABLE.getInstance().find(name2);
        // checks this is the first decleration of the class, in the global scope and we have a father (thats a class dec)
        if(isFirstTime != null || fatherType == null || !SYMBOL_TABLE.getInstance().isGlobalScope() || !fatherType.isClassDec())
        {
            //TODO: return exception with line number
        }
        //give the son all father parameters
        TYPE_CLASS_DEC classType = new TYPE_CLASS_DEC( (TYPE_CLASS_DEC)fatherType, name);
        classType.function_list = fatherType.function_list;
        classType.data_members = fatherType.data_members;
        SYMBOL_TABLE.getInstance().enter(name, classType);
        SYMBOL_TABLE.getInstance().beginScope();
        // the cFieldList will recursivly insert the fiels into the Symbol table
        cFieldList.SemantMe();
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().inClass = null;
        return classType;
     }
}