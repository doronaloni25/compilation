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

public class IRcommand_Call_Func extends IRcommand
{
	String name;
    TEMP dest;
    ArrayList<TEMP> funcArgs;
	
	public IRcommand_Call_Func(String name, TEMP dest, ArrayList<TEMP> funcArgs)
    {
        this.name = name;
        this.dest = dest;
        this.funcArgs = funcArgs;
    }

    @Override
    public Set<String> getLiveGen()
	{
        Set<String> genSet = new HashSet<String>;
		for (TEMP tArg : this.funcArgs){
            genSet.add(String.valueOf(tArg.getSerialNumber()));
        }
        return genSet;
	}

    @Override
    public String getLiveKill()
	{
        return (this.dest != null) ? String.valueOf(this.dest.getSerialNumber()) : null;
	}
}



