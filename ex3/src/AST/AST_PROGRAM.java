package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;

public class AST_PROGRAM extends AST_Node{

	/****************/
	/* DATA MEMBERS */
	/****************/
	
	public AST_DEC_LIST dec_list;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_PROGRAM(AST_DEC_LIST dec_list)
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
		this.dec_list = dec_list;
		
	}
	@Override
    public TYPE SemantMe() 
	{
        return this.dec_list.SemantMe();
    }



}

