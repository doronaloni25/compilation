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

public class IRcommand_Func_Init extends IRcommand
{
	public int localVariablesCount;
	
	public IRcommand_Func_Init(int localVariablesCount)
	{
		this.localVariablesCount = localVariablesCount;
	}
}
//kill and gen are not needed here, implemented in IRcommand