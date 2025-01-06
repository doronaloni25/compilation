package AST;
import TYPES.*;
public class AST_COMMA_TYPE_ID extends AST_Node {
    
    public AST_TYPE type; 
    public String name;

    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
    public AST_COMMA_TYPE_ID(AST_TYPE type,  String name) {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();
        
        System.out.format("----------AST_COMMA_TYPE_ID -> COMMA type ID\n");
        
        this.type = type;
        this.name = name;
    }
}