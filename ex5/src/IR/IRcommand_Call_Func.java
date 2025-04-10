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

public class IRcommand_Call_Func extends IRcommand
{
	String name;
    TEMP dest;
    ArrayList<TEMP> funcArgs;
	
	public IRcommand_Call_Func(String name, TEMP dest, ArrayList<TEMP> funcArgs)
    {
        this.name = name;
        this.dest = dest;
        this.funcArgs = funcArgs;
    }

    @Override
    public Set<String> getLiveGen()
	{
        Set<String> genSet = new HashSet<String>;
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
          //store the arguments of the called function to the stack
           int numOfArgs = this.funcArgs.size();
           MIPSGenerator.getInstance().subu(sp, sp, 4 * numOfArgs);
           for (int i = 0; i < numOfArgs; i++){
                TEMP arg = this.funcArgs.get(i);
                MIPSGenerator.getInstance().sw(arg ,sp, 4 * i);
           }
        //jump to the function
        MIPSGenerator.getInstance().jal(this.name);
        //go back to prev sp (before storing the args, we discard them)
        MIPSGenerator.getInstance().addiu(sp, sp, 4 * numOfArgs);
        //store the return value in the destination register (if it exists)
        if (this.dest != null){
            MIPSGenerator.getInstance().move(this.dest, v0);
        }
}



