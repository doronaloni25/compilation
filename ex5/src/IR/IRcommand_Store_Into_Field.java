				
package IR;

import MIPS.*;
import java.util.*;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import HelperFunctions.*;

public class IRcommand_Store_Into_Field extends IRcommand 
{
    public TEMP classT;
    public TEMP value; 
    public int offset;
    public String fieldName;

    public IRcommand_Store_Into_Field(TEMP classT,TEMP value, int offset, String fieldName)
    {
        this.classT = classT;
        this.value = value;
        this.offset = offset;
        this.fieldName = fieldName;
    }
    public Set<String> getLiveGen()
    {
        Set<String> liveGen = new HashSet<String>();
        liveGen.add(String.valueOf(this.classT.getSerialNumber()));
        liveGen.add(String.valueOf(this.value.getSerialNumber()));
        return liveGen;
    }
    //kill is not needed here, implemented IRcommand

    @Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		this.classT.setRegisterNumber(interference_graph_map);
        this.value.setRegisterNumber(interference_graph_map);
	}
    
    public void MIPSme(){
        // same process as load into field, except sw instead.
        // check null dereference
        MIPSGenerator.getInstance().checkNullPointer(this.classT);
        // get byte offset and store into the instance (add one since first item is vtable address)
        int byte_offset = this.offset * 4;
        MIPSGenerator.getInstance().sw(this.value, byte_offset, this.classT);
    }
}
            
