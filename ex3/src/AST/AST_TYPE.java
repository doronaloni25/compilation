package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import HelperUtils.HelperUtils;
public class AST_TYPE extends AST_Node {
    
    public String type; 
    public Boolean id;

    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
    public AST_TYPE(String type, Boolean id) 
    {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();

        
        if(id) 
        {
            System.out.format("type -> ID\n");
        }else 
        {
            System.out.format("type -> (%s)\n", type);
        }

        
        this.type = type;
        this.id = id;
    }

    @Override
    public TYPE SemantMe() 
    {
        TYPE t;
        // checks void type as it is a special case
        if(type.equals("void"))
            return TYPE_VOID.getInstance();
        // checks if the type was declared before
        t = SYMBOL_TABLE.getInstance().find(type);
        if (t == null) 
        {
            HelperUtils.printError(line);
        }
        return t;
    }

    public void PrintMe() 
    {
        if (id) {
            System.out.format("AST_TYPE ID(%s)\n", type);
        } else {
            System.out.format("AST_TYPE %s \n", type);
        }

        // Print to AST GRAPHVIZ DOT file
        if (id) {
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    String.format("TYPE ID(%s)", type));
        } else {
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    String.format("TYPE %s", type));
        }

    }

}
