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
        if( exp == null)
        {
            System.out.format("====================== stmt -> var DOT ID LPARAN RPARAN SEMICOLON\n");
        }
        else if(expList == null)
        {
		    System.out.format("====================== stmt -> var DOT ID LPAREN exp RPAREN SEMICOLON\n");
        }
        else
        {
           System.out.format("====================== stmt -> var DOT ID LPAREN exp commaExpList  RPAREN SEMICOLON\n");
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