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

public class IRcommand_Binop_Add_Strings extends IRcommand_Binop
{

	public IRcommand_Binop_Add_Strings(TEMP dst,TEMP t1,TEMP t2)
	{
		super(dst, t1, t2);
	}

	/*get gen implemented in IR_BINOP*/
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().add(dst,t1,t2);
	}
}
