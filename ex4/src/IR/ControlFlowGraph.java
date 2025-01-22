package IR;
import HelperFunctions.*;

public class ControlFlowGraph
{
    public List<Block> blocks;

    public ControlFlowGraph(IRcommandList irCommands)
    {
        this.blocks = new ArrayList<Block>();
        initBlocks(irCommands);
        initEdges();
        calcInsAndOuts(this.blocks.get(0));
    }

    public void addBlock(Block block)
    {
        this.blocks.add(block);
    }
    
    private void initBlocks(IRcommandList irCommands)
    {
        // add all commands to the cfg
        while(irCommands != null)
        {
            Block block = new Block(irCommands.head);
            addBlock(block);
            irCommands = irCommands.tail;
        }
    }
    private Block findBlockByLabel(String label)
    {
        for (int i = 0; i < this.blocks.size(); i++)
        {
            Block currBlock = this.blocks.get(i);
            // if block is of type label we have label otherwise label is null
            if(currBlock.label.equals(label))
            {
                return currBlock;
            }
        }
        return null;
    }
    private void initEdges()
    {
        for (int i = 0; i < this.blocks.size(); i++)
        {
            Block currBlock = this.blocks.get(i);
            IRcommand currCmd = currBlock.irCommand;

            // If the command is return, continue from end of main
            // TODO: take care of all function returns, not only main
            if(currCmd instanceof IRcommand_FuncReturn)
            {
                Block destBlock = findBlockByLabel("end of main");
                currBlock.addExitEdge(destBlock);
                destBlock.addEnterEdge(currBlock);
                continue;
            }
            // If the command is jump, add the edge to the label of destination 
            if(currCmd instanceof IRcommand_Jump_Label)
            {
                String label = ((IRcommand_Jump_Label)currCmd).label_name;
                Block destBlock = findBlockByLabel(label);
                destBlock.addEnterEdge(currBlock);
                currBlock.addExitEdge(destBlock);
                continue;
            }
            // If the command is jump if eq to zero, add the edge to the label of destination,
            // and add the edge to the next block
            if(currCmd instanceof IRcommand_Jump_If_Eq_To_Zero)
            {
                String label =((IRcommand_Jump_If_Eq_To_Zero)currCmd).label_name;
                Block destBlock = findBlockByLabel(label);
                destBlock.addEnterEdge(currBlock);
                currBlock.addExitEdge(destBlock);
                // we add the edge to the next block anyways later
            }
            // in any other case add the edge to the next block
            if(i+1 < this.blocks.size())
            {
                Block nextBlock = this.blocks.get(i+1);
                nextBlock.addEnterEdge(currBlock);
                currBlock.addExitEdge(nextBlock);
            }
        }    
    }

    private void calcInsAndOuts(Block currBlock)
    {
        List<Block> enterEdges = currBlock.enterEdges;
        List<Block> updatedIns = new ArrayList<Block>();
        Set<String> updatedOuts = new HashSet<String>();
        String kill = null;
        String gen = null;
        if(enterEdges.size() != 0)
        {
            // to make sure every code path has initialized variables we intersect the ins of all the enterEdges
            // enter the first one in order to calculate the intersection of all the enterEdges
            updatedIns.addAll(enterEdges.get(0).outs);
            for (int i = 1; i < enterEdges.size(); i++)
            {
                // do intersection of all the enterEdges
                updatedIns.retainAll(enterEdges.get(i).outs);
            }
        }
        // if we didnt change the ins we dont need to continue the recursion
        if(currBlock.alreadyBeenThere && updatedIns.equals(currBlock.ins))
        {
            return;
        }
        currBlock.alreadyBeenThere = true;
        currBlock.ins = updatedIns;
        // calculate the outs 
        if(currBlock.IRcommand instanceof IRcommand_Store)
        {
            kill = ((IRcommand_Store)currBlock.IRcommand).var_name;
        }
        else if(currBlock.IRcommand instanceof IRcommand_Allocate)
        {
            kill = ((IRcommand_Allocate)currBlock.IRcommand).var_name;
        }
        //we dont use kill for load as the temp was just generated, and 
        //has never been used before
        updatedOuts.addAll(currBlock.ins);
        gen = currBlock.IRcommand.getGen(currBlock.ins);
        if(kill!=null)
        {
            updatedOuts.remove(kill);
        }
        if(gen!=null)
        {
            updatedOuts.add(gen);
        }
        currBlock.outs = updatedOuts;
        // recursively calculate the ins and outs for all the exit edges
        for (int j = 0; j < currBlock.exitEdges.size(); j++)
        {
            calcInsAndOuts(currBlock.exitEdges.get(j));
        }
    }

    public Set<String> getInvalidVars(){
        Set<String> invalidVars = new HashSet<String>();
        for (int i = 0; i < this.blocks.size(); i++)
        {
            Block currBlock = this.blocks.get(i);
            String invalidVar = currBlock.getInvalidVar();
            if (invalidVar != null){
                invalidVars.add(invalidVar);
            }
        }
        return invalidVars;
    }
}