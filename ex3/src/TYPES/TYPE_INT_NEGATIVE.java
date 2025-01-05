package TYPES;

// This is needed for knowing if an INT is negative (when accessing an array)
public class TYPE_INT_NEGATIVE extends TYPE{
    /**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static TYPE_INT_NEGATIVE instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected TYPE_INT_NEGATIVE() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static TYPE_INT_NEGATIVE getInstance()
	{
		if (instance == null)
		{
			instance = new TYPE_INT_NEGATIVE();
			instance.name = "int_NEGATIVE";
		}
		return instance;
	}
}
