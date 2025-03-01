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
	public int localVarCount = 0;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FUNCTION(TYPE returnType,String name,TYPE_LIST params)
	{
		this.name = name;
		this.returnType = returnType;
		this.params = params;
		// TODO: Check if this is necessary, or params get saved in other way
		if (params != null) this.localVarCount = params.getLength();
		this.isInherited = false;
	}

	@Override
	public boolean isFunction() {
		return true;
	}
	
}
