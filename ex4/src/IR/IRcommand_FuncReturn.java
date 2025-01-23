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
	
	public IRcommand_FuncReturn(TEMP t)
	{
		this.tFuncReturn = t;
	}
}
