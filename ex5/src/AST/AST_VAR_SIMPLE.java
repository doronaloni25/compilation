package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
import java.util.*;

public class AST_VAR_SIMPLE extends AST_VAR
{
	/************************/
	/* simple variable name */
	/************************/
	public String name;
	public String nameWithVarDecScope;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(String name)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

	}
	@Override
	public TYPE SemantMe() {
		// Make sure symbol is already defined
		TYPE type = SYMBOL_TABLE.getInstance().find(name); 
		// for IRme
		nameWithVarDecScope = HelperFunctions.getVarNameWithDecScope(name); 
		if(type == null) {
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		this.data = SYMBOL_TABLE.getInstance().find_data_by_name(name);
		return type;

	}

	@Override
	public TEMP IRme()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Load(name, t, this.data));
		return t;
	}
}