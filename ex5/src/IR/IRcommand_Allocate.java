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
import AST.*;
// ALLOCATE = declaration :)
public class IRcommand_Allocate extends IRcommand
{
	String var_name;
	boolean isGlobal;
	AST_EXP exp;
	boolean isString;
	public IRcommand_Allocate(String var_name, boolean isGlobal)
	{
		this.var_name = var_name;
		this.isGlobal = isGlobal;
		this.exp = null;
		this.isString = false;
	}
	public IRcommand_Allocate(String var_name, boolean isGlobal, AST_EXP exp, boolean isString)
	{
		this.var_name = var_name;
		this.isGlobal = isGlobal;
		this.exp = exp;
		this.isString = isString;
	}

	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// this IR command actually does something only if global variable
		// however, still needed for non-global declaration since our used-before-init
		// analysis uses this IRcommand to kill variables.
		if (this.isGlobal)
		{
			if(isString)
			{
				MIPSGenerator.getInstance().allocateGlobalString(this.var_name, this.exp);
			}
			else if(exp != null && exp instanceof AST_EXP_INT)
			{
				MIPSGenerator.getInstance().allocateGlobalInt(this.var_name, this.exp);
			}
			else
			{
				MIPSGenerator.getInstance().allocate(this.var_name);
			}
		}
	}

	// Gen and Kill implemented in IRcommand

	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + ": " + this.var_name;
	}
	
}
