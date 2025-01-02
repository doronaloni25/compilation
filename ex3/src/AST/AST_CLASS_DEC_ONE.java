package AST;

public class AST_CLASS_DEC_ONE extends AST_DEC 
{
    
	public String name;
    public AST_CFIELD_LIST cFieldList;


    public AST_CLASS_DEC_ONE ( String name, AST_CFIELD_LIST cFieldList) {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.name = name;
        this.cFieldList = cFieldList;
        System.out.format("====================== ClassDec -> CLASS ID LBRACE cFieldList RBRACE \n");
    }
}

  


