package TYPES;

public class TYPE_NIL extends TYPE {
    private static TYPE_NIL instance = null;

    protected TYPE_NIL() {
        this.name = "nil";
    }

    public static TYPE_NIL getInstance() {
        if (instance == null) {
            instance = new TYPE_NIL();
        }
        return instance;
    }
}