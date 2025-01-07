package AST;

import TYPES.TYPE;
import TYPES.TYPE_LIST;

public abstract class AST_LIST extends AST_Node 
{
    public abstract AST_Node getHead();

    public abstract AST_LIST getTail();

    @Override
    public TYPE_LIST SemantMe()
    {
        AST_Node head = getHead();
        AST_LIST tail = getTail();
        TYPE head_type = null;
        TYPE_LIST tail_type = null;
        if(head != null)
            head_type = head.SemantMe();
        if(tail != null)
            tail_type = tail.SemantMe();

        return new TYPE_LIST(head_type, tail_type);
    }
    
}
