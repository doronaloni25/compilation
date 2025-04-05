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

public class IRcommand_Func_Init extends IRcommand
{
	public int localVariablesCount;
	
	public IRcommand_Func_Init(int localVariablesCount)
	{
		this.localVariablesCount = localVariablesCount;
	}

	public void MIPSme()
	{
		TEMP ra = new TEMP("ra", -1);
		TEMP sp = new TEMP("sp", -1);	
		TEMP fp = new TEMP("fp", -1);

		//allocate space to save returns address, and save it
		 MIPSGenerator.getInstance().subFromStack(4, sp);
        MIPSGenerator.getInstance().sw(ra, 0, sp);
		//allocate space for frame pointer, and save it
		MIPSGenerator.getInstance().subFromStack(4, sp);
		MIPSGenerator.getInstance().sw(fp, 0, sp);
		//initialize frame pointer to point to the top of the stack
		MIPSGenerator.getInstance().move(fp, sp);
		//allocate space for local variables, and save them
		MIPSGenerator.getInstance().subFromStack(localVariablesCount * 4, sp);
		MIPSGenerator.getInstance().pushRegistersToStack(localVariablesCount, sp);
		//TODO: make sure the sub and add are correct
		//TODO: check if need to initialize the local vars

	}
}
//kill and gen are not needed here, implemented in IRcommand
//assign registers is not needed here, implemented in IRcommand