package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
public class AST_FUNCDEC_DEC extends AST_DEC
{
    public AST_FUNC_DEC funcDec;
    public AST_FUNCDEC_DEC (AST_FUNC_DEC funcDec) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.funcDec = funcDec;
    }

    public TYPE SemantMe() {
        return funcDec.SemantMe();
    }
    public TEMP IRme()
    {
        funcDec.IRme();
        return null;
    }
}