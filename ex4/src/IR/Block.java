package IR;
import HelperFunctions.*;
public class Block
{
    public String label;
    public IRcommand IRCommand;
    public Set<String> ins;
    public Set<String> outs;
    public List<Block> exitEdges;
    public List<Block> enterEdges;
    public boolean alreadyBeenThere;
    public Block(IRcommand IRCommand)
    {
        this.IRCommand = IRCommand;
        this.ins = new HashSet<String>();
        this.outs = new HashSet<String>();
        this.exitEdges = new ArrayList<Block>();
        this.enterEdges = new ArrayList<Block>();
        this.alreadyBeenThere = false
        setLabel();
    }

    public addEnterEdge(Block enterEdge)
    {
        this.enterEdges.add(enterEdge);
    }

    public addExitEdge(Block exitEdge)
    {
        this.exitEdges.add(exitEdge);
    }
    private setLabel()
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
    
}
