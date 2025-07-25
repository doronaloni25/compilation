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

public abstract class IRcommand
{
	/*****************/
	/* Label Factory */
	/*****************/
	protected static int label_counter=0;
	public static String getFreshLabel(String msg)
	{
		return String.format("Label_%d_%s",label_counter++,msg);
	}
	public String getGen(Set<String> ins)
	{
		return null;
	}

	public String toString()
	{
		return this.getClass().getSimpleName();
	}
	public Set<String> getLiveGen()
	{
		return null;
	}
	public String getLiveKill()
	{
		return null;
	}
	public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public abstract void MIPSme();
}
