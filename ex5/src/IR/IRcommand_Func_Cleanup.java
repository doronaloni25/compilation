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

public class IRcommand_Func_Cleanup extends IRcommand
{
	public int localVariablesCount;
    public String endLabel;
	
	public IRcommand_Func_Cleanup(int localVariablesCount, String endLabel)
	{
		this.localVariablesCount = localVariablesCount;
        this.endLabel = endLabel;
	}
}
