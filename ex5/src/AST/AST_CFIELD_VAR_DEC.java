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
        if (varDec instanceof AST_VAR_DEC_EXP){
            if (((AST_VAR_DEC_EXP)varDec).exp instanceof AST_EXP_STRING){
                AST_EXP_STRING strExp = (AST_EXP_STRING)((AST_VAR_DEC_EXP)varDec).exp;
                IR.getInstance().Add_IRcommand(new IRcommandConstString(strExp.s, strExp.stringLabel));
            }
        }
        else{
            return varDec.IRme();
        }
        return null;
    }
}