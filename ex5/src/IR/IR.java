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

public class IR
{
	private IRcommand head=null;
	private IRcommandList tail=null;

	/******************/
	/* Add IR command */
	/******************/
	public void Add_IRcommand(IRcommand cmd)
	{
		if ((head == null) && (tail == null))
		{
			this.head = cmd;
		}
		else if ((head != null) && (tail == null))
		{
			this.tail = new IRcommandList(cmd,null);
		}
		else
		{
			IRcommandList it = tail;
			while ((it != null) && (it.tail != null))
			{
				it = it.tail;
			}
			it.tail = new IRcommandList(cmd,null);
		}
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if (head != null) head.MIPSme();
		if (tail != null) tail.MIPSme();
	}
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static IR instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected IR() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static IR getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new IR();
		}
		return instance;
	}

	public ControlFlowGraph createCFG(){
        IRcommandList properCommandList = new IRcommandList(this.head, this.tail);
        return new ControlFlowGraph(properCommandList);
    }
}
