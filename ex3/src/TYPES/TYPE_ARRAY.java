package TYPES;

public class TYPE_ARRAY extends TYPE
{

    public TYPE type;

    public TYPE_ARRAY(TYPE type)
    {
        this.type = type;
   
    }

    @Override
    public boolean isArray(){ return true;}

    public String getType(){ return "TYPE_ARRAY";}
    
}
