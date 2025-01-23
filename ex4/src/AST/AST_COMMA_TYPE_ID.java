package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public class AST_COMMA_TYPE_ID extends AST_Node {
    
    public AST_TYPE type; 
    public String name;

    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
    public AST_COMMA_TYPE_ID(AST_TYPE type,  String name) {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();
        
        System.out.format("----------AST_COMMA_TYPE_ID -> COMMA type ID\n");
        
        this.type = type;
        this.name = name;
    }

    //this method used in funtion declaration to add the function parameters to the symbol table
@Override
public TYPE SemantMe()
{
    TYPE t = type.SemantMe();
    if(t == null)
    {
        HelperFunctions.printError(line, this.getClass().getSimpleName());
    }
    SYMBOL_TABLE.getInstance().enter(name, t);
    return t;
}
}