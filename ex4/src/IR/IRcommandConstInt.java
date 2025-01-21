/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class IRcommandConstInt extends IRcommand
{
	TEMP t;
	int value;
	
	public IRcommandConstInt(TEMP t,int value)
	{
		this.t = t;
		this.value = value;
	}
	public String genGen(Set<String> ins)
	{
		// check if the left side of assign is correct and return the right side
		// in this case left side is always correct as it is a constant number
		int tNum = t.getSerialNumber();
		return String.valueof(tNum);
	}
}
