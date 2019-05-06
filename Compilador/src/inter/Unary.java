package inter;
import lexer.*;
import symbols.*;

public class Unary extends Op{
	public Expr expr;
	public Unary(Token tok, Expr x) {
		super(tok, null);
		expr = x;
		type = Type.max(Type.INTEGER, expr.type);
		if(type == null) {
			error("Erro de TIPO 2");
		}
	}
	public Expr gen() {
		return new Unary(op, expr.reduce());
	}
	public String toString() {
		return op.toString()+" "+expr.toString();
	}
	
}
