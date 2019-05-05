package symbols;
import lexer.*;
public class Type extends Word {
	public int width = 0;
	public Type(String s, int tag, int w) {
		super(s,tag);
		width=w;
	}
	public static final Type 
		INTEGER = new Type("integer",Tag.BASIC,4),
		REAL = new Type("real",Tag.BASIC,8);
	
	public static boolean numeric(Type p) {
		if( p==Type.INTEGER || p== Type.REAL )return true;
		return false;
	}
	public static Type max(Type p1,Type p2) {
		if(!numeric(p1)|| !numeric(p2)) return null;
		else if (p1 == Type.REAL || p2 == Type.REAL)return Type.REAL;
		else if (p1 == Type.INTEGER || p2 == Type.INTEGER)return Type.INTEGER;
		return null;

	}
}
