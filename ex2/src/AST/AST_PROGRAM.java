package AST;

public class AST_PROGRAM extends AST_Node{

	/****************/
	/* DATA MEMBERS */
	/****************/
	
	public AST_DEC_LIST dl;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_PROGRAM(AST_DEC_LIST dl)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== program -> declist( %s )\n",dl);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.dl = dl;
		
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE PROGRAM\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (dl!= null) dl.PrintMe();
		

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"PROGRAM\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		
	}
	
}


    // TODO: do something maybe
}
