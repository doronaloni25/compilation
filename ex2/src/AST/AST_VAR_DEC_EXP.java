package AST;

public class AST_VAR_DEC_EXP extends AST_VAR_DEC 
{
    
	public AST_TYPE type;
	public String name;
    public AST_EXP exp;


    public AST_VAR_DEC_EXP (AST_TYPE type, String name, AST_EXP exp) {
        super(type, name);

        this.exp = exp;
        System.out.format("====================== varDec -> type ID ASSIGN exp SEMICOLON \n");
    }
}
  


