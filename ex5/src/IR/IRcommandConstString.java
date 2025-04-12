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
	Strint label;
	public IRcommandConstString(TEMP t,String value, String label)
	{
		this.t = t;
		this.value = value;
		this.label = label;
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

	@Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		this.t.setRegisterNumber(interference_graph_map);;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().constString(this.label, this.value);
		MIPSGenerator.getInstance().la(this.t, this.label);
		
	}

}
