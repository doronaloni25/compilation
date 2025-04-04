/***********/
/* PACKAGE */
/***********/
package TEMP;
import java.util.*;
/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class TEMP
{
	private int serial = 0;
	private int register_number = -1;
	public TEMP(int serial)
	{
		this.serial = serial;
	}
	
	public int getSerialNumber()
	{
		return serial;
	}

	public setRegisterNumber(Map<String, InterferenceGraphNode> interference_graph_map){
		String name = String.valueOf(this.serial);
		this.register_number = interference_graph_map.get(name).color;
	}
}
