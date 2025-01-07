package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils.HelperUtils;
public class AST_BINOP extends AST_Node{
    public int op;
    // PLUS = 0
    // MINUS = 1
    // TIMES = 2
    // DIVIDE = 3
    // LT = 4
    // GT = 5
    // EQ = 6
    public AST_BINOP(int op)
    {
        this.op = op;
    }
    public TYPE SemantMe()
    {
        return null;
    }
}
