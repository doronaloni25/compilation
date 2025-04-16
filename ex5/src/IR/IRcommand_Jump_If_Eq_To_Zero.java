/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;
import java.util.*;

public class IRcommand_Jump_If_Eq_To_Zero extends IRcommand
{
	TEMP t;
	String label_name;
	
	public IRcommand_Jump_If_Eq_To_Zero(TEMP t, String label_name)
	{
		this.t          = t;
		this.label_name = label_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().beqz(t,label_name);
	}
	
	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + ": if t" + t.getSerialNumber() + " jump to " + this.label_name;
	}
	@Override
	public Set<String> getLiveGen()
	{
		Set<String> liveGen = new HashSet<String>();
		liveGen.add(String.valueOf(this.t.getSerialNumber()));
		return liveGen;
	}
	//kill is not needed here, implemented in IRcommand
	@Override
	public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		
		this.t.setRegisterNumber(interference_graph_map);
		
	}
}
