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

public class IRcommand_Load extends IRcommand
{
	TEMP dst;
	String var_name;
	
	public IRcommand_Load(TEMP dst,String var_name)
	{
		this.dst      = dst;
		this.var_name = var_name;
	}
	public String getGen(Set<String> ins)
	{
		// check if the left side of assign is correct and return the right side
		// checks if the variable is valid and returns the temp serial number as string
		int dstNum = dst.getSerialNumber();
		if(ins.contains(var_name))
		{
			return String.valueof(dstNum);
		}
		return null;
	}
}
