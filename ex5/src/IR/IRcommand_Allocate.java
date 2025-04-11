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
	boolean isGlobal;
	public IRcommand_Allocate(String var_name, boolean isGlobal)
	{
		this.var_name = var_name;
		this.isGlobal = isGlobal;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// this IR command actually does something only if global variable
		// however, still needed for non-global declaration since our used-before-init
		// analysis uses this IRcommand to kill variables.
		if (this.isGlobal){
			MIPSGenerator.getInstance().allocate(var_name);
		}
	}

	// Gen and Kill implemented in IRcommand

	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + ": " + this.var_name;
	}
	
}
