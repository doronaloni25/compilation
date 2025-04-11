package TYPES;

public class TYPE_CLASS_VAR_DEC
{
	public TYPE t;
	public String name;
	public int defaultIntValue = 0;
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
			AST_EXP_INT exp_int = (AST_EXP_INT)exp;
			if (exp_int.isMinus){
				this.defaultIntValue = (-1) * exp_int.value;
			}
			else{
				this.defaultIntValue = exp_int.value;
			}
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
