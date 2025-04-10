				
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
    //TODO- MIPSme
}
            
