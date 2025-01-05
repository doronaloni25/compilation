package AST;
import TYPES.TYPE;
import SymbolTable.SYMBOL_TABLE;
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
    @Override
    public TYPE SemantMe() 
    {
        // we work on "class.function() or class.function(exp, exp, exp)"

        TYPE varType = v.SemantMe();
        TYPE nameType = name.SemantMe();
        //check if the variable is a class and if the name is not null
        if(varType == null || !(varType.isClass())|| nameType == null)
        {
            //TODO: return exception with line number
        }
        //check if the class has a method with the given name
        boolean found_function = varType.functionInClass(nameType.name);
        if((!found_function))
        {
            //  TODO: return exception with line number
        }
        if(exp != null)
        {
            //use irit function to check the params!!
        }
            
    }
	
}