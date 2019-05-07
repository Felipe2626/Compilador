package lexer;

public class Word extends Token{
	public String lexeme = "";
	public Word(String s, int tag) {
		super(tag);
		lexeme = s;
	}
	public String toString() {
		return lexeme;
	}
	public static final Word
		and = new Word("&&", Tag.AND),
		start = new Word("start", Tag.START),
		stop = new Word("stop", Tag.STOP),
		ifcause = new Word("if", Tag.IF), 
		then = new Word ("then",Tag.THEN),
		end = new Word ("end",Tag.END),
		elseif = new Word ("else",Tag.ELSE),
		repeat = new Word ("repeat",Tag.REPEAT),
		doit = new Word ("do",Tag.DO),
		read = new Word ("read",Tag.READ),
		write = new Word ("write",Tag.WRITE),
		until = new Word ("until",Tag.UNTIL),		
		whileit = new Word ("while",Tag.WHILE),
		minus = new Word ("-",Tag.MINUS),
		not = new Word ("!",Tag.NOT),
		or = new Word("||", Tag.OR),
		eq = new Word("=", Tag.EQ), //usando apenas = nao ==
		ne = new Word("!=", Tag.NE),
		le = new Word("<=", Tag.LE),
		ge = new Word(">=", Tag.GE),
		app = new Word("app", Tag.APP),
	    plus = new Word ("+",Tag.PLUS),
	    mult = new Word ("*",Tag.MULT), 
	    div = new Word ("/",Tag.DIV),
	    opencur = new Word ("{",Tag.OPENCUR),
	    closecur = new Word ("}",Tag.CLOSECUR),
	    att = new Word (":=",Tag.ATT),
	    temp = new Word("t", Tag.TEMP);
		

		
		
		
		
		
		
		
		
}
