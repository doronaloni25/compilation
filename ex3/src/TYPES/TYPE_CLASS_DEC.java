package TYPES;

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
	public TYPE_LIST data_members;
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
		this.data_members = new TYPE_LIST(null, null);
		this.function_list = new TYPE_LIST(null, null);
		//here we need to add function and arguments of the class 
	}
	@Override
	public boolean isClass(){ return true;}
	public TYPE_FUNCTION functionInClass(String name)
	{
		TYPE_LIST currFunction = function_list;
		while(currFunction != null)
		{
			if(currFunction.head.name.equals(name))
			{
				return currFunction.head;
			}
			currFunction = currFunction.tail;
		}
		return null;
	}
	//check if a field is in the class and return its type
	public TYPE fieldInClass(String name)
	{
		TYPE_LIST currField = data_members;
		while(currField != null)
		{
			if(currField.head.name.equals(name))
			{
				return currField.head;
			}
			currField = currField.tail;
		}
		return null;
	}
}
