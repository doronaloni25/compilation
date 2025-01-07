package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils.HelperUtils;
public class AST_CFIELD_VAR_DEC extends AST_CFIELD
{
    AST_VAR_DEC varDec;

    public AST_CFIELD_VAR_DEC(AST_VAR_DEC varDec)
    {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== cField -> varDec \n");
        this.varDec = varDec;
    }
    @Override
    public TYPE SemantMe()
    {
        return varDec.SemantMe();
    }
}