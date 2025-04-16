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

public class IRcommand_Label extends IRcommand
{
	String label_name;
	/*
	 * POSSIBLE LABEL TYPES:
	 *  FUNC_START,
	 *	FUNC_END,
	 *	METHOD_START,
	 *	METHOD_END,
	 *	WHILE_START,
	 *	WHILE_END,
	 *	IF
	 */
	Label_Type label_type;
	
	public IRcommand_Label(String label_name, Label_Type label_type)
	{
		this.label_name = label_name;
		this.label_type = label_type;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().label(label_name);
	}
	
	
	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + ": " + this.label_name;
	}

	//kill and gen are not needed here, implemented in IRcommand
	//assign registers is not needed here, implemented in IRcommand
}
