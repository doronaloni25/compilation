package AST;

public class AST_BINOP extends AST_Node{
    public int op;
    public AST_BINOP(int op)
    {
        this.op = op;
    }
    public TYPE SemantMe()
    {
        return null;
    }
}
