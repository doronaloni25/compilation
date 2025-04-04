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

public class IRcommand_Binop_Sub_Integers extends IRcommand_Binop
{
	public IRcommand_Binop_Sub_Integers(TEMP dst,TEMP t1,TEMP t2)
	{
		super(dst, t1, t2);
	}
	/*get gen implemented in IR_BINOP*/ 

	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// TODO: CHANGE TO SUB!!!!
		MIPSGenerator.getInstance().sub(dst,t1,t2);
	}
}