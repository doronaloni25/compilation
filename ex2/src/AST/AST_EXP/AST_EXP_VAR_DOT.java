package AST;

public class AST_EXP_VAR_DOT extends AST_EXP
{
    AST_VAR v;
    String name;
    AST_EXP exp;
    AST_COMMA_EXP_LIST expList;
    public AST_EXP_VAR_DOT(AST_VAR v,String name,  AST_EXP exp, AST_COMMA_EXP_LIST expList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if( exp = null)
        {
            System.out.format("====================== exp -> var(%s) DOT ID( %s) LPARAN RPARAN\n", v, name);
        }
        else if(expList == null)
        {
		    System.out.format("====================== exp -> var(%s) DOT ID( %s ) LPAREN exp(%s) RPAREN\n", var, name, exp);
        }
        else
        {
           System.out.format("====================== exp -> var(%s) DOT ID( %s ) LPAREN exp(%s) commaExpList (%s) RPAREN\n", var, name, exp, expList);
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