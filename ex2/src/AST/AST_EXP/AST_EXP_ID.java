package AST;

public class AST_EXP_ID extends AST_EXP
{
    String name;
    AST_EXP exp;
    AST_COMMA_EXP_LIST expList;
    public AST_EXP_VAR_ID(String name,  AST_EXP exp, AST_COMMA_EXP_LIST expList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if( exp = null)
        {
            System.out.format("====================== exp ->  ID( %s) LPARAN RPARAN\n", name);
        }
        else if(expList == null)
        {
		    System.out.format("====================== exp ->  ID( %s ) LPAREN exp(%s) RPAREN\n", name, exp);
        }
        else
        {
           System.out.format("====================== exp ->  ID( %s ) LPAREN exp(%s) commaExpList (%s) RPAREN\n",  name, exp, expList);
        }

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
        this.name = name;
        this.exp = exp;
        this.expList = expList;
    }
}