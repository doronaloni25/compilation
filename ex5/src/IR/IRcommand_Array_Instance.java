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
// Create new Array: new <type> arr[exp] where exp is integer
public class IRcommand_Array_Instance extends IRcommand
{
    TEMP pointer;
    TYPE instanceType;
    TEMP tExp;
	
	public IRcommand_Array_Instance(TEMP pointer, TYPE instanceType, TEMP tExp)
    {
        this.pointer = pointer;
        this.instanceType = instanceType;
        this.tExp = tExp;
    }

    @Override
	public String getLiveKill()
	{
        // kill pointer as we apply a value to it now.
		return String.valueOf(pointer.getSerialNumber());
	}

    @Override
	public Set<String> getLiveGen()
	{
        // gen the size of the array
		Set<String> liveGen = new HashSet<String>();
		liveGen.add(String.valueOf(tExp.getSerialNumber()));
		return liveGen;
	}

    @Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		this.pointer.setRegisterNumber(interference_graph_map);
        this.tExp.setRegisterNumber(interference_graph_map);
	}

    @Override
    public void MIPSme(){
        TEMP s0 = new TEMP("s", 0);
        TEMP s1 = new TEMP("s", 1);
        TEMP s2 = new TEMP("s", 2);
        // save size
        MIPSGenerator.getInstance().move(s0, this.tExp);
        MIPSGenerator.getInstance().move(s1, this.tExp);
        
    }
}

			