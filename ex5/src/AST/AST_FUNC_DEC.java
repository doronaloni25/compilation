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

    public AST_FUNC_DEC (AST_TYPE type, String name, AST_STMT_LIST stmtList) {

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
        IR.getInstance().Add_IRcommand(new IRcommand_Label(HelperFunctions.formatEntryLabel(name)));
        //TODO: implement for methods
        IR.getInstance().Add_IRcommand(new IRcommand_Function_Init(this.localVariablesCount));
        this.stmtList.IRme();
        String endLabel = HelperFunctions.formatExitLabel(name);
        IR.getInstance().Add_IRcommand(new IRcommand_Function_Cleanup(this.localVariablesCount, endLabel));
        return null;
    }
}