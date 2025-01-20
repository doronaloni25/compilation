package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
public class AST_CLASSDEC_DEC extends AST_DEC
{
    public AST_CLASS_DEC_ONE classDec;
    public AST_CLASSDEC_DEC (AST_CLASS_DEC_ONE classDec) {

        this.classDec = classDec;
    }

    public TYPE SemantMe() {
        return classDec.SemantMe();
    }
    //TODO: check if this is correct
    public TEMP IRme()
    {
        classDec.IRme();
        return null;
    }
}