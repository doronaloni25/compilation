package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
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
    public TEMP IRme()
    {
        return varDec.IRme();
    }
}