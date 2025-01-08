package HelperFunctions;

import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import TYPES.*;
import AST.*;
import SYMBOL_TABLE.*;

public class HelperFunctions{
    
    public static PrintWriter file_writer = null;

    public static void setFileWriter(PrintWriter writer) {
        HelperFunctions.file_writer = writer;
    }

    public static boolean isInhiritedFromOrNil(TYPE t1, TYPE t2) {
        if (t1 == null && t2 == null){
            return true;
        }
        if (t1 == null || t2 == null){
            return true;
        }
        System.out.print("inhertince check, t1 name is " + t1.name);
        System.out.println("; t2 name is " + t2.name);
        if (t1.name.equals(t2.name)) {
            System.out.println("inhertince check, t1 and t2 has same name");
            return true;
        }
        if (t1 == TYPE_NIL.getInstance() && t2 != TYPE_STRING.getInstance() && t2 != TYPE_INT.getInstance()) {
            return true;
        }
        if (t1.isClass()){
            System.out.println("unlucky");
        }
        if (t1.isClassDec()) {
            System.out.println("inhertince t1's father is " + (((TYPE_CLASS_DEC) t1).father).name);
            return isInhiritedFromOrNil(((TYPE_CLASS_DEC) t1).father, t2);
        }
        return false;
    }

    public static boolean compareTypeLists(TYPE_LIST t1, TYPE_LIST t2) {
        // Check if the arguments of the function are the same
        if (t1 == null && t2 == null) {
            return true;
        }
        //not the same length
        if (t1 == null || t2 == null) {
            
            return false;
        }
        //arguments not the same type or not inherited
        if (isInhiritedFromOrNil(t1.head, t2.head) == false) {
            return false;
        }
        return compareTypeLists(t1.tail, t2.tail);
    }

    public static boolean isConstant(AST_EXP exp){
        return (exp instanceof AST_EXP_INT || exp instanceof AST_EXP_STRING || exp instanceof AST_EXP_NIL);   
    }

    public static void printError(int line, String name)
    {
        SYMBOL_TABLE.getInstance().PrintMe();
        System.out.print("Error");
        System.out.println(" at " + name);
        System.out.println("row = " + Integer.toString(line));
        file_writer.write("ERROR");
		file_writer.write("(");
		file_writer.write(Integer.toString(line));
		file_writer.write(")\n");		
		file_writer.close();
		System.exit(0);
    }

   
}
