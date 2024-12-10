package AST;

public class AST_VAR_DEC_NEW_EXP extends AST_VAR_DEC 
{
    
	public AST_TYPE type;
	public String name;
    public AST_NEW_EXP neExp;


    public AST_VAR_DEC_NEW_EXP (AST_TYPE type, String name, AST_NEW_EXP neExp) {
        super(type, name);
        
        this.neExp = neExp;
        System.out.format("====================== varDec -> type ID ASSIGN newEXP SEMICOLON \n");
    }
}

   