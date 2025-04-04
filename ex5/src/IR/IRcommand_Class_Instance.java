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
// Creates a new instance of a class.
public class IRcommand_Class_Instance extends IRcommand
{
    TYPE_CLASS_VAR_DEC_LIST data_members;
    String name;
    TEMP pointer;
	
	public IRcommand_Class_Instance(TYPE_CLASS_VAR_DEC_LIST data_members, String name, TEMP pointer)
    {
        this.data_members = data_members;
        this.name = name;
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

}
			