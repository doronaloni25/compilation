/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;
import HelperFunctions.*;
/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class MIPSGenerator
{
	private int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}
	public void print_int(TEMP t)
	{
		int register_name=t.getRegisterName();
		fileWriter.format("\tmove $a0,%s\n", register_name);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
		//print space character
		fileWriter.format("\tli $a0,32\n");
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");
	}
	//public TEMP addressLocalVar(int serialLocalVarNum)
	//{
	//	TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
	//	int idx = t.getSerialNumber();
	//
	//	fileWriter.format("\taddi Temp_%d,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
	//	
	//	return t;
	//}
	public void allocate(String var_name)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .word 0\n",var_name);
	}
	public void load(TEMP dst,String var_name)
	{
		// TODO: check if necessary
		int dst_reg=dst.getRegisterName();
		fileWriter.format("\tlw $%d,global_%s\n",dst_reg,var_name);
	}
	public void store(String var_name,TEMP src)
	{
		// TODO: check if necessary
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,global_%s\n",idxsrc,var_name);		
	}
	public void li(TEMP t,int value)
	{
		String t_reg = t.getRegisterName();
		fileWriter.format("\tli %s,%d\n",t_reg,value);
	}
	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		String oprnd1_reg =oprnd1.getRegisterName();
		String oprnd2_reg =oprnd2.getRegisterName();
		String dst_reg=dst.getRegisterName();
		fileWriter.format("\tadd %s,%s,%sd\n",dst_reg,oprnd1_reg,oprnd2_reg);
		this.isInBounds(dst);
	}
	public void sub(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		String dst_reg = dst.getRegisterName();
		String oprnd1_reg = oprnd1.getRegisterName();
		String oprnd2_reg = oprnd2.getRegisterName();
		fileWriter.format("\tsub %s,%s,%s\n",dst_reg,oprnd1_reg,oprnd2_reg);
		this.isInBounds(dst);
	}
	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		String dst_reg = dst.getRegisterName();
		String oprnd1_reg = oprnd1.getRegisterName();
		String oprnd2_reg = oprnd2.getRegisterName();
		fileWriter.format("\tmul %s,%s,%s\n",dst_reg,oprnd1_reg,oprnd2_reg);
		isInBounds(dst);
	}
	public void label(String inlabel)
	{
		if (inlabel.equals("main"))
		{
			fileWriter.format(".text\n");
			fileWriter.format("%s:\n",inlabel);
		}
		else
		{
			fileWriter.format("%s:\n",inlabel);
		}
	}	
	public void jump(String inlabel)
	{
		fileWriter.format("\tj %s\n",inlabel);
	}	
	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		String opernd1_reg =oprnd1.getRegisterName();
		String opernd2_reg =oprnd2.getRegisterName();
		
		fileWriter.format("\tblt %s,%s,%s\n",opernd1_reg,opernd2_reg,label);				
	}
	public void bgt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		String opernd1_reg =oprnd1.getRegisterName();
		String opernd2_reg =oprnd2.getRegisterName();
		
		fileWriter.format("\tbgt %s,%s,%s\n",opernd1_reg,opernd2_reg,label);				
	}
	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		String opernd1_reg =oprnd1.getRegisterName();
		String opernd2_reg =oprnd2.getRegisterName();
		
		fileWriter.format("\tbge %s,%s,%s\n",oprnd1_reg,opernd2_reg,label);				
	}
	public void ble(TEMP oprnd1,TEMP oprnd2,String label)
	{
		String opernd1_reg =oprnd1.getRegisterName();
		String opernd2_reg =oprnd2.getRegisterName();
		
		fileWriter.format("\tble %s,%s,%s\n",opernd1_reg,opernd2_reg,label);				
	}
	public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		String opernd1_reg =oprnd1.getRegisterName();
		String opernd2_reg =oprnd2.getRegisterName();
		
		fileWriter.format("\tbne %s,%s,%s\n",opernd1_reg,opernd2_reg,label);				
	}
	public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		String opernd1_reg =oprnd1.getRegisterName();
		String opernd2_reg =oprnd2.getRegisterName();
		
		fileWriter.format("\tbeq %s,%s,%s\n",opernd1_reg,opernd2_reg,label);				
	}
	public void beqz(TEMP oprnd1,String label)
	{
		String opernd =oprnd1.getRegisterName();
		fileWriter.format("\tbeq %s,$zero,%s\n",opernd,label);				
	}
	
	public void move(TEMP dest, TEMP src){
		String src_reg = src.getRegisterName();
		String dest_reg = dest.getRegisterName();
		fileWriter.format("\tmove %s,%s\n", dest_reg, src_reg);
	}

	// allocated address is returned in $v0
	public TEMP malloc(TEMP size){
		TEMP v0 = new TEMP("v", 0);
		move(new TEMP("a", 0), size);
		fileWriter.format("\tli %s,9\n",v0.getRegisterName());
		fileWriter.format("\tsyscall\n");
		return v0;
	}

	public void addiu(TEMP dest, TEMP src, int unsinged_value){
		String src_reg = src.getRegisterName();
		String dest_reg = dest.getRegisterName();
		fileWriter.format("\taddiu %s,%s,%d\n", dest_reg, src_reg, unsinged_value);
	}

	

	public void sw(TEMP src, int offset, TEMP address){
		String src_reg = src.getRegisterName();
		String address_reg = address.getRegisterName();
		fileWriter.format("\tsw %s,%d(%s)\n", src_reg, offset, address_reg);
	}

	public TEMP calcStringLen(TEMP str){
		// We create MIPS code that loops over the string in order to calculate its length
		TEMP s7 = new TEMP("s", 7);
		TEMP s8 = new TEMP("s", 8);
		TEMP s9 = new TEMP("s", 9);
		String start_of_loop = IRcommand.getFreshLabel("loop_string_start");
		String end_of_loop = IRcommand.getFreshLabel("loop_string_end");

		// S7 will hold the address of curr char (in order to progress along the string)
		// S8 will hold current char - init with the first byte from the pointer (each char is one byte).
		// S9 will hold length so far - init with 0.
		move(s7, str);
		li(s9, 0);

		// start the loop
		label(start_of_loop); 
		lb(s8, 0, s7); 
		// if curr char is terminating (0), exit loop.
		beqz(s8, end_of_loop);
		// else, add 1 to length and to adress(point to next char in string)
		addiu(s9, s9, 1);
		addiu(s7, s7, 1);
		jump(start_of_loop);
		label(end_of_loop);
		// when done - return temp that holds the current length
		return s9;
	}

	public void label(String label_name){
		if (label_name.equals(HelperFunctions.formatEntryLabel("main"))){
			fileWriter.format(".globl %s\n%s:\n", label_name, label_name);
		}
		else{
			fileWriter.format("%s:\n", label_name);
		}
	}

	public void strcpy(TEMP dest, TEMP src){
		// we assume len(dest) >= len(src)
		TEMP s7 = new TEMP("s", 7);
		TEMP s8 = new TEMP("s", 8);
		TEMP s9 = new TEMP("s", 9);
		String start_of_loop = IRcommand.getFreshLabel("loop_string_start");
		String end_of_loop = IRcommand.getFreshLabel("loop_string_end");

		// S7 will hold curr char adress for src
		// S8 will hold curr char for src
		// S9 will hold curr char adress for dest
		move(s7, src);
		move(s9, dest);

		label(start_of_loop);
		lb(s8, 0, s7);
		// if curr char is terminating (0), exit loop.
		beqz(s8, end_of_loop);
		// else, write curr char to curr char address in dest (s9)
		sb(s8, 0, s9); 
		// increment both pointers, and continue the loop
		addiu(s7, s7, 1);
		addiu(s9, s9, 1);
		jump(start_of_loop);
		label(end_of_loop);
	}

	public void lb(TEMP dest, int offset, TEMP src){
		String dest_reg = dest.getRegisterName();
		String src_reg = src.getRegisterName();
		fileWriter.format("\tlb %s,%d(%s)\n", dest_reg, offset, src_reg);
	}

	public void sb(TEMP src, int offset, TEMP dest){
		String dest_reg = dest.getRegisterName();	
		String src_reg = src.getRegisterName();
		fileWriter.format("\tsb %s,%d(%s)\n", src_reg, offset, dest_reg);
	}

	public void lw(TEMP dest,  int offset, TEMP address){
		String dest_reg = dest.getRegisterName();
		String address_reg = address.getRegisterName();
		fileWriter.format("\tlw %s,%d(%s)\n", dest_reg, offset, address_reg);
	}
	public void jr(TEMP address){
		String address_reg = address.getRegisterName();
		fileWriter.format("\tjr %s\n", address_reg);
	}
	public void addToStack(int offset, TEMP sp){
		if(offset>0)
		{
			String sp_reg = sp.getRegisterName();
			fileWriter.format("\taddiu %s,%s,%d\n", sp_reg, sp_reg, offset);
		}
	}
	public void subu(TEMP dest, TEMP src, int offset){
		{
			String sp_reg = sp.getRegisterName();
			fileWriter.format("\tsubu %s,%s,%d\n", sp_reg, sp_reg, offset);
		}
		
	}
	public void pushRegistersToStack(TEMP sp){
		for (int i = 0; i <10; i++)
		{
			//TODO: make sure that the add and sub are correct
			TEMP tempReg = new TEMP("t", i);
			this.subu(sp, sp, 4);
			this.sw(tempReg, 0, sp);
		}
	}
	public void popRegistersFromStack(TEMP sp){
		for (int i = 0; i <10; i++)
		{
			TEMP tempReg = new TEMP("t", i);
			this.lw(tempReg, 0, sp);
			this.addToStack(4, sp);
		}
	}

	public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2){
		String div_by_zero_label = IRcommand.getFreshLabel("handle_div_by_zero");
		String no_div_by_zero_label = IRcommand.getFreshLabel("skip_div_by_zero");
		String oprnd1_reg = oprnd1.getRegisterName();
		String oprnd2_reg = oprnd2.getRegisterName();
		String dst_reg = dst.getRegisterName();
		// if second operand is 0, jump to div by zero handler.
		beqz(oprnd2, div_by_zero_label);
		// else, skip it
		jump(no_div_by_zero_label);

		label(div_by_zero_label);
		// print string with the illegal div label as defined in constructor
		// and exit - as defined in exercise.
		printString("string_illegal_div_by_0");
		exit();
		
		// divide oprnd1/oprnd2
		label(no_div_by_zero_label);
		fileWriter.format("\tdiv %s,%s\n", oprnd1_reg, oprnd2_reg);
		// get quotient (special SPIM register)
		fileWriter.format("\tmflo %s\n",dst_reg);
		isInBounds(dst);
	}

	public void exit(){
		li(new TEMP("v", 0), 10);
		fileWriter.format("\tsyscall\n");
	}

	public void printString(String label){
		TEMP v0 = new TEMP("v", 0);
		TEMP a0 = new TEMP("a", 0);
		// load string label
		la(a0, label);
		li(v0, 4);
		fileWriter.format("\tsyscall\n");
	}

	public void la(TEMP dst, String label){
		dst_reg = dst.getRegisterName();
		fileWriter.format("\tla %s,%s\n", dst_reg, label);
	}

	public void isInBounds(TEMP dst)
	{
		String handle_overflow_label = IRcommand.getInstance().getFreshLabel("handle_overflow");
		String handle_underflow_label = IRcommand.getInstance().getFreshLabel("handle_underflow");
		String valid_label = IRcommand.getInstance().getFreshLabel("valid");
		TEMP s8  = new TEMP("s", 8);
		TEMP s9 = new TEMP("s", 9);
		
		this.li(s8, 32767);
		this.li(s9, -32767);
		//check if is bigger than 2^15
		this.bge(dst, s8, handle_overflow_label);
		//check if smaller than -2^15
		this.ble(dst, s9, handle_underfloew_label);
		//handling overflow
		this.label(handle_overflow_label);
		this.li(dst,32767);
		this.jump(valid_label);
		//handling underflow
		this.label(handle_underfloew_label);
		this.li(dst,-32767);
		//finished
		this.label(valid_label);


	}

	public void storeTempsToStack()
	{
		TEMP sp = new TEMP("sp", -1);
		for (int i = 0; i < 10; i++)
		{
			TEMP tempReg = new TEMP("t", i);
			this.subu(sp, sp, 4);
			this.sw(tempReg, 0, sp);
		}
	}

	public void constString(String label, String text)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\t%s: .asciiz \"%s\"\n", label, text);
		fileWriter.format(".text\n");
	}
	public void allocateDispatchVector(String name, TYPE_LIST function_list)
	{
		String name_dispatch_vector = String.format("%s_dispatch_vector", name);
		fileWriter.format(".data\n");
		fileWriter.format("\t%s:\n", name_dispatch_vector);
		for(TYPE_LIST curr = function_list; curr != null; curr = curr.tail)
		{
			fileWriter.format("\t.word %s\n", HelperFunctions.formatEntryLabel(curr.head.name));
		}
		fileWriter.format(".text\n");
	}

	public void jalr(TEMP dst){
		file_writer.format("\tjalr %s\n", dst.getRegisterName());
	}

	public void checkNullPointer(TEMP pointer){
		String handle_null_pointer = IRcommand.getFreshLabel("null_pointer");
		String valid_pointer = IRcommand.getFreshLabel("valid_pointer");

		beqz(pointer, handle_null_pointer);
		jump(valid_pointer);

		label(handle_null_pointer);
		printString("string_invalid_ptr_dref");
		exit();

		label(valid_pointer);
	}

	public void checkIndexInBounds(TEMP arr, TEMP index)
	{
		TEMP zero = new TEMP("zero", -1);
		TEMP s9 = new TEMP("s", 9);
		String error = IRcommand.getFreshLabel("index_out_of_bounds");
		String all_good = IRcommand.getFreshLabel("all_good");
		// first check if index is negative
		blt(index, zero, error);
		// get array size into s9
		lw(s9, 0, arr);
		//check if index is bigger than array size
		bge(index, s1, error);
		// if we reached here all is good
		jump(all_good);
		// Take care of invalid index
		label(error);
		printString("string_access_violation");
		exit();
		label(all_good);
	}

	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static MIPSGenerator instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected MIPSGenerator() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static MIPSGenerator getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new MIPSGenerator();

			try
			{
				/*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
				/*********************************************************************************/
				String dirname="./output/";
				String filename=String.format("MIPS.txt");

				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				instance.fileWriter = new PrintWriter(dirname+filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			/*****************************************************/
			/* [3] Print data section with error message strings */
			/*****************************************************/
			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
			instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
		}
		return instance;
	}
}
