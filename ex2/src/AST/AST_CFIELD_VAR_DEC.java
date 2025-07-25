package AST;

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
}