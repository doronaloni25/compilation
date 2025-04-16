package TYPES;

public abstract class TYPE
{
	/******************************/
	/*  Every type has a name and other fields ... */
	/******************************/
	public String name;
	

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
