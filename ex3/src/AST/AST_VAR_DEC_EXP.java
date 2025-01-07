package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
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

    @Override
    public TYPE SemantMe() 
    {
        // check if we are inside class, since we cant assign non-constants to variables inside of a class declaration
        TYPE_CLASS_DEC classType = SYMBOL_TABLE.getInstance().inClass;
        if(classType != null)
        {
            // check if exp is constant:
            if (!HelperFunctions.isConstant(exp)) {
                HelperFunctions.printError(line);
            }
        }
        TYPE expType = exp.SemantMe();
        if (expType == null)
        {
            HelperFunctions.printError(line);
        }
        // take care of SemantMe on "type ID", adds it to the symbol table and takes care of class field if relevant 
        TYPE currType = super.SemantMe();
        if (HelperFunctions.isInhiritedFromOrNil(expType, currType)) {
            return currType;
        }
        else {
            HelperFunctions.printError(line);
        }
        // unreachable code
        return currType;
    }   
}


