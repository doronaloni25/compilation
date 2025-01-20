package AST;
import TYPES.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import HelperFunctions.HelperFunctions;
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

    public TYPE SemantMe(){
        // can only declare array in global scope
        if (!SYMBOL_TABLE.getInstance().isGlobalScope()){
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }

        // check if name is occupied
        TYPE checkNameType = SYMBOL_TABLE.getInstance().find(name);
        if (checkNameType != null){
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        
        TYPE t = type.SemantMe();
        if (t == null){
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        TYPE_ARRAY newArray = new TYPE_ARRAY(t, name); 
        SYMBOL_TABLE.getInstance().enter(name, newArray);
        return newArray;
    }
}
