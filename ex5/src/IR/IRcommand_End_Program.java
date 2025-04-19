/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.util.*;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;
import HelperFunctions.*;

// End Program = run main.
public class IRcommand_End_Program extends IRcommand
{
	
	public IRcommand_End_Program()
	{
	}

	public void MIPSme(){
		MIPSGenerator.getInstance().label("main");
		MIPSGenerator.getInstance().jal(HelperFunctions.formatEntryLabel("main"));
	}
}

//kill and gen are not needed here, implemented in IRcommand
//assign registers is not needed here, implemented in IRcommand