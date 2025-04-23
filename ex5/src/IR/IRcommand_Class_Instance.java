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
import MIPS.*;

// Creates a new instance of a class.
public class IRcommand_Class_Instance extends IRcommand
{
    TYPE_CLASS_VAR_DEC_LIST data_members;
    String class_name;
    TEMP pointer;
	
	public IRcommand_Class_Instance(TYPE_CLASS_VAR_DEC_LIST data_members, String class_name, TEMP pointer)
    {
        this.data_members = data_members;
        this.class_name = class_name;
        this.pointer = pointer;
    }

    @Override
    public String getLiveKill()
	{
		return String.valueOf(this.pointer.getSerialNumber());
	}

    @Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		this.pointer.setRegisterNumber(interference_graph_map);
	}

    public void MIPSme()
    {
        int numOfDataMembers = data_members.getLength();
        TEMP s0 = new TEMP("s", 0);
        TEMP v0;
        String dispatch_vector_label = String.format("%s_dispatch_vector", class_name);
        //  allocate for size of dataMembers + 1 for Dispatch Vector
        MIPSGenerator.getInstance().li(s0, 4 *(numOfDataMembers + 1));
        v0 = MIPSGenerator.getInstance().malloc(s0);
        //put the allocated address in the pointer
        MIPSGenerator.getInstance().move(this.pointer, v0);
        //store in s0 the dispatch vector address
        MIPSGenerator.getInstance().la(s0, dispatch_vector_label);
        // put at the first location of the class instance the dispatch vector
        MIPSGenerator.getInstance().sw(s0, 0, this.pointer);
        //now fill default values for each 
        int i = 0;
        for(TYPE_CLASS_VAR_DEC_LIST curr_node = data_members; curr_node != null && curr_node.head != null; curr_node=curr_node.tail)
        {
            TYPE_CLASS_VAR_DEC curr_var = curr_node.head;
            if(curr_var.t instanceof TYPE_STRING)
            {
                MIPSGenerator.getInstance().la(s0, curr_var.defaultStringLabel);
                MIPSGenerator.getInstance().sw(s0, 4*(i+1), this.pointer);
            }
            // we are here if we are INT or NIL
            else
            {
                MIPSGenerator.getInstance().li(s0, curr_var.defaultIntValue);
                MIPSGenerator.getInstance().sw(s0, 4*(i+1), this.pointer);
            }
            i++;
        }

    }

}
			