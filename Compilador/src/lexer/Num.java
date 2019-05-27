package lexer;

public class Num extends Token{
	public final int value;
	public Num(int v) { //implementar distinçao entre integer e real
		super(Tag.VALINT);
		value = v;
	}
	public String toString() {
		return "" + value;
	}
}
