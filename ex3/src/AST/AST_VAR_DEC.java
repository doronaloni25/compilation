package AST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
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
            //TODO: return exception with line number
        }
        TYPE t = type.SemantMe();
        SYMBOL_TABLE.getInstance().enter(name, t);
        return t;
    }
}

	
	
  


