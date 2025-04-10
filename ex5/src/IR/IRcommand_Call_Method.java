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

public class IRcommand_Call_Method extends IRcommand
{
	
    TEMP dest;
    TEMP object;
    String name;
    ArrayList<TEMP> funcArgs;
	int offset;

	public IRcommand_Call_Method(TEMP dest, TEMP object, String name, ArrayList<TEMP> funcArgs, int offset)
    {
        this.dest = dest;
        this.object = object;
        this.name = name;
        this.funcArgs = funcArgs;
        this.offset = offset;
    }

    @Override
	public Set<String> getLiveGen()
	{
		Set<String> liveGen = new HashSet<String>();
        liveGen.add(String.valueOf(object.getSerialNumber()));
        for (TEMP tArg : this.funcArgs){
            liveGen.add(String.valueOf(tArg.getSerialNumber()));
        }
		return liveGen;
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
        this.object.setRegisterNumber(interference_graph_map);
        if (this.dest != null){
            this.dest.setRegisterNumber(interference_graph_map);
        }
	}
    public void MIPSme()
    {
        TEMP v0 = new TEMP("v",0);
        TEMP sp = new TEMP("sp",-1);
        TEMP s0 = new TEMP("s",0);
        TEMP s1 = new TEMP("s",1);
        //check null reference
        MIPSGenerator.getInstance().checkNullPointer(this.object);
        //load arguments to the stack
        MIPSGenerator.getInstance().subu(sp,sp, 4*this.funcArgs.size()); 
        for (int i = 0; i < this.funcArgs.size(); i++){
            TEMP arg = this.funcArgs.get(i);
            MIPSGenerator.getInstance().sw(arg,4*i,sp);
        }

        //get the adress of the method in the class methods table
        MIPSGenerator.getInstance().subu(sp,sp,4);
        MIPSGenerator.getInstance().sw(this.object, 0, sp);
        MIPSGenerator.getInstance().lw(s0, 0, this.object);
        //s0 has the adress of the class dispach vector
        int offset = this.offset * 4;
        //s1 has the adress of the method in the class dispach vector
        MIPSGenerator.getInstance().lw(s1, offset,s0);
        //jump to the mehod(using the adress in the register)
        MIPSGenerator.getInstance().jalr(s1);
        //discard the arguments from the stack
        MIPSGenerator.getInstance().addToStack(4*this.funcArgs.size(),sp);
        //load the return value to the destination register
        if(this.dest != null)
        {
            MIPSGenerator.getInstance().move(this.dest, v0);
        }
    
    
}
}
