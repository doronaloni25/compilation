package AST;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.*;
public class AST_EXP_BINOP extends AST_EXP
{
	public AST_BINOP OP;
	public AST_EXP left;
	public AST_EXP right;
	private TYPE expType;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right, AST_BINOP OP)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		this.OP = OP;
	}

	public TYPE SemantMe(){
		TYPE leftType = left.SemantMe();
		this.expType = leftType;
		TYPE rightType = right.SemantMe();
		if (leftType == null || rightType == null){
			HelperFunctions.printError(line, this.getClass().getSimpleName());
		}
		// check if op is EQ, can only compare equality (coded as 6) if the types are the same or inherit from one another
		if(OP.op == 6){
			// cant eqaute NIL with STRING
			if ((leftType == TYPE_STRING.getInstance() && rightType == TYPE_NIL.getInstance()) || (leftType == TYPE_NIL.getInstance() && rightType == TYPE_STRING.getInstance())){
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
			// if one of the types is array both should be - compare by name
			else if(leftType.isArray() || rightType.isArray()){
				if (rightType == TYPE_NIL.getInstance() || leftType == TYPE_NIL.getInstance()){
					return TYPE_INT.getInstance();
				}
				if (!(leftType.isArray() && rightType.isArray())){
                	HelperFunctions.printError(line, this.getClass().getSimpleName());
            	}
				if (!leftType.name.equals(rightType.name)){
					HelperFunctions.printError(line, this.getClass().getSimpleName());
				}
				return TYPE_INT.getInstance();
			}
			else
			if (HelperFunctions.isInhiritedFromOrNil(leftType, rightType) || HelperFunctions.isInhiritedFromOrNil(rightType, leftType)){
				return TYPE_INT.getInstance();
			}
			else{
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
		}

		// check if op is +, can be done only on int or string
		else if (OP.op == 0){
			if (leftType == TYPE_INT.getInstance() && rightType == TYPE_INT.getInstance()){
				return TYPE_INT.getInstance();
			}
			else if (leftType == TYPE_STRING.getInstance() && rightType == TYPE_STRING.getInstance()){
				return TYPE_STRING.getInstance();
			}
			else{
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
		}

		// check if op is DIVIDE, can be only done on int, and second int can't be 0
		else if (OP.op == 3){
			if (leftType == TYPE_INT.getInstance() && rightType == TYPE_INT.getInstance()){
				// If right is constant, check if its 0 and throw exception if so.
				if (HelperFunctions.isConstant(right) && ((AST_EXP_INT)right).value == 0){
					HelperFunctions.printError(line, this.getClass().getSimpleName());
				}
				return TYPE_INT.getInstance();
			}
			else{
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
		}

		// any other op, only 2 ints
		else{
			if (leftType == TYPE_INT.getInstance() && rightType == TYPE_INT.getInstance()){
				return TYPE_INT.getInstance();
			}
			else{
				HelperFunctions.printError(line, this.getClass().getSimpleName());
			}
		}
		//Unreachable code
		return TYPE_INT.getInstance();
	}
	
	@Override
	public TEMP IRme(){
		// create temp to store result of operation
		TEMP dest = TEMP_FACTORY.getInstance().getFreshTEMP();
		// get temps of left and right
		TEMP t1 = left.IRme();
		TEMP t2 = right.IRme();

		IRcommand cmd = null;
		// different IR command for every operation
		// PLUS = 0
		// MINUS = 1
		// TIMES = 2
		// DIVIDE = 3
		// LT = 4
		// GT = 5
		// EQ = 6
		switch (OP.op){
			case 0:
				//check if its string or int
				if(expType == TYPE_STRING.getInstance())
				{
					cmd = new IRcommand_Binop_Add_Strings(dest, t1, t2);
				}
				else
				{
					cmd = new IRcommand_Binop_Add_Integers(dest, t1, t2);
				}	
				break;
			case 1:
				cmd = new IRcommand_Binop_Sub_Integers(dest, t1, t2);
				break;
			case 2:
				cmd = new IRcommand_Binop_Mul_Integers(dest, t1, t2);
				break;
			case 3:
				cmd = new IRcommand_Binop_Div_Integers(dest, t1, t2);
				break;
			case 4:
				cmd = new IRcommand_Binop_LT_Integers(dest, t1, t2);
				break;
			case 5:
				cmd = new IRcommand_Binop_GT_Integers(dest, t1, t2);
				break;
			case 6:
				if(expType == TYPE_INT.getInstance())
				{
					cmd = new IRcommand_Binop_EQ_Integers(dest, t1, t2);
				}
				else if(expType == TYPE_STRING.getInstance())
				{
					cmd = new IRcommand_Binop_EQ_Strings(dest, t1, t2);
				}
				else if(expType.isArray())
				{
					cmd = new IRcommand_Binop_EQ_Arrays(dest, t1, t2);
				}
				else
				{
					cmd = new IRcommand_Binop_EQ_Objects(dest, t1, t2);
				}
				
				break;
		}
		// add command to IR
		IR.getInstance().Add_IRcommand(cmd);
		// return temp that holds the result of the operation
		return dest;
	}


}
