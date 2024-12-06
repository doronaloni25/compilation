package AST;

public class AST_STMT_ID extends AST_STMT
{
    String name;
    AST_EXP exp;
    AST_COMMA_EXP_LIST expList;
    public AST_STMT_ID(String name,  AST_EXP exp, AST_COMMA_EXP_LIST expList)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if( exp == null)
        {
            System.out.format("====================== exp ->  ID( %s) LPARAN RPARAN SEMICOLON\n", name);
        }
        else if(expList == null)
        {
		    System.out.format("====================== exp ->  ID( %s ) LPAREN exp(%s) RPAREN SEMICOLON\n", name, exp);
        }
        else
        {
           System.out.format("====================== exp ->  ID( %s ) LPAREN exp(%s) commaExpList (%s) RPAREN SEMICOLON\n",  name, exp, expList);
        }

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
        this.name = name;
        this.exp = exp;
        this.expList = expList;
    }
}