package AST;
import TYPES.*;
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

    //TODO- implement semantme, insert to symbol table without checking if the name is already in the table, 
    //since these are arguments to function declraion, but the type does exist in the symbol table/
    //return TYPLE_LIST of inly types' and insert the names of the arguments to the symbol table

@Override
public TYPE semantMe()
{
    TYPE t = type.SemantMe();
    if(t == null)
    {
        //TODO- throw exeption
    }
    SYMBOL_TABLE.getInstance().enter(name, t);
    return t;
}
}