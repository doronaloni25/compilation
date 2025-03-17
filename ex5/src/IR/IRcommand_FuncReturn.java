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

public class IRcommand_FuncReturn extends IRcommand
{
	TEMP tFuncReturn;
	String exitLabel;

	public IRcommand_FuncReturn(TEMP t, String exitLabel)
	{
		this.tFuncReturn = t;
		this.exitLabel = exitLabel;
	}
}
