package AST;
import TYPES.*;

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
        if( exp == null)
        {
            System.out.format("====================== exp -> var DOT ID LPARAN RPARAN\n");
        }
        else if(expList == null)
        {
		    System.out.format("====================== exp -> var DOT ID LPAREN exp RPAREN\n");
        }
        else
        {
           System.out.format("====================== exp -> var DOT ID LPAREN exp commaExpList RPAREN\n");
        }

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
        this.name = name;
        this.exp = exp;
        this.expList = expList;
    }

    public TYPE SemantMe(){
        // TODO: implement
        if (exp == null){

        }
        else if (expList == null){

        }
        else{

        }
    }
}