package TYPES;

public class TYPE_FUNCTION extends TYPE
{
	/***********************************/
	/* The return type of the function */
	/***********************************/
	public TYPE returnType;
	public boolean isInherited;
	/*************************/
	/* types of input params */
	/*************************/
	public TYPE_LIST params;
	public int localVariablesCount = 0;
	public String classDecName;
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FUNCTION(TYPE returnType,String name,TYPE_LIST params)
	{
		this.name = name;
		this.returnType = returnType;
		this.params = params;
		this.isInherited = false;
		this.classDecName = null;
	}

	@Override
	public boolean isFunction() {
		return true;
	}
	
}
