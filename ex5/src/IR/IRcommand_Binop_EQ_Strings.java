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

public class IRcommand_Binop_EQ_Strings extends IRcommand_Binop
{
	public IRcommand_Binop_EQ_Strings(TEMP dst,TEMP t1,TEMP t2)
	{
		super(dst, t1, t2);
	}
	/*get gen implemented in IR_BINOP*/ 

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// s0 will hold current char adress in t1
		TEMP s0 = new TEMP("s", 0);
		// s1 will hold current char adrees in t2
		TEMP s1 = new TEMP("s", 1);
		// s2 will hold current char in t1
		TEMP s2 = new TEMP("s", 2);
		// s3 will hold current char in t2
		TEMP s3 = new TEMP("s", 3);
		
		// init dest to 0
		MIPSGenerator.getInstance().move(dst, new TEMP("zero", -1));

		String loop_string_start = IRcommand.getFreshLabel("loop_string_start");
		String equal_label = IRcommand.getFreshLabel("equal");
		String end_label = IRcommand.getFreshLabel("end");

		MIPSGenerator.getInstance().move(s0, t1);
		MIPSGenerator.getInstance().move(s1, t2);

		label(loop_string_start);
		MIPSGenerator.getInstance().lb(s2, 0, t1);
		MIPSGenerator.getInstance().lb(s3, 0, t2);

		// if chars aren't matching - not equal
		MIPSGenerator.getInstance().bne(s2, s3, end_label);
		// if both end with 0 - equal
		MIPSGenerator.getInstance().beqz(s2, equal_label);

		// increment pointers
		MIPSGenerator.getInstance().addiu(s0, s0, 1);
		MIPSGenerator.getInstance().addiu(s1, s1, 1);

		MIPSGenerator.getInstance().jump(loop_string_start);

		label(equal_label);
		MIPSGenerator.getInstance().addiu(dst, 1);

		label(end);
	}
}
