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
		or = new Word("||", Tag.OR),
		eq = new Word("==", Tag.EQ), //usando apenas = nao ==
		ne = new Word("!=", Tag.NE),
		le = new Word("<=", Tag.LE),
		ge = new Word(">=", Tag.GE),
		app = new Word("app", Tag.APP),
		start = new Word("start", Tag.START),
		stop = new Word("stop", Tag.STOP),
		ifcause = new Word("if", Tag.IF); 
		//continuar palavras reservadas
		
		
		
}
