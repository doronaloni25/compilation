package AST;

public class AST_EXP_NIL extends AST_EXP {

    public AST_EXP_NIL() 
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        System.out.format("exp -> NIL\n");
    }
}