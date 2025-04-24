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
		// s0 and s1 will hold lengths of strings
		TEMP s0 = new TEMP("s", 0);
		TEMP s1 = new TEMP("s", 1);
		// s2 will hold new string length
		TEMP s2 = new TEMP("s", 2);
		// s3 will hold pointer to new string (and will move during copy of the 2 strings)
		TEMP s3 = new TEMP("s", 3);
		// s4 will hold final pointer to new string.
		TEMP s4 = new TEMP("s", 4);

		// calc lengths
		TEMP len = MIPSGenerator.getInstance().calcStringLen(this.t1);
		MIPSGenerator.getInstance().move(s0, len);
		len = MIPSGenerator.getInstance().calcStringLen(this.t2);
		MIPSGenerator.getInstance().move(s1, len);
		
		// add lengths of both strings and save into s2 for new string length - add 1 for terminating char
		MIPSGenerator.getInstance().addNoBoundsCheck(s2, s0, s1);
		MIPSGenerator.getInstance().addiu(s2, s2, 1);

		// allocate memory for new string
		TEMP v0 = MIPSGenerator.getInstance().malloc(s2);
		// copy twice because we will move along new string
		MIPSGenerator.getInstance().move(s3, v0);
		MIPSGenerator.getInstance().move(s4, s3);

		// copy string 1 into start of new string
		MIPSGenerator.getInstance().strcpy(s3, this.t1);
		// move new string pointer (with the length of the 1st string) and copy 2nd string
		MIPSGenerator.getInstance().addNoBoundsCheck(s3, s3, s0);
		MIPSGenerator.getInstance().strcpy(s3, this.t2);
		// move new string pointer (with the length of the 2nd string) and add terminating char
		MIPSGenerator.getInstance().addNoBoundsCheck(s3, s3, s1);
		TEMP zero = new TEMP("zero", -1);
		MIPSGenerator.getInstance().sb(zero, 0, s3);

		// save s4 into this.dst
		MIPSGenerator.getInstance().move(this.dst, s4);
	}
}
