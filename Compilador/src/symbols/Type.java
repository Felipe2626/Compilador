package symbols;
import lexer.*;
public class Type extends Word {
	public int width = 0;
	public Type(String s, int tag, int w) {
		super(s,tag);
		width=w;
	}
	public static final Type 
		Integer = new Type("integer",Tag.BASIC,4),
		Real = new Type("real",Tag.BASIC,8);
	
	public static boolean numeric(Type p) {
		if( p==Type.Integer || p== Type.Real )return true;
		return false;
	}
	public static Type max(Type p1,Type p2) {
		if(!numeric(p1)|| !numeric(p2)) return null;
		else if (p1 == Type.Real || p2 == Type.Real)return Type.Real;
		else if (p1 == Type.Integer || p2 == Type.Integer)return Type.Integer;
		return null;

	}
}
