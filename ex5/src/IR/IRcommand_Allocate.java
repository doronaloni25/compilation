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
// ALLOCATE = declaration :)
public class IRcommand_Allocate extends IRcommand
{
	String var_name;
	
	public IRcommand_Allocate(String var_name)
	{
		this.var_name = var_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO: implement correctly
		MIPSGenerator.getInstance().allocate(var_name);
	}

	// Gen and Kill implemented in IRcommand

	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + ": " + this.var_name;
	}

	public void MIPSme()
	{
		MIPSGenerator.getInstance().allocate(String label, AST_EXP exp);
	}
	
}
