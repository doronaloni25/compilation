package IR;
import java.util.*;
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
        this.printCFG();
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
            if(currBlock.label != null && currBlock.label.equals(label))
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
            IRcommand currCmd = currBlock.IRCommand;

            // If the command is return, the run is finished (no exit edges).
            if(currCmd instanceof IRcommand_FuncReturn)
            {
                continue;
            }

            // at the start of a function, we want to skip the function content (edge to the end label) as well as go inside the function (an edge to the next block)
            if (currCmd instanceof IRcommand_Label && ((IRcommand_Label)currCmd).label_name.equals("start of func main")){
                Block destBlock = findBlockByLabel("end of func main");
                currBlock.addExitEdge(destBlock);
                destBlock.addEnterEdge(currBlock);
            }

            // If the command is jump, add the edge to the label of destination (and not to the next block)
            if(currCmd instanceof IRcommand_Jump_Label)
            {
                String label = ((IRcommand_Jump_Label)currCmd).label_name;
                Block destBlock = findBlockByLabel(label);
                destBlock.addEnterEdge(currBlock);
                currBlock.addExitEdge(destBlock);
                continue;
            }

            // If the command is jump if eq to zero, add the edge to the label of destination,
            // and add the edge to the next block (in the case where the condition is false)
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
                // if next block is end of main, same as return - we want to end the run (no exit edges)
                if (nextBlock.IRCommand instanceof IRcommand_Label && ((IRcommand_Label)nextBlock.IRCommand).label_name.equals("end of func main")){
                    continue;
                }
                nextBlock.addEnterEdge(currBlock);
                currBlock.addExitEdge(nextBlock);
            }
        }    
    }

    private void calcInsAndOuts(Block currBlock)
    {
        List<Block> enterEdges = currBlock.enterEdges;
        Set<String> updatedIns = new HashSet<String>();
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
                // do intersection of all the enterEdges (if they are already initialized)
                if (enterEdges.get(i).alreadyBeenThere){
                    updatedIns.retainAll(enterEdges.get(i).outs);
                }
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
        if(currBlock.IRCommand instanceof IRcommand_Store)
        {
            kill = ((IRcommand_Store)currBlock.IRCommand).var_name;
        }
        else if(currBlock.IRCommand instanceof IRcommand_Allocate)
        {
            kill = ((IRcommand_Allocate)currBlock.IRCommand).var_name;
        }

        //we dont use kill for load as the temp was just generated, and 
        //has never been used before
        updatedOuts.addAll(currBlock.ins);
        gen = currBlock.IRCommand.getGen(currBlock.ins);
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

    public void printCFG()
    {
        for (int i = 0; i < this.blocks.size(); i++)
        {
            Block currBlock = this.blocks.get(i);
            System.out.println("Block " + i + ":");
            System.out.println("ins: " + currBlock.ins);
            System.out.println("outs: " + currBlock.outs);
            System.out.println("enter edges: " + currBlock.enterEdges);
            System.out.println("exit edges: " + currBlock.exitEdges);
            System.out.println("IRcommand: " + currBlock.IRCommand.toString());
            System.out.println();
        }
    }

    public Set<String> getInvalidVars(){
        Set<String> invalidVars = new HashSet<String>();
        for (int i = 0; i < this.blocks.size(); i++)
        {
            Block currBlock = this.blocks.get(i);
            // only for reachable blocks
            if (!currBlock.alreadyBeenThere){
                continue;   
            } 
            String invalidVar = currBlock.getInvalidVar();
            if (invalidVar != null){
                invalidVars.add(invalidVar);
            }
        }
        return invalidVars;
    }

    public void printCurrInOutsCalculation(Block currBlock, String kill, String gen, Set<String> updatedOuts){
        // a function used for debugging, prints the current block and the updated ins and outs (should be used inside calcInsAndOuts)
        System.out.println("CalcInsAndOuts of " + currBlock.toString());
        System.out.println("Updated Ins = " + currBlock.ins);
        System.out.println("kill = " + kill);
        System.out.println("gen = " + gen);
        System.out.println("Updated outs = " + updatedOuts);
        System.out.println();
    }
}