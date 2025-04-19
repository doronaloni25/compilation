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
import MIPS.*;

public class IRcommand_Func_Cleanup extends IRcommand_Label
{
	public int localVariablesCount;
	
	public IRcommand_Func_Cleanup(int localVariablesCount, String endLabel)
	{
		super(endLabel, Label_Type.FUNC_END);
		this.localVariablesCount = localVariablesCount;
	}

	public IRcommand_Func_Cleanup(int localVariablesCount, String endLabel, boolean isMethod)
	{
		super(endLabel, Label_Type.METHOD_END);
		this.localVariablesCount = localVariablesCount;
	}

	public void MIPSme()
	{
		//add the label to the mips code, according to the label+ type of label
		MIPSGenerator.getInstance().label(this.label_name);
		
		TEMP ra = new TEMP("ra", -1);
		TEMP sp = new TEMP("sp", -1);
		TEMP fp = new TEMP("fp", -1);

		//pop local variables from the stack, save them in t0-ti registers
		MIPSGenerator.getInstance().addToStack(4 * this.localVariablesCount, sp);
		MIPSGenerator.getInstance().popRegistersFromStack(sp);
		//pop callee activation record
		MIPSGenerator.getInstance().lw(fp, 0, sp);
		//pop the return addresss
        MIPSGenerator.getInstance().lw(ra, 4, sp);
		// add 8 to the stack, because we popped the ra and fp from the stack
        MIPSGenerator.getInstance().addToStack(8, sp);
		//jump to the return adress
        MIPSGenerator.getInstance().jr(ra);
	}
}
//kill and gen are not needed here, implemented in IRcommand
//assign registers is not needed here, implemented in IRcommand