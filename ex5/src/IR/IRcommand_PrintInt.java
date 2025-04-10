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

public class IRcommand_PrintInt extends IRcommand
{
	TEMP t;
	
	public IRcommand_PrintInt(TEMP t)
	{
		this.t = t;
	}

	public Set<String> getLiveGen()
	{
		Set<String> liveGen = new HashSet<String>();
		liveGen.add(String.valueOf(this.t.getSerialNumber()));
		return liveGen;
	}
	//kill is not needed here, implemented IRcommand

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().print_int(t);
	}
	@Override
	public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		
		this.t.setRegisterNumber(interference_graph_map);	
	}
	public void MIPSme()
	{
		MIPSGenerator.getInstance().print_int(t);
	}
}
