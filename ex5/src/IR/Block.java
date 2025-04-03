package IR;
import java.util.*;
import HelperFunctions.*;
import java.util.*;
public class Block
{
    public String label;
    public IRcommand IRCommand;
    public Set<String> ins;
    public Set<String> outs;
    public Set<String> liveIns;
    public Set<String> liveOuts;
    public List<Block> exitEdges;
    public List<Block> enterEdges;
    public boolean alreadyBeenThere;
    public boolean alreadyBeenThereLive;

    public Block(IRcommand IRCommand)
    {
        this.IRCommand = IRCommand;
        this.ins = new HashSet<String>();
        this.outs = new HashSet<String>();
        this.exitEdges = new ArrayList<Block>();
        this.enterEdges = new ArrayList<Block>();
        this.alreadyBeenThere = false;
        this.alreadyBeenThereLive = false;
        this.liveIns = new HashSet<String>();
        this.liveOuts = new HashSet<String>();
        setLabel();
    }

    public void addEnterEdge(Block enterEdge)
    {
        this.enterEdges.add(enterEdge);
    }

    public void addExitEdge(Block exitEdge)
    {
        this.exitEdges.add(exitEdge);
    }
    
    private void setLabel()
    {
        if(this.IRCommand instanceof IRcommand_Label)
        {
            this.label = ((IRcommand_Label)this.IRCommand).label_name;
        }
        else
        {
            this.label = null;
        }
    }

    public String getInvalidVar(){
        if(!(this.IRCommand instanceof IRcommand_Load))
        {
            return null;
        }

        IRcommand_Load cmd = (IRcommand_Load)this.IRCommand;

        if (!this.ins.contains(cmd.var_name))
        {
            return cmd.var_name.split("@")[0];
        }
        return null;
    }

    public String toString(){
        return this.IRCommand.toString();
    }
    
}
