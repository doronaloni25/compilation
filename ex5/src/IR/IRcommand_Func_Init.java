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

public class IRcommand_Func_Init extends IRcommand
{
	public int localVariablesCount;
	
	public IRcommand_Func_Init(int localVariablesCount)
	{
		this.localVariablesCount = localVariablesCount;
		System.out.println("Local variable count of func init: " + localVariablesCount);
	}

	public void MIPSme()
	{
		TEMP ra = new TEMP("ra", -1);
		TEMP sp = new TEMP("sp", -1);	
		TEMP fp = new TEMP("fp", -1);

		//allocate space to save returns address, and save it
		MIPSGenerator.getInstance().subu(sp, sp, 4);
        MIPSGenerator.getInstance().sw(ra, 0, sp);
		//allocate space for frame pointer, and save it
		MIPSGenerator.getInstance().subu(sp, sp, 4);
		MIPSGenerator.getInstance().sw(fp, 0, sp);
		//initialize frame pointer to point to the top of the stack
		MIPSGenerator.getInstance().move(fp, sp);
		//allocate space for local variables, and save them
		MIPSGenerator.getInstance().storeTempsToStack();
		if(localVariablesCount != 0)
		{
			MIPSGenerator.getInstance().subu(sp, sp, localVariablesCount * 4);
		}

	}
}
//kill and gen are not needed here, implemented in IRcommand
//assign registers is not needed here, implemented in IRcommand