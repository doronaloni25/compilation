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
    
    public void MIPSme(){
        // exactly the same process as load from array, except we store instead.
        TEMP s0 = new TEMP("s",0);
        TEMP s1 = new TEMP("s",1);
        // check null reference
        MIPSGenerator.getInstance().checkNullPointer(this.array);
        // check valid index
        MIPSGenerator.getInstance().checkIndexInBounds(this.array, this.index);

        // move index to temp register for translation (we will modify it in this function)
        MIPSGenerator.getInstance().move(s0, this.index);

        // calculate size by bytes, and add one to size since first item is the size of array
        MIPSGenerator.getInstance().li(s1, 4);
        MIPSGenerator.getInstance().addiu(s0, s0, 1);
        MIPSGenerator.getInstance().mul(s0, s0, s1);

        // the arr[index] is now at address: arr + s0, we will save this adress at s0
        MIPSGenerator.getInstance().add(s0, this.array, s0);
        // store the item
        MIPSGenerator.getInstance().sw(this.dst, 0, s0);
    }
}
