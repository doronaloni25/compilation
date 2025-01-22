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

public class IRcommand_Store extends IRcommand
{
	String var_name;
	TEMP src;
	
	public IRcommand_Store(String var_name,TEMP src)
	{
		this.src      = src;
		this.var_name = var_name;
	}
	public String getGen(Set<String> ins)
	{
		// check if the left side of assign is correct and return the right side
		// check if the temp is valid and returns the variable name
		int srcNum = src.getSerialNumber();
		if(ins.contains(String.valueof(srcNum)))
		{
			return var_name;
		}
		return null;
	}
}
