package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public class AST_ARRAY_TYPE_DEF_DEC extends AST_DEC 
{
    AST_ARRAY_TYPE_DEF arrayTypedef;



    public AST_ARRAY_TYPE_DEF_DEC ( AST_ARRAY_TYPE_DEF arrayTypedef) {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.arrayTypedef = arrayTypedef;
        System.out.print("====================== dec -> arrayTypedef \n");
    }

    public TYPE SemantMe() {
        return arrayTypedef.SemantMe();
    }
    //TODO: check if this is correct
    public TEMP IRme()
    {
        arrayTypedef.IRme();
        return null;
    }
}

  


