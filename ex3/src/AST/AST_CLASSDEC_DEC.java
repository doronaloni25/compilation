package AST;

public class AST_CLASSDEC_DEC extends AST_DEC
{
    public AST_CLASS_DEC_ONE classDec;
    public AST_CLASSDEC_DEC (AST_CLASS_DEC_ONE classDec) {

        this.classDec = classDec;
    }

    public TYPE semantMe() {
        return classDec.semantMe();
    }
}