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
		System.out.format("====================== program -> declist\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.dl = dl;
		
	}


}

