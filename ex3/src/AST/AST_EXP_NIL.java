package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils.HelperUtils;

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
}