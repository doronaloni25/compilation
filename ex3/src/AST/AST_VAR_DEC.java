package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
public class AST_VAR_DEC extends AST_DEC 
{
    
	public AST_TYPE type;
	public String name;


    public AST_VAR_DEC (AST_TYPE type, String name) 
    {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        this.name = name;

        System.out.format("====================== varDec -> type ID SEMICOLON \n");
    }
    @Override
    public TYPE SemantMe() 
    {
        TYPE currType = SYMBOL_TABLE.getInstance().findWithinScope(name);
        if(currType != null)
        {
            System.out.println("Error");
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        TYPE t = type.SemantMe();
        if (t == null)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        SYMBOL_TABLE.getInstance().enter(name, t);
        // check if im in class scope, and if so add to the class fields
        TYPE_CLASS_DEC classType = SYMBOL_TABLE.getInstance().inClass;
        if(classType != null)
        {
            // This also checks that the field was not declared before (in any ancestor of the class)
            classType.addField(name, t);
        }
        return t;
    }
}

	
	
  


