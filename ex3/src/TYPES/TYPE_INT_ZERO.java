package TYPES;
// specific type for int zero, used to check for devision by zero 
public class TYPE_INT_ZERO extends TYPE{
    /**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static TYPE_INT_ZERO instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected TYPE_INT_ZERO() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static TYPE_INT_ZERO getInstance()
	{
		if (instance == null)
		{
			instance = new TYPE_INT_ZERO();
			instance.name = "int_NEGATIVE";
		}
		return instance;
	}
}

