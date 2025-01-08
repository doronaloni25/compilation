package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperFunctions.HelperFunctions;
public class AST_EXP_BINOP extends AST_EXP
{
	public AST_BINOP OP;
	public AST_EXP left;
	public AST_EXP right;
	
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
}
