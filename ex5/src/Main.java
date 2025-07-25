   
import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import HelperFunctions.*;
import java.util.*;
import AST.*;
import IR.*;
import MIPS.*;

public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		AST_PROGRAM AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);
			HelperFunctions.setFileWriter(file_writer);
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
			
			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new Parser(l, file_writer);

			/***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
			AST = (AST_PROGRAM) p.parse().value;
			
			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();

			/**************************/
			/* [7] Semant the AST ... */
			/**************************/
			AST.SemantMe();

			/**********************/
			/* [8] IR the AST ... */
			/**********************/
			AST.IRme();

			ControlFlowGraph cfg = IR.getInstance().createCFG();
			Set<String> invalidVars = cfg.getInvalidVars();
			
			/***********************/
			/* [9] MIPS the IR ... */
			/***********************/
			// TODO: remove this line (probably?)
			MIPSGenerator.createMipsOutputfile(outputFilename);
			IR.getInstance().MIPSme();			

			/***************************/
			/* [11] Finalize MIPS file */
			/***************************/
			MIPSGenerator.getInstance().finalizeFile();			

			/**************************/
			/* [12] Close output file */
			/**************************/
			file_writer.close();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


