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
import TYPES.*;

public class IRcommand_Array_Instance extends IRcommand
{
    TEMP pointer;
    TYPE instanceType;
	
	public IRcommand_Array_Instance(TEMP pointer, TYPE instanceType)
    {
        this.pointer = pointer;
        this.instanceType = instanceType;
    }

}

			