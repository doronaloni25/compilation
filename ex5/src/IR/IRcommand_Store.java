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
import MIPS.*;

public class IRcommand_Store extends IRcommand
{
	String var_name;
	TEMP src;
	boolean isString;
	boolean isGlobal;
	public IRcommand_Store(String var_name, TEMP src, boolean isString)
	{
		this.src      = src;
		this.var_name = var_name;
		this.isString = isString;
		this.isGlobal =  (0 == HelperFunctions.getScopeNumFromVarWithScopeName(var_name));
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
		this.src.setRegisterNumber(interference_graph_map);;
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

		if(isGlobal)
		{
			//store global : same for string and int
			MIPSGenerator.getInstance().storeGlobal(var_name, src);
		}
		
		MIPSGenerator.getInstance().store(var_name,src);
	}
}
