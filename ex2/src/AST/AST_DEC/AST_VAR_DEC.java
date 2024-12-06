package AST;

public class AST_VAR_DEC extends AST_DEC 
{
    
	public AST_TYPE type;
	public String name;


    public AST_VAR_DEC (AST_TYPE type, String name) {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.type = type;
        this.name = name;

        System.out.format("====================== varDec -> type( %s ) ID( %s ) SEMICOLON \n", type, name);
    }

    public void PrintMe() {

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

	
	
  


