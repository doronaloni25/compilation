/***********/
/* PACKAGE */
/***********/
package IR;
import java.util.*;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class IRcommand_Func_Cleanup extends IRcommand_Label
{
	public int localVariablesCount;
	
	public IRcommand_Func_Cleanup(int localVariablesCount, String endLabel)
	{
		super(endLabel, Label_Type.FUNC_END);
		this.localVariablesCount = localVariablesCount;
	}

	public IRcommand_Func_Cleanup(int localVariablesCount, String endLabel, boolean isMethod)
	{
		super(endLabel, Label_Type.METHOD_END);
		this.localVariablesCount = localVariablesCount;
	}
}
