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
import MIPS.*;

public class IRcommand_Jump_Label extends IRcommand
{
	String label_name;
	
	public IRcommand_Jump_Label(String label_name)
	{
		this.label_name = label_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().jump(label_name);
	}
	
	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + ": jump to " + this.label_name;
	}

	public void MIPSme()
	{
		MIPSGenerator.getInstance().jump(label_name);
	}

//kill and gen are not needed here, implemented in IRcommand
//assined registers is not needed here, implemented in IRcommand
	}

