package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public class AST_VAR_DEC extends AST_DEC 
{
    
	public AST_TYPE type;
	public String name;
    public String nameWithVarDecScope;

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
        //System.out.println("name vardec: " + name);
        TYPE currType = SYMBOL_TABLE.getInstance().findWithinScope(name);
        if(currType != null)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        TYPE t = type.SemantMe();
        if (t == null)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        SYMBOL_TABLE.getInstance().enter(name, t);
        // for IRme - it will return my scope, as we just inserted it to the symbol table
		nameWithVarDecScope = HelperFunctions.getVarNameWithDecScope(name);
        // check if im in class scope (but not in func), and if so add to the class fields
        TYPE_CLASS_DEC classType = SYMBOL_TABLE.getInstance().inClass;
        TYPE_FUNCTION funcType = SYMBOL_TABLE.getInstance().inFunction;
        // This basically means we are inside a class, but in its "main" scope - 
        // - not inside any funcs, if's, and while - since they are all inside funcdec
        if(classType != null && funcType == null)
        {
            // This also checks that the field was not declared before (in any ancestor of the class)
            classType.addField(name, t, line);
        }
        return t;
    }
    @Override
    public TEMP IRme()
    {
        IR.getInstance().Add_IRcommand(new IRcommand_Allocate(nameWithVarDecScope));
		return null;
    }
}

	
	
  


