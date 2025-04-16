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
import HelperFunctions.*;

public class IRcommand_Store extends IRcommand
{
	String var_name;
	TEMP src;
	boolean isString;
	ArrayList<Object> data;
	boolean is_global;
	boolean is_func_param = false;
	boolean is_method_param = false;
	boolean is_local_variable = false;
	boolean is_class_field = false;
	int offset = -1;
	public IRcommand_Store(String var_name, TEMP src, boolean isString, ArrayList<Object> data)
	{
		this.src      = src;
		this.var_name = var_name;
		this.isString = isString;
		this.data     = data;
		if(data!=null)
		{
			this.is_global =  data.get(0);
			this.is_func_param = data.get(1);
			this.is_method_param = data.get(2);
			this.is_local_variable = data.get(3);		
			this.offset = data.get(5);
		}
		
	}
	public String getGen(Set<String> ins)
	{
		// check if the left side of assign is correct and return the right side
		// check if the temp is valid and returns the variable name
		int srcNum = src.getSerialNumber();
		if(ins.contains(String.valueOf(srcNum)))
		{
			return var_name;
		}
		return null;
	}
	
	public String toString()
	{
		return "Command: " + this.getClass().getSimpleName() + ": " 
		+ this.var_name + " = t" + this.src.getSerialNumber();
	}
	@Override

	public Set<String> getLiveGen()
	{
		Set<String> liveGen = new HashSet<String>();
		liveGen.add(String.valueOf(this.src.getSerialNumber()));
		return liveGen;
	}
	//kill is not needed here, implemented IRcommand

	@Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		this.src.setRegisterNumber(interference_graph_map);
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		/*
		#string
		allocate(string) : global_s = 0
		label address = ir("abc") : label_1 = "abc"
		store(global_s, value in label_address)

		#allovate(x) : global_x = 0
		store(x,4) : global_x = 4
		*/

		if(this.is_global)
		{
			MIPSGenerator.getInstance.storeGlobal(this.var_name, this.src);
		}
		else if(this.is_func_param)
		{
			MIPSGenerator.getInstance.storeFuncParam(this.offset ,this.src);
		}
		else if(this.is_method_param)
		{
			MIPSGenerator.getInstance.storeMethodParam(this.offset, this.src);
		}
		else if(this.is_local_variable)
		{
			MIPSGenerator.getInstance.storeLocalVar(this.offset, this.src);
		}
			
	}
}
