package AST;

public class AST_VAR_DEC extends AST_DEC 
{
    
	public AST_TYPE type;
	public String name;
    public AST_EXP exp;
    public AST_NEW_EXP newExp;


    public AST_VAR_DEC (AST_TYPE type, String name, AST_EXP exp, AST_NEW_EXP newExp) {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();

        
    
        this.type = type;
        this.name = name;
        this.exp = exp;
        this.newExp = newExp;
    }
// TODO - change:


    if (id) {
            System.out.format("type -> ID(%s)\n", type);
        } else {
            System.out.format("type -> %s\n", type);
        }

    public void PrintMe() {
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

	
	
}
  


