package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public class AST_FUNC_DEC extends AST_DEC
{
	public AST_TYPE type;
	public String name;
    public AST_STMT_LIST stmtList;
    protected int localVarsCount = 0;
    public TYPE_CLASS_DEC classDec = null;

    public AST_FUNC_DEC (AST_TYPE type, String name, AST_STMT_LIST stmtList) 
    {

        this.type = type;
        this.name = name;
        this.stmtList = stmtList;
    }
    public TYPE SemantMe ()
    {
        return null;
    }
    public TEMP IRme()
    {
        if(this.classDec == null)
        {
            IR.getInstance().Add_IRcommand(new IRcommand_Label(HelperFunctions.formatEntryLabel(name)));
        }
        else
        {
            IR.getInstance().Add_IRcommand(new IRcommand_Label(HelperFunctions.formatMethodLabel(this.classDec.name, name)));
        }
        IR.getInstance().Add_IRcommand(new IRcommand_Func_Init(this.localVariablesCount));
        this.stmtList.IRme();
        if(this.classDec == null)
        {
          String endLabel = HelperFunctions.formatExitLabel(name);
        }
        else
        {
            String endLabel = HelperFunctions.formatMethodExitLabel(this.classDec.name, name);
        }
        IR.getInstance().Add_IRcommand(new IRcommand_Func_Cleanup(this.localVariablesCount, endLabel));
        return null;
    }
}