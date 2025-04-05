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

public class IRcommand_Store_Into_Array extends IRcommand 
{
    public TEMP array;
    public TEMP index;
    public TEMP value;

    public IRcommand_Store_Into_Array(TEMP array, TEMP index, TEMP value)
    {
        this.array = array;
        this.index = index;
        this.value = value;
    }
    @Override
    public Set<String> getLiveGen()
    {
        Set<String> liveGen = new HashSet<String>();
        liveGen.add(String.valueOf(this.array.getSerialNumber()));
        liveGen.add(String.valueOf(this.index.getSerialNumber()));
        liveGen.add(String.valueOf(this.value.getSerialNumber()));
        return liveGen;
    }
    //kill is not needed here, implemented IRcommand

    @Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		this.array.setRegisterNumber(interference_graph_map);
        this.index.setRegisterNumber(interference_graph_map);
		this.value.setRegisterNumber(interference_graph_map);
	}
    //TODO- MIPSme
}
