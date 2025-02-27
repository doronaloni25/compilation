package TYPES;

public class TYPE_CLASS extends TYPE
{
    // THIS CLASS REPRESENTS A CLASS INSTANCE IN THE LANGUAGE
	public TYPE_CLASS_DEC classDec;

    public TYPE_CLASS(TYPE_CLASS_DEC classDec)
    {
        this.classDec = classDec;
        this.name = classDec.name;
    }
    
    @Override
    public boolean isClass() { return true; }
}