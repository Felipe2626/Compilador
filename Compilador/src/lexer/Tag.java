package lexer;

public class Tag {
	public final static int
		APP = 256,
		START = 257,
		STOP = 258,
		INT = 259,
		REAL = 260,
		IF = 261,
		THEN = 262,
		END = 263,
		ELSE = 264,
		REPEAT = 265,
		UNTIL = 266,
		WHILE = 267,
		DO = 268,
		READ = 269,
		WRITE = 270,
		NOT = 271, //!
		MINUS = 272, //-
		PLUS = 273, //+
		EQ = 274, //=
		GT = 275, //>
		GE = 276, //>=
		LT = 277, //<
		LE = 278, //<=
		NE = 279, //!=
		OR = 280, //||
		MULT = 281, 
		DIV = 282,
		AND = 283, //&&
		OPENCUR = 284, //{
		CLOSECUR = 285, //}
		NUM = 286,	
		ID = 287,
		BASIC = 288,
		ATT = 289, //:=
		TEMP = 290,
		LITERAL = 291,
		OPENPAR = 292,
		CLOSEPAR = 293,
	    COMMA = 294, // ,
		DOTCOMMA = 295;//;
}
