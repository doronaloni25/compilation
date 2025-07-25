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
import MIPS.*;

public class IRcommand_Create_Dispatch_Vector extends IRcommand
{
	public String name;
    public TYPE_LIST function_list;
    
	public IRcommand_Create_Dispatch_Vector(String name, TYPE_LIST function_list)
    {
        this.name = name;
        this.function_list = function_list;
    }
    @Override
    public void MIPSme()
    {
        // Create the dispatch vector
        MIPSGenerator.getInstance().allocateDispatchVector(this.name, this.function_list);
    }
}
