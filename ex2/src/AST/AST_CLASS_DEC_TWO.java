package AST;


public class AST_CLASS_DEC_TWO extends AST_CLASS_DEC_ONE 
{
    
	public String nameTwo;


    public AST_CLASS_DEC_TWO ( String name, String nameTwo, AST_CFIELD_LIST cFieldList) {
        super(name, cFieldList);

        this.nameTwo = nameTwo;
        System.out.format("====================== ClassDec -> CLASS ID LBRACE cFieldList RBRACE \n");
    }
}