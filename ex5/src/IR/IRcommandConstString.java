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

public class IRcommandConstString extends IRcommand
{
	TEMP t;
	String value;
	String label;
	public IRcommandConstString(TEMP t,String value, String label)
	{
		this.t = t;
		this.value = value;
		this.label = label;
	}
	public IRcommandConstString(String value, String label){
		this.value = value;
		this.label = label;
		this.t = null;
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
		if (t == null){
			return null;
		}
		return (String.valueOf(this.t.getSerialNumber()));
	}

	@Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		if (t != null){
			this.t.setRegisterNumber(interference_graph_map);
		}
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().constString(this.label, this.value);
		if (t != null)
		{
			MIPSGenerator.getInstance().la(this.t, this.label);
		}
	}

}
