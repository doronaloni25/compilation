package AST;

public class AST_STMT_VAR_DOT extends AST_STMT
{
    AST_VAR v;
    String name;
    AST_EXP exp;
    AST_COMMA_EXP_LIST expList;
    public AST_STMT_VAR_DOT(AST_VAR v,String name,  AST_EXP exp, AST_COMMA_EXP_LIST expList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if( exp = null)
        {
            System.out.format("====================== stmt -> var(%s) DOT ID( %s) LPARAN RPARAN SEMICOLON\n", v, name);
        }
        else if(expList == null)
        {
		    System.out.format("====================== stmt -> var(%s) DOT ID( %s ) LPAREN exp(%s) RPAREN SEMICOLON\n", var, name, exp);
        }
        else
        {
           System.out.format("====================== stmt -> var(%s) DOT ID( %s ) LPAREN exp(%s) commaExpList (%s) RPAREN SEMICOLON\n", var, name, exp, expList);
        }

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
        this.name = name;
        this.exp = exp;
        this.expList = expList;
    }
}