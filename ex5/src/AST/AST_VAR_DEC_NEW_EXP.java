package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
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
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        TYPE newExpType = neExp.SemantMe();
        if (newExpType == null)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        TYPE currType = super.SemantMe();
        // if one of them is an array, both should be
        if (currType.isArray() || newExpType.isArray()){
            if (!(currType.isArray() && newExpType.isArray())){
                HelperFunctions.printError(line, this.getClass().getSimpleName());
            }
            TYPE_ARRAY currTypeArr = (TYPE_ARRAY)currType;
            TYPE_ARRAY newExpTypeArr = (TYPE_ARRAY)newExpType;
            //System.out.println("type of currtypearr " + currTypeArr.type.name);
            //System.out.println("type of currtypearr " + newExpTypeArr.type.name);
            if (currTypeArr.type.name.equals(newExpTypeArr.type.name)){
                return currTypeArr;
            }
            else{
                HelperFunctions.printError(line, this.getClass().getSimpleName());
            }          
        }
        if (HelperFunctions.isInhiritedFromOrNil(newExpType, currType)) {
            return currType;
        }
        else {
            //System.out.println(newExpType.name + " is not inherited from " + currType.name);
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        // unreachable code
        return currType;
    }
    public TEMP IRme()
    {
        //TODO: for next exercise
        return null;
    }
}

   