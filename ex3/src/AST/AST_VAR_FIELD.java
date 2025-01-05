package AST;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_VAR var;
	public String fieldName;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(AST_VAR var,String fieldName)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> var DOT ID\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.fieldName = fieldName;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.print("AST NODE FIELD VAR\n");

		/**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
		/**********************************************/
		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",fieldName);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FIELD\nVAR\n...->%s",fieldName));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}
	@Override
	public TYPE SemantMe() {
		varType = var.SemantMe();
		if(vartype == null) {
			//TODO- throw exception, the identifier is not defined
		}
		//make sure the variable is a class, so you can access its fields
		if(varType.isClass()) {
			TYPE_CLASS classType = (TYPE_CLASS)varType;
		}
		else {
			//TODO- throw exception, the variable is not a class
		}
		for(TYPE_LIST dataMembers = classType.data_members; dataMembers!=null; dataMembers = dataMembers.tail)
			{
				TYPE_CLASS_VAR_DEC var_dec = (TYPE_CLASS_VAR_DEC)dataMembers.head;
				if(varDec.name.equals(fieldName)) {
					return varDec.t;
				}
		
			}
		}
		//TODO- throw exception, the field is not defined
		return null;
	}

