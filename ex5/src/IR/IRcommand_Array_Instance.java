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

public class IRcommand_Array_Instance extends IRcommand
{
    TEMP pointer;
    TEMP instanceType;
	
	public IRcommand_Array_Instance(TEMP pointer, TEMP instanceType)
    {
        this.pointer = pointer;
        this.instanceType = instanceType;
    }

}

			