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
import IR.*;

public class TEMP
{
	private int serial = 0;
	private int register_number = -1;

	// can be t, s, a, v. - default is t
	public String register_type = "t";

	public TEMP(int serial)
	{
		this.serial = serial;
	}

	public TEMP(String register_type, int register_number){
		this.register_type = register_type;
		this.register_number = register_number;
	}
	
	public int getSerialNumber()
	{
		return serial;
	}

	public void setRegisterNumber(Map<String, InterferenceGraphNode> interference_graph_map){
		String name = String.valueOf(this.serial);
		this.register_number = interference_graph_map.get(name).color;
	}

	public String getRegisterName(){
		if(this.register_number == -1)
		{
			return "$" + register_type;
		}
		return "$" + register_type + register_number;

	}
}
