package IR;

import REG.*;
import MIPS.*;
//import REG.*;
//import MIPS.*;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import HelperFunctions.*;

public class IRcommand_Load_From_Array extends IRcommand 
{
    public TEMP dst;
    public TEMP array;
    public TEMP index;

    public IRcommand_Load_From_Array(TEMP dst, TEMP array, TEMP index)
    {
        this.dst = dst;
        this.array = array;
        this.index = index;
    }
    @Override
    public Set<String> getLiveGen()
    {
        Set<String> liveGen = new HashSet<String>();
        liveGen.add(String.valueOf(this.array.getSerialNumber()));
        liveGen.add(String.valueOf(this.index.getSerialNumber()));
        return liveGen;
    }
    @Override
    public String getLiveKill()
    {
        return String.valueOf(this.dst.getSerialNumber());
    }
    @Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		
		this.dst.setRegisterNumber(interference_graph_map);
        this.array.setRegisterNumber(interference_graph_map);
        this.index.setRegisterNumber(interference_graph_map);
		
	}
    //TODO- MIPSme
}
