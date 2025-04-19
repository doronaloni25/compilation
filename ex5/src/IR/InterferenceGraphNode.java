package IR;
import java.util.*;
import HelperFunctions.*;
import java.util.*;

public class InterferenceGraphNode
{
    String unique_id;
    Set<InterferenceGraphNode> neighbors;
    public int color;

    public InterferenceGraphNode(String unique_id){
        this.unique_id = unique_id;
        this.neighbors = new HashSet<InterferenceGraphNode>();
        this.color = -1;
    }

    public void addNeighbor(InterferenceGraphNode neighbor){
        this.neighbors.add(neighbor);
    }

}