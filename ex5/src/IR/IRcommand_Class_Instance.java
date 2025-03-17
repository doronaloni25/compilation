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

public class IRcommand_Class_Instance extends IRcommand
{
    TYPE_CLASS_VAR_DEC_LIST data_members;
    String name;
    TEMP pointer;
	
	public IRcommand_Class_Instance(TYPE_CLASS_VAR_DEC_LIST data_members, String name, TEMP pointer)
    {
        this.data_members = data_members;
        this.name = name;
        this.pointer = pointer;
    }

}
			