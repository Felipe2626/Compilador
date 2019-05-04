package lexer;

public class Num extends Token{
	public final int value;
	public Num(int v) { //implementar distin�ao entre integer e real
		super(Tag.NUM);
		value = v;
	}
	public String toString() {
		return "" + value;
	}
}