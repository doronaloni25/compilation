package AST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
public class AST_CLASS_DEC_ONE extends AST_DEC 
{
    
	public String name;
    public AST_CFIELD_LIST cFieldList;


    public AST_CLASS_DEC_ONE ( String name, AST_CFIELD_LIST cFieldList) 
    {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.name = name;
        this.cFieldList = cFieldList;
        System.out.format("====================== ClassDec -> CLASS ID LBRACE cFieldList RBRACE \n");
    }
    @Override
    public TYPE SemantMe() 
    {
        //null is passed as the father of the class as we do not extend
        TYPE_CLASS classType = new TYPE_CLASS(null, name);
        SYMBOL_TABLE.getInstance().inClass = classType;
        //checks if the class was decleared before
        TYPE isFirstTime = SYMBOL_TABLE.getInstance().find(name);
        if(isFirstTime != null || !SYMBOL_TABLE.getInstance().isGlobalScope())
        {
            //TODO: return exception with line number
        }
        SYMBOL_TABLE.getInstance().enter(name, classType);

        SYMBOL_TABLE.getInstance().beginScope();
        // the cFieldList will recursivly insert the fiels into the Symbol table
        cFieldList.SemantMe();
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().inClass = false;
    }
}

  


