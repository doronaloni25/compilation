package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;

public class AST_EXP_NIL extends AST_EXP {
    public AST_EXP_NIL() 
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        System.out.format("exp -> NIL\n");
    }

    public TYPE SemantMe()
    {
        return TYPE_NIL.getInstance();
    }
    //TODO: make sure it's correct???
    public TEMP IRme(){
    
        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR.getInstance().Add_IRcommand(new IRcommandConstInt(t,0));
        return t;
    }
}