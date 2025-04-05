/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;

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
		int idx=t.getSerialNumber();
		// fileWriter.format("\taddi $a0,Temp_%d,0\n",idx);
		fileWriter.format("\tmove $a0,Temp_%d\n",idx);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
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
		fileWriter.format("\tglobal_%s: .word 721\n",var_name);
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

	public void isInBounds(TEMP dst)
	{
		String handle_overflow_label = IRcommand.getInstance().getFreshLabel("handle_overflow");
		String handle_underfloew_label = IRcommand.getInstance().getFreshLabel("handle_underflow");
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
