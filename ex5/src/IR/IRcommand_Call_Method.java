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

public class IRcommand_Call_Method extends IRcommand
{
	
    TEMP dest;
    TEMP object;
    String name;
    ArrayList<TEMP> funcArgs;
	int offset;

	public IRcommand_Call_Method(TEMP dest, TEMP object, String name, ArrayList<TEMP> funcArgs, int offset)
    {
        
        this.dest = dest;
        this.object = object;
        this.name = name;
        this.funcArgs = funcArgs;
        this.offset = offset;
    }
}
