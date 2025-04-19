package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
import java.util.*;

public abstract class AST_Node
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public int SerialNumber;
	public static int generalLine = 0;
	public int line = generalLine;
	public ArrayList<Object> data = new ArrayList<Object>();

	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}
	public TYPE SemantMe() 
	{
        return null;
    }
	public TEMP IRme()
	{
		return null;
	}
	
}
