package sintatico;

import java.io.FileReader;
import java.io.IOException;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;
import symbols.Env;

public class Parser {
	Lexer lex;
	Env env = new Env();
	Token tok;
	public Parser(FileReader fr) {
		// TODO Auto-generated constructor stub
		env = new Env();
		lex  = new Lexer(fr,env);
	}


	void getToken() {//Get next token
		try {
			tok=lex.scan();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.printf("Erro ao ler token");
			e.printStackTrace();
		}
	}
	void eat(Token t){
		 if (tok.getTag()==t.getTag()) getToken();
		 else error();
	}
	
	void error() {
		System.out.printf("!Erro:Get next token!\n");
	}
	void S() {
	}
	void program() {
	}
	void body() {
	}
	void decllist(){ 	}
	void decl() {}
	void indentlist(){ }
	void type() { }
	void stmlist() {}
	void stmt() {}
	void assignstmt () {}
	void ifstmt() {}
	void condition () {}
	void repeatstmt() {}
	void whilestmt() {}
	void writable() {}
	void expression() {}
	void simpleexpr() {}
	void term() {}
	void fatora() {}
	void addop() {}
	void mulop() {}
	void constant() {}
	void integer_cost() {}
	void float_cost() {}
	void literal() {}
	void identifier () {}
	void letter() {}
	void digit () {}
	void caractere() {}
}
