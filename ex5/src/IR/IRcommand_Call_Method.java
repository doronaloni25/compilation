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

    @Override
	public Set<String> getLiveGen()
	{
		Set<String> liveGen = new HashSet<String>();
        liveGen.add(String.valueOf(object.getSerialNumber()));
        for (TEMP tArg : this.funcArgs){
            liveGen.add(String.valueOf(tArg.getSerialNumber()));
        }
		return liveGen;
	}

    @Override
    public String getLiveKill()
	{
		return (this.dest != null) ? String.valueOf(this.dest.getSerialNumber()) : null;
	}

    @Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		for (TEMP tArg : this.funcArgs){
            tArg.setRegisterNumber(interference_graph_map);
        }
        if (this.dest != null){
            this.dest.setRegisterNumber(interference_graph_map);
        }
	}
    
}
