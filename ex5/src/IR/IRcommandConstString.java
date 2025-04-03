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

public class IRcommandConstString extends IRcommand
{
	TEMP t;
	String value;
	
	public IRcommandConstString(TEMP t,String value)
	{
		this.t = t;
		this.value = value;
	}
	public String getGen(Set<String> ins)
	{
        //TODO: DO!
		return "";
	}

	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + " , with string = " + this.value;
	}

	public String getLiveKill()
	{
		return (String.valueOf(this.t.getSerialNumber()));
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO: DO!
	}

}
