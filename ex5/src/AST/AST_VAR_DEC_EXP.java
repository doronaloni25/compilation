package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public class AST_VAR_DEC_EXP extends AST_VAR_DEC 
{
    
    public AST_EXP exp;
    public boolean isString = false;

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
        TYPE_FUNCTION funcType = SYMBOL_TABLE.getInstance().inFunction;
        if(classType != null && funcType == null)
        {
            // check if exp is constant:
            if (!HelperFunctions.isConstant(exp)) {
                System.out.println("check if exp is constant error");
                HelperFunctions.printError(line, this.getClass().getSimpleName());
            }
        }
        TYPE expType = exp.SemantMe(); 
        if (expType == null)
        {
            System.out.println("check if exptype is null error");
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        if(expType instanceof TYPE_STRING)
        {
            isString = true;
        }
        // take care of SemantMe on "type ID", adds it to the symbol table and takes care of class field if relevant 
        TYPE currType = super.SemantMe();
        // For IRme, super takes care of saving name with scope index    
        if (HelperFunctions.isInhiritedFromOrNil(expType, currType)) {
            return currType;
        }
        else {
            System.out.println("check if inherited or nil error");
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        // unreachable code
        return currType;
    }   
    public TEMP IRme()
    {
        if(this.isGlobal)
        {
            IR.getInstance().Add_IRcommand(new IRcommand_Allocate(this.name, this.isGlobal,this.exp, this.isString));
        }
        else
        {
            IR.getInstance().Add_IRcommand(new IRcommand_Allocate(this.name, this.isGlobal)); 
            TEMP t = exp.IRme(); 
            IR.getInstance().Add_IRcommand(new IRcommand_Store(this.name, t,this.isString, this.data)); 
        }
        return null;
        
    }
}
