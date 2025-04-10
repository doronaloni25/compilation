package TYPES;

public class TYPE_CLASS_VAR_DEC
{
	public TYPE t;
	public String name;
	public int defaultIntValue = -1;
	public String defaultStringLabel = null;

	public TYPE_CLASS_VAR_DEC(TYPE t,String name)
	{
		this.t = t;
		this.name = name;
	}

	public TYPE_CLASS_VAR_DEC(TYPE t,String name, AST_EXP exp)
	{
		this.t = t;
		this.name = name;
		getDefaultValue(exp);
	}

	private getDefaultValue(AST_EXP exp){
		if (t instanceof TYPE_INT){
			this.defaultIntValue = (AST_EXP_INT)exp.value;
		}
		else if(t instanceof TYPE_STRING){
			this.defaultStringLabel = (AST_EXP_STRING)exp.stringLabel;
		}
		else if(t instanceof TYPE_NIL){
			this.defaultIntValue = 0;	
		}
		// TODO: remove..
		else{
			System.out.println("wtf... var dec is wrong type for some reason unlucky, the name is: " + name);
		}
	}
}
