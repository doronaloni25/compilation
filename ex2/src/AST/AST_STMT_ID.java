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
            System.out.format("====================== exp ->  ID LPARAN RPARAN SEMICOLON\n");
        }
        else if(expList == null)
        {
		    System.out.format("====================== exp ->  ID LPAREN exp RPAREN SEMICOLON\n");
        }
        else
        {
           System.out.format("====================== exp ->  ID LPAREN exp commaExpList RPAREN SEMICOLON\n");
        }

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
        this.name = name;
        this.exp = exp;
        this.expList = expList;
    }
}