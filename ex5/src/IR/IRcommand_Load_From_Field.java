package IR;

import MIPS.*;
import java.util.*;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import HelperFunctions.*;
import TYPES.*;

public class IRcommand_Load_From_Field extends IRcommand 
{
    public TEMP dst;
    public TEMP instance;
    public TYPE_CLASS_DEC tClass;
    public int offset;
    public String fieldName;

    public IRcommand_Load_From_Field(TEMP dst, TEMP instance, int offset, String fieldName)
    {
        this.dst = dst;
        this.instance = instance;
        this.tClass = tClass;
        this.offset = offset;
        this.fieldName = fieldName;
    }
    @Override
    public Set<String> getLiveGen()
    {
        Set<String> liveGen = new HashSet<String>();
        liveGen.add(String.valueOf(this.instance.getSerialNumber()));
        return liveGen;
    }

    @Override
    public String getLiveKill()
    {
        return (String.valueOf(this.dst.getSerialNumber()));
    }
    @Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		
		this.dst.setRegisterNumber(interference_graph_map);
        this.instance.setRegisterNumber(interference_graph_map);	
	}
    
    public void MIPSme(){
        // check null dereference
        MIPSGenerator.getInstance().checkNullPointer(this.instance);
        // get byte offset and store into the instance (add one since first item is vtable address)
        int byte_offset = this.offset  * 4;
        MIPSGenerator.getInstance().lw(this.dst, byte_offset, this.instance);
    }
}
