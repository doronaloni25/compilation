import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;
import TYPES.*;

public static boolean isInhiritedFromOrNil(TYPE t1, TYPE t2) {
    // Check if t1 is Inherited from t2
    if (t1 == t2) {
        return true;
    }
    if (t1 == TYPE_NIL.getInstance()) {
        return true;
    }
    if (t1.isClass()) {
        return isInhiritedFromOrNil(((TYPE_CLASS) t1).father, t2);
    }
    return false;
}