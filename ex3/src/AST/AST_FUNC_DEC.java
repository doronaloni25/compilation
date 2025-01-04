package AST;

public class AST_FUNC_DEC extends AST_DEC
{
	public AST_TYPE type;
	public String name;
    public AST_STMT_LIST stmt_list;

    public AST_FUNC_DEC (AST_TYPE type, String name, AST_STMT_LIST stmt_list) {

        this.type = type;
        this.name = name;
        this.stmt_list = stmt_list;
    }
   public TYPE SemantMe ()
   {
    return null;
   }
}