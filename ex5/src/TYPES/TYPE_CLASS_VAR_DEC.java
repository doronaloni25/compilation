package TYPES;
import AST.*;

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

	private void getDefaultValue(AST_EXP exp){
		if (exp instanceof AST_EXP_INT){
			AST_EXP_INT exp_int = (AST_EXP_INT)exp;
			this.defaultIntValue = exp_int.value;
		}
		else if(exp instanceof AST_EXP_STRING){
			this.defaultStringLabel = ((AST_EXP_STRING)exp).stringLabel;
		}
		else if(exp instanceof AST_EXP_NIL){
			this.defaultIntValue = 0;	
		}
		// TODO: remove..
		else{
			System.out.println("wtf... var dec is wrong type for some reason unlucky, the name is: " + name);
		}
	}
	public String toString()
	{
		return "my field name is: " + this.name;
	}

	public TYPE_CLASS_VAR_DEC copy(){
		TYPE_CLASS_VAR_DEC copied = new TYPE_CLASS_VAR_DEC(this.t, this.name);
		copied.defaultIntValue = this.defaultIntValue;
		copied.defaultStringLabel = this.defaultStringLabel;
		return copied;
	}
}
