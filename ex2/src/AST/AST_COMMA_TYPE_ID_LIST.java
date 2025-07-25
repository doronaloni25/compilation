package AST;


public class AST_COMMA_TYPE_ID_LIST extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_COMMA_TYPE_ID head;
	public AST_COMMA_TYPE_ID_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_COMMA_TYPE_ID_LIST(AST_COMMA_TYPE_ID head, AST_COMMA_TYPE_ID_LIST tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) System.out.print("====================== AST_COMMA_TYPE_ID -> AST_COMMA_TYPE_ID AST_COMMA_TYPE_ID_LIST\n");
		if (tail == null) System.out.print("====================== AST_COMMA_TYPE_ID_LIST -> AST_COMMA_TYPE_ID      \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
	}
}