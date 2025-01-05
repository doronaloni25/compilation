package AST;
import org.jcp.xml.dsig.internal.dom.Utils;
import TYPES.*;
import HelperUtils;

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
		// check if op is EQ, can only compare equality (coded as 6) if the types are the same or inherit from one another
		if(OP.op == 6){
			// cant eqaute NIL with STRING
			if ((leftType == TYPE_STRING.getInstance() && rightType == TYPE_NIL.getInstance()) || (leftType == TYPE_NIL.getInstance() && rightType == TYPE_STRING.getInstance())){
				// TODO: throw exception
			}
			else
			if (HelperUtils.isInhiritedFromOrNil(leftType, rightType) || HelperUtils.isInhiritedFromOrNil(rightType, leftType)){
				return TYPE_INT.getInstance();
			}
			else{
				// TODO: throw exception
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
				// TODO: throw exception
			}
		}

		// check if op is DIVIDE, can be only done on int, and second int can't be 0
		else if (OP.op == 3){
			// TODO: check if right is 0
			if (leftType == TYPE_INT.getInstance() && rightType == TYPE_INT.getInstance()){
				return TYPE_INT.getInstance();
			}
			else{
				// TODO: throw exception
			}
		}

		// any other op, only 2 ints
		else{
			if (leftType == TYPE_INT.getInstance() && rightType == TYPE_INT.getInstance()){
				return TYPE_INT.getInstance();
			}
			else{
				// TODO: throw exception
			}
		}
		
	}
}
