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
            return false;
        }
        // look at classdec instead of class type
        if (t1.isClass()){
            t1 = ((TYPE_CLASS)t1).classDec;
        }
        // Special case, if one of them is type_void, return false
        if (t1 == TYPE_VOID.getInstance() || t2 == TYPE_VOID.getInstance()){
            return false;
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
        if (t1.isClassDec()) {
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

    public static void addInheritedVarsToSymbolTable(TYPE_CLASS_VAR_DEC_LIST varDecs, TYPE_LIST funcList){
        TYPE_CLASS_VAR_DEC varDec;
        TYPE_FUNCTION funcDec;
        if (varDecs.head == null || funcList.head == null){
            return;
        }
        // Enter var decs to the symbol table
        while (varDecs != null && varDecs.head != null){
            varDec = varDecs.head;
            TYPE varType = varDec.t;
            String varName = varDec.name;
            SYMBOL_TABLE.getInstance().enter(varName, varType);
            varDecs = varDecs.tail;
        }
        // Enter func decs to the symbol table
        while (funcList != null && funcList.head != null){
            funcDec = (TYPE_FUNCTION)funcList.head;
            String funcName = funcDec.name;
            SYMBOL_TABLE.getInstance().enter(funcName, funcDec);
            funcList = funcList.tail;
        }
    }

   
}
