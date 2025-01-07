package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils.HelperUtils;
public class AST_CFIELD_FUNC_DEC extends AST_CFIELD
{
    AST_FUNC_DEC funcDec;

    public AST_CFIELD_FUNC_DEC(AST_FUNC_DEC funcDec)
    {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== cField -> funcDec \n");
        this.funcDec = funcDec;
    }
    @Override
    public TYPE SemantMe()
    {
        return funcDec.SemantMe();
    }

}