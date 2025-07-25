/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column

/*******************************************************************************/
/* Note that this has to be the EXACT same name of the class the CUP generates */
/*******************************************************************************/
%cupsym TokenNames

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup

/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; } 

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/
	public int getTokenStartPosition() { return yycolumn + 1; } 

	/******************************/
	/* checks if integer is valid */
	/******************************/
	private Symbol symbolFromNumber(String stringNum)
	{
		if(stringNum.length() > 5){
			return symbol(TokenNames.ERROR);
		}
		
		int num = new Integer(stringNum);

		if(num <= 32767)
		{
			return symbol(TokenNames.INT, num);
		}

		return symbol(TokenNames.ERROR);
	}  
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
ID				= [A-Za-z][A-Za-z0-9]*
LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t]
INTEGER			= 0 | [1-9][0-9]*
IllegalInteger  = 0[0-9]+
Bracket			= [\(\)\[\]\{\}]
TableTwoNoTerm 	= {ID} | {INTEGER} | [ \t] | {Bracket} | [\?\!\+\-\*\/\.\;]
TableTwoNoSlashNoStar 	= {ID} | {INTEGER} | [ \t] | {Bracket} | [\?\!\+\-\.\;] | {LineTerminator}
TableTwoNoStar  = {ID} | {INTEGER} | [ \t] | {Bracket} | [\?\!\+\-\.\;\/] | {LineTerminator}
StarsNoSlash	= (\*)+{TableTwoNoSlashNoStar}
NoStarORStarsNoSlash 	= {TableTwoNoStar} | {StarsNoSlash}
TableTwo        = {TableTwoNoTerm} | {LineTerminator}
CommentOne		= \/\/{TableTwoNoTerm}*{LineTerminator}
CommentTwo		= \/\*{NoStarORStarsNoSlash}*(\*)*\*\/
Comment			= {CommentOne} | {CommentTwo}
IllegalTokens	= [\,\:=\=\>\<]
LegalTokens  	= {TableTwo}*
IllegalComment	= \/\*{LegalTokens}*{IllegalTokens}+{LegalTokens}*\*\/	| \/\/{TableTwoNoTerm}*{IllegalTokens}+{TableTwoNoTerm}*{LineTerminator} | \/\*[^(\*\/)]*
STRING			= \"[A-Za-z]*\"


/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/

/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/

<YYINITIAL> {
{Comment}			{ /* just skip what was found, do nothing */ }
{IllegalComment}	{ return symbol(TokenNames.ERROR);}
"class"				{ return symbol(TokenNames.CLASS);}
"nil"				{ return symbol(TokenNames.NIL);}
"array"				{ return symbol(TokenNames.ARRAY);}
"while"				{ return symbol(TokenNames.WHILE);}
"int"				{ return symbol(TokenNames.TYPE_INT);}
"void"				{ return symbol(TokenNames.TYPE_VOID);}
"extends"			{ return symbol(TokenNames.EXTENDS);}
"return"			{ return symbol(TokenNames.RETURN);}
"new"				{ return symbol(TokenNames.NEW);}
"if"				{ return symbol(TokenNames.IF);}
"string"			{ return symbol(TokenNames.TYPE_STRING);}
"("					{ return symbol(TokenNames.LPAREN);}
")"					{ return symbol(TokenNames.RPAREN);}
"["					{ return symbol(TokenNames.LBRACK);}
"]"					{ return symbol(TokenNames.RBRACK);}
"{"					{ return symbol(TokenNames.LBRACE);}
"}"					{ return symbol(TokenNames.RBRACE);}
"+"					{ return symbol(TokenNames.PLUS);}
"-"					{ return symbol(TokenNames.MINUS);}
"*"					{ return symbol(TokenNames.TIMES);}
"/"					{ return symbol(TokenNames.DIVIDE);}
","					{ return symbol(TokenNames.COMMA);}
"."					{ return symbol(TokenNames.DOT);}
";"					{ return symbol(TokenNames.SEMICOLON);}
":="				{ return symbol(TokenNames.ASSIGN);}
"="					{ return symbol(TokenNames.EQ);}
"<"					{ return symbol(TokenNames.LT);}
">"					{ return symbol(TokenNames.GT);}
{IllegalInteger}	{ return symbol(TokenNames.ERROR);}
{INTEGER}			{ return symbolFromNumber(new String(yytext()));}
{STRING}			{ return symbol(TokenNames.STRING, new String(yytext()));}
{ID}				{ return symbol(TokenNames.ID,  new String( yytext()));}   
{WhiteSpace}		{ /* just skip what was found, do nothing */ }
<<EOF>>				{ return symbol(TokenNames.EOF);}
.					{return symbol(TokenNames.ERROR);}
}
