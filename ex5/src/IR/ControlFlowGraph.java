package IR;
import java.util.*;
import HelperFunctions.*;

public class ControlFlowGraph
{
    public List<Block> blocks;
    public Map<String, InterferenceGraphNode> interference_graph_map;
    public Set<String> graphNodesNumbers;

    public ControlFlowGraph(IRcommandList irCommands)
    {
        this.blocks = new ArrayList<Block>();
        this.interference_graph_map = new HashMap<>();
        this.graphNodesNumbers = new HashSet<String>();
        initBlocks(irCommands);
        initEdges();
        calcInsAndOuts(this.blocks.get(0));
        for (Block block : blocks){
            livelinessAnalysis(block);
        }
        addAllNodesToInterferenceGraph(this.graphNodesNumbers);
        addInterferenceEdges();
        boolean colorable = colorGraph(this.interference_graph_map);
        if (!colorable){
            HelperFunctions.printError("Register Allocation Failed");
        }
        //this.printIRs();
        //this.printLivenessCFG();
        assignRegisters();
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
            
            // In case of Func or Method
            // at the start of a function, we want to skip the function content (edge to the end label) as well as go inside the function (an edge to the next block)
            if (currCmd instanceof IRcommand_Label && (((IRcommand_Label)currCmd).label_type == Label_Type.FUNC_START || ((IRcommand_Label)currCmd).label_type == Label_Type.METHOD_START)){
                String endLabel = HelperFunctions.getEndLabelFromStartLabel(((IRcommand_Label)currCmd).label_name ,((IRcommand_Label)currCmd).label_type);
                Block destBlock = findBlockByLabel(endLabel);
                currBlock.addExitEdge(destBlock);
                destBlock.addEnterEdge(currBlock);
            }

            // If the command is jump, add the edge to the label of destination (and not to the next block)
            // This is only for while (going from the end of the while loop to the start)
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
            // We are here in if or while (when checking the condition)
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

    // chaotic iterations for the USED-BEFORE-INITIALIZED analysis (ex 4)
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

    private void livelinessAnalysis(Block currBlock)
    {
        // The direction of edges is reversed as we go from the end to the beginning
        // so enter edge will actually be exit edge now and vise-versa
        List<Block> enterEdges = currBlock.exitEdges;
        Set<String> updatedIns = new HashSet<String>();
        Set<String> updatedOuts = new HashSet<String>();
        String kill = null;
        Set<String> gen = null;
        if(enterEdges.size() != 0)
        {
            // according to live analysis rule we take the union of all the enterEdges
            for (int i = 0; i < enterEdges.size(); i++)
            {
                updatedIns.addAll(enterEdges.get(i).liveOuts);
            }
        }
        // if we didnt change the ins we dont need to continue the recursion
        if(currBlock.alreadyBeenThereLive && updatedIns.equals(currBlock.liveIns))
        {
            return;
        }
        // indeed we visted the block! horray
        currBlock.alreadyBeenThereLive = true;
        currBlock.liveIns = updatedIns;
        
        // calculate the outs (which will be the enterEdges)
        updatedOuts.addAll(currBlock.liveIns);
        gen = currBlock.IRCommand.getLiveGen();

        // add all the temp numbers (as strings) to the graphNodesNumbers
        // we add all temp used in this block which is the gen and kill
        if (gen != null){

            this.graphNodesNumbers.addAll(gen);
            updatedOuts.addAll(gen);
        }
        kill = currBlock.IRCommand.getLiveKill();
        
        if(kill!=null)
        {
            updatedOuts.remove(kill);
            this.graphNodesNumbers.add(kill);
        }

        currBlock.liveOuts = updatedOuts;

        // recursively calculate the ins and outs for all the exit edges (see remarks at the start of function)
        for (int j = 0; j < currBlock.enterEdges.size(); j++)
        {
            livelinessAnalysis(currBlock.enterEdges.get(j));
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

    public void printLivenessCFG()
    {
        for (int i = 0; i < this.blocks.size(); i++)
        {
            Block currBlock = this.blocks.get(i);
            System.out.println("Block " + i + ":");
            System.out.println("ins: " + currBlock.liveIns);
            System.out.println("outs: " + currBlock.liveOuts);
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

    private void addAllNodesToInterferenceGraph(Set<String> names){
        for (String name : names){
            System.out.println("inserting node " + name + " into graph");
            InterferenceGraphNode node = new InterferenceGraphNode(name);
            this.interference_graph_map.put(name, node);
        }
    }

    private void addInterferenceEdges(){
        for (Block block : this.blocks){
            for (String out1 : block.liveOuts){
                for (String out2: block.liveOuts){
                    if (!out1.equals(out2)){
                        InterferenceGraphNode node1 = interference_graph_map.get(out1);
                        InterferenceGraphNode node2 = interference_graph_map.get(out2);
                        // undirected graph
                        node1.addNeighbor(node2);
                        node2.addNeighbor(node1);
                    }
                }
            }
        }
    }

    private boolean colorGraph(Map<String, InterferenceGraphNode> graph) {
        for (String key : graph.keySet()){
			System.out.println("key: " + key);
		}
        Stack<InterferenceGraphNode> stack = new Stack<>();
        Set<InterferenceGraphNode> remainingNodes = new HashSet<>(graph.values());
        int K = 10;

        // Simplify phase: remove nodes with < K neighbors
        while (!remainingNodes.isEmpty()) {
            boolean removed = false;
            Iterator<InterferenceGraphNode> iter = remainingNodes.iterator();
            while (iter.hasNext()) {
                InterferenceGraphNode node = iter.next();
                if (node.neighbors.size() < K) {
                    iter.remove();
                    stack.push(node);
                    for (InterferenceGraphNode neighbor : node.neighbors) {
                        neighbor.neighbors.remove(node);
                    }
                    removed = true;
                }
            }
            if (!removed) return false; // Graph is not k-colorable
        }

        // Coloring phase: assign colors while popping from the stack
        while (!stack.isEmpty()) {
            InterferenceGraphNode node = stack.pop();
            Set<Integer> usedColors = new HashSet<>();
            for (InterferenceGraphNode neighbor : node.neighbors) {
                if (neighbor.color != -1) usedColors.add(neighbor.color);
            }
            // Assign the lowest available color
            for (int c = 0; c < K; c++) {
                if (!usedColors.contains(c)) {
                    node.color = c;
                    break;
                }
            }
            graph.put(node.unique_id, node);
        }
        return true;
    }

    private void assignRegisters(){
        for (Block block : this.blocks){
            System.out.println("assigning registers to block: " + block.IRCommand.toString());
            block.IRCommand.assignRegisters(this.interference_graph_map);
        }
    }

    private void printIRs(){
        System.out.println("\n************************ PRINTING IRS ********************\n");
        for (Block block : this.blocks){
            System.out.println(block.toString());
        }
        System.out.println("\n************************ DONE PRINTING IRS ********************\n");
    }


}