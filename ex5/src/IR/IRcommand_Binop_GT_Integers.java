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

public class IRcommand_Binop_GT_Integers extends IRcommand_Binop
{
	public IRcommand_Binop_GT_Integers(TEMP dst,TEMP t1,TEMP t2)
	{
		super(dst, t1, t2);
	}
	/*get gen implemented in IR_BINOP*/ 
	
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		String label_end        = getFreshLabel("end");
		String label_AssignOne  = getFreshLabel("AssignOne");
		String label_AssignZero = getFreshLabel("AssignZero");

		/******************************************/
		/* [1] if (t1 > t2) goto label_AssignOne; */
		/*     else goto label_AssignZero;       */
		/******************************************/
		MIPSGenerator.getInstance().bgt(t1, t2, label_AssignOne);
		MIPSGenerator.getInstance().ble(t1, t2, label_AssignZero);

		/************************/
		/* [2] label_AssignOne: */
		/*                      */
		/*         dst := 1     */
		/*         goto end;    */
		/*                      */
		/************************/
		MIPSGenerator.getInstance().label(label_AssignOne);
		MIPSGenerator.getInstance().li(dst, 1);
		MIPSGenerator.getInstance().jump(label_end);

		/*************************/
		/* [3] label_AssignZero: */
		/*                       */
		/*         dst := 0      */
		/*         goto end;     */
		/*                       */
		/*************************/
		MIPSGenerator.getInstance().label(label_AssignZero);
		MIPSGenerator.getInstance().li(dst, 0);
		MIPSGenerator.getInstance().jump(label_end);

		/******************/
		/* [4] label_end: */
		/******************/
		MIPSGenerator.getInstance().label(label_end);

	}
}
