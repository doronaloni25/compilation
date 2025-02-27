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

public class IRcommandConstInt extends IRcommand
{
	TEMP t;
	int value;
	
	public IRcommandConstInt(TEMP t,int value)
	{
		this.t = t;
		this.value = value;
	}
	public String getGen(Set<String> ins)
	{
		// check if the right side of assign is correct and return the left side
		// in this case right side is always correct as it is a constant number
		int tNum = t.getSerialNumber();
		return String.valueOf(tNum);
	}

	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + " , with int = " + this.value;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().li(t,value);
	}
}
