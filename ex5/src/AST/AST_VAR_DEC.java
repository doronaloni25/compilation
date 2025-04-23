package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public class AST_VAR_DEC extends AST_DEC 
{
    
	public AST_TYPE type;
	public String name;
    public String nameWithVarDecScope;
    public boolean isGlobal = false;

    public AST_VAR_DEC (AST_TYPE type, String name) 
    {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.type = type;
        this.name = name;

        System.out.format("====================== varDec -> type ID SEMICOLON \n");
    }
    @Override
    public TYPE SemantMe() 
    {
        TYPE currType = SYMBOL_TABLE.getInstance().findWithinScope(name);

        if(currType != null)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        TYPE t = type.SemantMe();
        if (t == null)
        {
            HelperFunctions.printError(line, this.getClass().getSimpleName());
        }
        //get global for ex5
        this.isGlobal = SYMBOL_TABLE.getInstance().isGlobalScope();
        if(this.isGlobal)
        {
            HelperFunctions.set_data(this.data,true, false, false, false, false);
        }
        // for IRme - it will return my scope, as we just inserted it to the symbol table
		//this.nameWithVarDecScope = HelperFunctions.getVarNameWithDecScope(name);
        // check if im in class scope (but not in func), and if so add to the class fields
        TYPE_CLASS_DEC classType = SYMBOL_TABLE.getInstance().inClass;
        TYPE_FUNCTION funcType = SYMBOL_TABLE.getInstance().inFunction;
        // update the function local variables count for IRme later...
        if(funcType != null)
        {
            //take care of both methods and functions
          
            HelperFunctions.set_data(this.data, false, false, false, true, false, funcType.localVariablesCount);  
            funcType.localVariablesCount++;
            
        }
        // This basically means we are inside a class, but in its "main" scope - 
        // - not inside any funcs, if's, and while - since they are all inside funcdec
        if(classType != null && funcType == null)
        {
            // This also checks that the field was not declared before (in any ancestor of the class)
            // if the field has a default value, we should save it
            if (this instanceof AST_VAR_DEC_EXP){
                classType.addField(name, t, line, ((AST_VAR_DEC_EXP)this).exp);
            }
            else{
                classType.addField(name, t, line);
            }
            //t is a field in the class
            HelperFunctions.set_data(this.data,false, false, false, false, true, classType.getFieldOffset(this.name)); 
        }
        SYMBOL_TABLE.getInstance().enter(name, t, this.data);
       
        return t;
    }
    @Override
    public TEMP IRme()
    {
        IR.getInstance().Add_IRcommand(new IRcommand_Allocate(this.name, this.isGlobal));
        return null;
    }
}

	
	
  


