package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public abstract class AST_EXP extends AST_Node
{
    public TYPE SemantMe()
    {
        return null;
    }

    public TEMP IRme(){
        return null;
    }
}