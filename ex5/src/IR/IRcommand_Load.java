/***********/
/* PACKAGE */
/***********/
package IR;
import java.util.*;
import java.util.*;
/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import HelperFunctions.*;

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
			return String.valueOf(dstNum);
		}
		return null;
	}
	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + ": t" 
		+ this.dst.getSerialNumber() + " = " + this.var_name;
	}

	@Override
	public Set<String> getLiveGen()
	{
		return null;
	}

	@Override
	public String getLiveKill()
	{
		return (String.valueOf(this.dst.getSerialNumber()));
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().load(dst,var_name);
	}
}
