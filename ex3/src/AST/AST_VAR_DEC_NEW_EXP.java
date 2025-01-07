package AST;
import TYPES.*;
import HelperUtils.HelperUtils;
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

    @Override
    public TYPE SemantMe() 
    {
        // check if we are inside class, since we cant assign non-constants to variables inside of a class declaration
        TYPE_CLASS_DEC classType = SYMBOL_TABLE.getInstance().inClass;
        if(classType != null)
        {
            // TODO: return exception with line number
        }
        TYPE newExpType = neExp.SemantMe();
        if (newExpType == null)
        {
            // TODO: return exception with line number
        }
        TYPE currType = super.SemantMe();
        if (HelperUtils.isInhiritedFromOrNil(newExpType, currType)) {
            return currType;
        }
        else {
            // TODO: return exception with line number
        }
    }
}

   