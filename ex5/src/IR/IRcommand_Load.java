/***********/
/* PACKAGE */
/***********/
package IR;
import java.util.*;
import java.util.*;
/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;
import HelperFunctions.*;

public class IRcommand_Load extends IRcommand
{
	TEMP dst;
	String var_name;
	ArrayList<Object> data;
	boolean is_global;
	boolean is_func_param = false;
	boolean is_method_param = false;
	boolean is_local_variable = false;
	int offset = -1;

	public IRcommand_Load(String var_name, TEMP dst, ArrayList<Object> data)
	{
		this.dst      = dst;
		this.var_name = var_name;

		if(data!=null)
		{
			this.data = data;
			this.is_global =  (boolean)data.get(0);
			this.is_func_param = (boolean)data.get(1);
			this.is_method_param = (boolean)data.get(2);
			this.is_local_variable = (boolean)data.get(3);		
			this.offset = (int)data.get(5);
		}
	}
	
	public String getGen(Set<String> ins)
	{
		// check if the left side of assign is correct and return the right side
		// checks if the variable is valid and returns the temp serial number as string
		int dstNum = dst.getSerialNumber();
		if(ins.contains(var_name))
		{
			return String.valueOf(dstNum);
		}
		return null;
	}
	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + ": t" 
		+ this.dst.getSerialNumber() + " = " + this.var_name;
	}

	@Override
	public Set<String> getLiveGen()
	{
		return null;
	}

	@Override
	public String getLiveKill()
	{
		return (String.valueOf(this.dst.getSerialNumber()));
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if(this.is_global)
		{
			MIPSGenerator.getInstance().loadGlobal(this.var_name, this.dst);
		}
		else if(this.is_func_param)
		{
			MIPSGenerator.getInstance().loadFuncParam(this.offset ,this.dst);
		}
		else if(this.is_method_param)
		{
			MIPSGenerator.getInstance().loadMethodParam(this.offset, this.dst);
		}
		else if(this.is_local_variable)
		{
			MIPSGenerator.getInstance().loadLocalVar(this.offset, this.dst);
		}
	}
		
	  public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		
		this.dst.setRegisterNumber(interference_graph_map);
	}

}
