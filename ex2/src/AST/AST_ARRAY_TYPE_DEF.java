package AST;

public class AST_ARRAY_TYPE_DEF extends AST_Node{
    public String name;
    public AST_TYPE type;

    public AST_ARRAY_TYPE_DEF(String name,  AST_TYPE type)
    {
        // Set a unique serial number
        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.name = name;
        this.type = type;
        System.out.format("====================== AST_ARRAY_TYPE_DEF -> ARRAY ID( %s ) EQ type(%s) LBRACK RBRACK SEMICOLON \n", name, type);
    }
}
