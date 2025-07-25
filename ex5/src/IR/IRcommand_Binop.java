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

public class IRcommand_Binop extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}

	@Override
	public String getGen(Set<String> ins)
	{
		int num1 = t1.getSerialNumber();
		int num2 = t2.getSerialNumber();
		if(ins.contains(String.valueOf(num1)) && ins.contains(String.valueOf(num2)))
		{
			return String.valueOf(dst.getSerialNumber());
		}
		return null;
	}
	@Override
	public String getLiveKill(){
		return String.valueOf(dst.getSerialNumber());
	}

	@Override
	public Set<String> getLiveGen()
	{
		Set<String> liveGen = new HashSet<String>();
		liveGen.add(String.valueOf(t1.getSerialNumber()));
		liveGen.add(String.valueOf(t2.getSerialNumber()));
		return liveGen;
	}
	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + ": t" 
		+ this.dst.getSerialNumber() + " = " + this.t1.getSerialNumber() + " op " + this.t2.getSerialNumber();
	}

	@Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		this.t1.setRegisterNumber(interference_graph_map);
        this.t2.setRegisterNumber(interference_graph_map);
		this.dst.setRegisterNumber(interference_graph_map);
	}

	public void MIPSme(){
		// this is here because a class that extends IRcommand must have this
		// gets implemented in all of its inherited classes
	}
}
