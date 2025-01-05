package AST;
import TYPES.*;
import SYMBOL_TABLE.SYMBOL_TABLE;

public class AST_ARRAY_TYPE_DEF extends AST_Node{
    public String name;
    public AST_TYPE type;

    public AST_ARRAY_TYPE_DEF(String name,  AST_TYPE type)
    {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.name = name;
        this.type = type;
        System.out.print("====================== AST_ARRAY_TYPE_DEF -> ARRAY ID EQ type LBRACK RBRACK SEMICOLON \n");
    }

    public TYPE semantMe(){
        // can only declare array in global scope
        if (!SYMBOL_TABLE.getInstance().isGlobalScope()){
            // TODO: throw exception
        }

        ARRAY_TYPE t = type.semantMe(); 
        
        // can only declare array of existing class
        if (SYMBOL_TABLE.getInstance().find(t) == null){
            // TODO: throw exception
        }
        
        SYMBOL_TABLE.getInstance().enter(name, t);
    }
}
