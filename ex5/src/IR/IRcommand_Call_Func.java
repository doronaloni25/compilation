 /***********/
/* PACKAGE */
/***********/
package IR;
import java.util.*;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import TYPES.*;
import MIPS.*;
import HelperFunctions.*;

public class IRcommand_Call_Func extends IRcommand
{
	String name;
    TEMP dest;
    ArrayList<TEMP> funcArgs;
    boolean calledMethodInsideClass = false;
    String className;
    TYPE_CLASS_DEC classDec;
	
	public IRcommand_Call_Func(String name, TEMP dest, ArrayList<TEMP> funcArgs)
    {
        this.name = name;
        this.dest = dest;
        this.funcArgs = funcArgs;
    }

    public IRcommand_Call_Func(String name, TEMP dest, ArrayList<TEMP> funcArgs, boolean calledMethodInsideClass, TYPE_CLASS_DEC classDec)
    {
        this.name = name;
        this.dest = dest;
        this.funcArgs = funcArgs;
        this.calledMethodInsideClass = calledMethodInsideClass;
        this.className = classDec.name;
        this.classDec = classDec;
    }

    @Override
    public String toString(){
        return "Command: call func on function: " + this.name;
    }

    @Override
    public Set<String> getLiveGen()
	{
        Set<String> genSet = new HashSet<String>();
		for (TEMP tArg : this.funcArgs){
            genSet.add(String.valueOf(tArg.getSerialNumber()));
        }
        return genSet;
	}

    @Override
    public String getLiveKill()
	{
        return (this.dest != null) ? String.valueOf(this.dest.getSerialNumber()) : null;
	}

    @Override
    public void assignRegisters(Map<String, InterferenceGraphNode> interference_graph_map){
		for (TEMP tArg : this.funcArgs){
            tArg.setRegisterNumber(interference_graph_map);
        }
        if (this.dest != null){
            this.dest.setRegisterNumber(interference_graph_map);
        }
	}
    
    @Override
    public void MIPSme()
    {
        String funcLabel;
        TEMP sp = new TEMP("sp", -1);
        if (this.calledMethodInsideClass){
            // load my dynamic self into object
            TEMP fp = new TEMP("fp", -1);
            TEMP object = new TEMP("s", 0);
            TEMP s1 = new TEMP("s", 1);
            TEMP s2 = new TEMP("s", 2);
            MIPSGenerator.getInstance().lw(object, 8, fp);

            int offset = this.classDec.getMethodOffset(this.name)*4;

            MIPSGenerator.getInstance().lw(s1, 0, object);
            //s1 has the adress of the class dispach vector

            //s2 has the adress of the method in the class dispach vector
            MIPSGenerator.getInstance().lw(s2, offset, s1);

            //store the arguments of the called function to the stack
            int numOfArgs = this.funcArgs.size();
            MIPSGenerator.getInstance().subu(sp, sp, 4 * numOfArgs);
            for (int i = 0; i < numOfArgs; i++){
                TEMP arg = this.funcArgs.get(i);
                MIPSGenerator.getInstance().sw(arg , 4 * i, sp);
            }

            MIPSGenerator.getInstance().subu(sp, sp, 4);
            MIPSGenerator.getInstance().sw(object, 0, sp);

            //jump to the mehod(using the adress in the register)
            MIPSGenerator.getInstance().jalr(s2);

            MIPSGenerator.getInstance().addiu(sp, sp, 4 * (numOfArgs+1));

            if (this.dest != null){
                TEMP v0 = new TEMP("v", 0);
                MIPSGenerator.getInstance().move(this.dest, v0);
            }   
            return;
        }   

        funcLabel = HelperFunctions.formatEntryLabel(this.name);
       
        //store the arguments of the called function to the stack
        int numOfArgs = this.funcArgs.size();
        MIPSGenerator.getInstance().subu(sp, sp, 4 * numOfArgs);
        for (int i = 0; i < numOfArgs; i++){
            TEMP arg = this.funcArgs.get(i);
            MIPSGenerator.getInstance().sw(arg , 4 * i, sp);
        }
        //jump to the function
        MIPSGenerator.getInstance().jal(funcLabel);
        //go back to prev sp (before storing the args, we discard them)
        MIPSGenerator.getInstance().addiu(sp, sp, 4 * numOfArgs);
        //store the return value in the destination register (if it exists)
        if (this.dest != null){
            TEMP v0 = new TEMP("v", 0);
            MIPSGenerator.getInstance().move(this.dest, v0);
        }
    }
}



