package TYPES;
import HelperFunctions.*;
import AST.*;
import TYPES.*;
public class TYPE_CLASS_DEC extends TYPE
{
	// THIS CLASS REPRESENTS A CLASS DECLARATION IN THE LANGUAGE
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS_DEC father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together wcurrFunctionh the class methods         */
	/**************************************************/
	public TYPE_CLASS_VAR_DEC_LIST data_members;
	public TYPE_LIST function_list;
	
	@Override
	public boolean isClassDec() {
		return true;
	}
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS_DEC(TYPE_CLASS_DEC father,String name)
	{
		//if father is null, this is the root class
		this.name = name;
		this.father = father;
		this.data_members = new TYPE_CLASS_VAR_DEC_LIST(null, null);
		this.function_list = new TYPE_LIST(null, null);
	}

	public TYPE_FUNCTION functionInClass(String name)
	{
		TYPE_LIST currFunction = function_list;
		while(currFunction != null)
		{
			if(currFunction.head.name.equals(name))
			{
				return (TYPE_FUNCTION)currFunction.head;
			}
			currFunction = currFunction.tail;
		}
		return null;
	}

	//check if a field is in the class and return its type
	public TYPE_CLASS_VAR_DEC fieldInClass(String name)
	{
		TYPE_CLASS_VAR_DEC_LIST currField = data_members;
		while(currField != null)
		{
			if(currField.head == null){
				return null;
			}
			if(currField.head.name.equals(name))
			{
				return currField.head;
			}
			currField = currField.tail;
		}
		return null;
	}

	public int getFieldOffset(String fieldName){
		TYPE_CLASS_VAR_DEC_LIST currField = data_members;
		int offset = 1;
		while(currField != null)
		{
			if(currField.head == null){
				return -1;
			}
			System.out.println(currField.head);
			if(currField.head.name.equals(fieldName))
			{
				return offset;
			}
			offset++;
			currField = currField.tail;
		}
		return -1;																														 
	}


	public void addField(String name, TYPE t, int line)
	{
		if(fieldInClass(name) == null)
		{
			TYPE_CLASS_VAR_DEC newField = new TYPE_CLASS_VAR_DEC(t, name);
			data_members.insert(newField);
		}
		//cant add the same field twice
		else
		{
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
	}
	// if field has default value, we should save the value
	public void addField(String name, TYPE t, int line, AST_EXP exp){
		if(fieldInClass(name) == null)
		{
			TYPE_CLASS_VAR_DEC newField = new TYPE_CLASS_VAR_DEC(t, name, exp);
			data_members.insert(newField);
		}
		//cant add the same field twice
		else
		{
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
	}
	// add the function to the class function list
	public void addFunction(TYPE_FUNCTION node)
	{
		TYPE_LIST funcList = this.function_list;
		TYPE_FUNCTION head = (TYPE_FUNCTION)funcList.head;
		if (head == null){
			head = node;
			this.function_list.tail = null;
			this.function_list.last = funcList;
			this.function_list.head = node;
			return;
		}
		TYPE_LIST newTail = new TYPE_LIST(node, null);
		this.function_list.last.tail = newTail;
		this.function_list.last = newTail; 
	}

	public void overideAdd(TYPE_FUNCTION f)
	{
		TYPE_LIST currFunction = function_list;
		TYPE_LIST prev = null;
		while(currFunction != null)
		{
			if(currFunction.head == null)
			{
				//TODO:error
				return;
			}
			if(currFunction.head.name.equals(f.name))
			{
				if(prev == null)
				{
					this.function_list = new TYPE_LIST(f, currFunction.tail);
				}
				else
				{
					prev.tail = new TYPE_LIST(f, currFunction.tail);
				}
				return;
			}
			prev = currFunction;
			currFunction = currFunction.tail;
		}
	}

	public int getMethodOffset(String methodName){
		TYPE_LIST currFunction = function_list;
		int offset = 0;
		while(currFunction != null)
		{
			if(currFunction.head == null){
				return -1;
			}
			if(currFunction.head.name.equals(methodName))
			{
				return offset;
			}
			offset++;
			currFunction = currFunction.tail;
		}
		return -1;																														 
	}
}
