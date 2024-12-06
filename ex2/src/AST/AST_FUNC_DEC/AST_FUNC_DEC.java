package AST;

public abstract class AST_FUNC_DEC extends AST_Node
{
	public AST_TYPE type;
	public String name;
    public AST_STMT_LIST stmt_list;

    public AST_VAR_DEC (AST_TYPE type, String name, AST_STMT_LIST stmt_list) {

        this.type = type;
        this.name = name;
        this.stmt_list = stmt_list;
    }
}