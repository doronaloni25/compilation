package AST;

public class AST_VARDEC_DEC extends AST_DEC
{
    public AST_VAR_DEC varDec;
    public AST_VARDEC_DEC (AST_VAR_DEC varDec) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.varDec = varDec;
        System.out.println("====================== DEC -> varDec");
    }

    public TYPE semantMe() {
        return varDec.semantMe();
    }
}