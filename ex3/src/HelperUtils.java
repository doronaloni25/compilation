import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;

public static boolean isEnhiritedFromOrNil(TYPE t1, TYPE t2) {
    // Check if t1 is enherited from t2
    if (t1 == null || t2 == null) {
        return false;
    }
    if (t1 == t2) {
        return true;
    }
    if (t1 == TYPE_NIL.getInstance()) {
        return false;
    }
    if (t1.isClass()) {
        return isEnhiritedFromOrNil(((TYPE_CLASS) t1).father, t2);
    }
    return false;
}