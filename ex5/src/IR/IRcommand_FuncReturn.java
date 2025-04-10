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

public class IRcommand_FuncReturn extends IRcommand
{
	TEMP tFuncReturn;
	String exitLabel;

	public IRcommand_FuncReturn(TEMP t, String exitLabel)
	{
		this.tFuncReturn = t;
		this.exitLabel = exitLabel;
	}
}
@Override
public Set<String> getLiveGen()
	{
		if(this.tFuncReturn != null)
		{
			Set<String> liveGen = new HashSet<String>();
			liveGen.add(String.valueOf(this.tFuncReturn.getSerialNumber()));
			return liveGen;
		}
		return null;
	}
@Override
	public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		if(this.tFuncReturn != null)
		{
			this.tFuncReturn.setRegisterNumber(interference_graph_map);
		}
	}

@Override
	public void MIPSme()
	{
		if(this.tFuncReturn != null)
		{
			MIPSGenerator.getInstance().move(new TEMP("v", 0), this.tFuncReturn);
		}
		MIPSGenerator.getInstance().jump(this.exitLabel);
	}
//kill is not needed here, implemented in IRcommand