package TYPES;

public abstract class TYPE
{
	/******************************/
	/*  Every type has a name and other fields ... */
	/******************************/
	public String name;
	public boolean isGlobal = false;
	public boolean is_func_param = false;
	public boolean is_method_param = false;
	public boolean is_local_varibale = false;
	public boolean is_class_field = false;
	public int offset = -1;

	/*************/
	/* isClass() */
	/*************/
	public boolean isClass(){ return false;}

	/*************/
	/* isArray() */
	/*************/
	public boolean isArray(){ return false;}
	public boolean isFunction(){ return false;}
	public boolean isClassDec(){ return false;}


}
