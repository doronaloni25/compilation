package HelperFunctions;

import java.io.*;
import java.io.PrintWriter;
import java.util.*;
import java_cup.runtime.Symbol;
import TYPES.*;
import AST.*;
import TEMP.*;
import IR.*;
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
        //System.out.print("inhertince check, t1 name is " + t1.name);
        ////System.out.println("; t2 name is " + t2.name);
        if (t1.name.equals(t2.name)) {
            ////System.out.println("inhertince check, t1 and t2 has same name");
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

    public static void printError(String msg)
    {
        file_writer.println(msg);
		file_writer.close();
		System.exit(0);
    }

    public static void addInheritedVarsToSymbolTable(TYPE_CLASS_VAR_DEC_LIST varDecs, TYPE_LIST funcList){
        TYPE_CLASS_VAR_DEC varDec;
        TYPE_FUNCTION funcDec;

        // Enter var decs to the symbol table
        int offset = 1;
        while (varDecs != null && varDecs.head != null)
        {
            varDec = varDecs.head;
            TYPE varType = varDec.t;
            String varName = varDec.name;
            ArrayList<Object> data = new ArrayList<Object>();
            set_data(data, false, false,false,false,true, offset);
            SYMBOL_TABLE.getInstance().enter(varName, varType, data);
            varDecs = varDecs.tail;
            offset++;
        }
        // Enter func decs to the symbol table
        while (funcList != null && funcList.head != null){
            funcDec = (TYPE_FUNCTION)funcList.head;
            String funcName = funcDec.name;
            //System.out.println("Entering func " + funcName + " To symbol table");
            SYMBOL_TABLE.getInstance().enter(funcName, funcDec);
            funcList = funcList.tail;
        }
    }

    // set function list as inherited (inplace) 
    public static void setFunctionListInherited(TYPE_LIST funcList)
    {
        TYPE_FUNCTION funcDec;
        while (funcList != null && funcList.head != null){
            funcDec = (TYPE_FUNCTION)funcList.head;
            funcDec.isInherited = true;
            funcList = funcList.tail;
        }
    }

    public static boolean isOverloading(TYPE_FUNCTION f, TYPE_FUNCTION fAncestor){
        //System.out.println("isOverloading over function: " + f.name + " with return type: " + f.returnType.name);
        //System.out.println("isOverloading over function: " + fAncestor.name + " with return type: " + fAncestor.returnType.name);
        if(!f.returnType.equals(fAncestor.returnType)|| !HelperFunctions.compareTypeLists(f.params, fAncestor.params) )
        {
            //System.out.println("Method overloading");
            return true;
        }
        return false;
    }

    public static void checkValidMethod(TYPE_FUNCTION function, TYPE funcCheck, TYPE_CLASS_DEC classDec, int line, String className){
        // found symbol with same name
        if (funcCheck != null){
            //System.out.println("funcheck isnt null");
            // if its a function, make sure its inherited (override, check later for no overload)
            if (funcCheck.isFunction()){
                TYPE_FUNCTION funcType = (TYPE_FUNCTION)funcCheck;
                //System.out.println("inheritence of funcCheck:" + funcType.isInherited);
                if (!funcType.isInherited){
                    HelperFunctions.printError(line, className);
                }
                else{
                    // take care of overloading
                    if (HelperFunctions.isOverloading(function, funcType))
                    {
                        HelperFunctions.printError(line, className);
                    }
                    function.classDecName = classDec.name;
                    classDec.overideAdd(function);
                }
            }
            else{
                HelperFunctions.printError(line, className);
            }
        }
        else{
            // need to insert to class function list
            function.classDecName = classDec.name;
            classDec.addFunction(function);
        }
    }

    public static String getVarNameWithDecScope(String varName){
        int decScopeNum = SYMBOL_TABLE.getInstance().findDecScopeNumber(varName);
        if (decScopeNum == -1){
            // cant happen, since this would get taken care in semantme
            return null;
        }
        return varName + "@" + Integer.toString(decScopeNum);
    }

    public static int getScopeNumFromVarWithScopeName(String varWithScopeNum)
    {
        String[] parts = varWithScopeNum.split("@");
        if (parts.length == 2) 
        {
            return Integer.parseInt(parts[1]);
        }
        else
        {
            return -1;
        }
    }

    public static void printInvalidVars(Set<String> invalidVars){
        if (invalidVars.isEmpty()){
            file_writer.println("!OK");
        }
        else{
            List<String> sortedInvalidVars = new ArrayList<String>(invalidVars);
            Collections.sort(sortedInvalidVars);
            for (String var : sortedInvalidVars){
                file_writer.println(var);
            }
        }
    }

    public static String formatExitLabel(String label){
        return "end_of_func_" + label;
    }

    public static String formatEntryLabel(String label){
        return "start_of_func_" + label;
    }
    public static String formatMethodLabel(String classDecName, String label){
        return "start_of_method_" + label + "_in_class_" + classDecName;
    }
    public static String formatMethodExitLabel(String classDecName, String label){
        return "end_of_method_" + label + "_in_class_" + classDecName;
    }

    public static String getEndLabelFromStartLabel(String label, Label_Type label_type){
        switch(label_type){
            case FUNC_START:
                String labelOnly = label.replaceFirst("start_of_func_", "");
                return formatExitLabel(labelOnly);
            case METHOD_START:
                String[] parts = label.split("_in_class_");
                String methodLabel = parts[0].replaceFirst("start_of_method_", "").trim();
                String className = parts[1].trim();
                return formatMethodExitLabel(className, methodLabel);
            default:
                return "";
        }
    }
    
    public static void set_func_or_method_argument_list_data(AST_LIST func_args, boolean isFunc, boolean isMethod)
     {
        int cnt = 0;
        while (func_args != null && func_args.getHead() != null) {
            AST_Node arg =  func_args.getHead();
            set_data(arg.data, false, isFunc, isMethod, false, false, cnt);
            cnt++;
            func_args = func_args.getTail();
        }
    }
  
   public static void set_data(ArrayList<Object> data, boolean isGlobal, boolean is_func_param, boolean is_method_param, boolean is_local_varibale, boolean is_class_field, int offset) {
        data.add(isGlobal);
        data.add(is_func_param);
        data.add(is_method_param);
        data.add(is_local_varibale);
        data.add(is_class_field);
        data.add(offset);
    }
    public static  void set_data(ArrayList<Object> data, boolean isGlobal, boolean is_func_param, boolean is_method_param, boolean is_local_varibale, boolean is_class_field) {
        data.add(isGlobal);
        data.add(is_func_param);
        data.add(is_method_param);
        data.add(is_local_varibale);
        data.add(is_class_field);
        data.add(-1);
    }
}
