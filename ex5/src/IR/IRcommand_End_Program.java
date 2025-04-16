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

public class IRcommand_End_Program extends IRcommand
{
	
	public IRcommand_End_Program()
	{
	}
	//TODO: implement MIPSme? is it needed here?
	public void MIPSme(){
		// this is here because we have to implement mipsme (its abstract)
	}
}

//kill and gen are not needed here, implemented in IRcommand
//assign registers is not needed here, implemented in IRcommand