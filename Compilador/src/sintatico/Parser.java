package sintatico;

import java.io.FileReader;
import java.io.IOException;
import java.security.Identity;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;
import symbols.Env;

public class Parser {
	Lexer lex;
	Env env = new Env();
	int tok;
	public Parser(FileReader fr) {
		// TODO Auto-generated constructor stub
		env = new Env();
		lex  = new Lexer(fr,env);
	}


	void getToken() {//Get next token
		try {
			tok=lex.scan().getTag();
		} catch (IOException e) {
			// TODO Auto-generated  catch block
			System.out.printf("Erro ao ler token");
			e.printStackTrace();
		}
	}
	void eat(int tag){
		 if (tok==tag) getToken();
		 else error();
	}
	
	void error() {
		System.out.printf("!Erro:Get next token!\n");
	}
	void S() {//Implement program,identifier
		getToken();
		eat(Tag.APP);
		eat(Tag.ID);
		body();
	}
	void body() {
		decllist();
		eat(Tag.START);
		stmtlist();
		eat(Tag.STOP);
	}
	void decllist(){
		do {
			if(tok==Tag.INT) {
				eat(Tag.INT);
				do {
					eat(Tag.ID);
					if(tok==Tag.DOTCOMMA) {
						eat(Tag.DOTCOMMA);
						break;
					}
					eat(Tag.COMMA);
				}while(true);
			}else if(tok==Tag.REAL) {
				eat(Tag.REAL);
				do {
					eat(Tag.ID);
					if(tok==Tag.DOTCOMMA) {
						eat(Tag.DOTCOMMA);
						break;
					}
					eat(Tag.COMMA);
				}while(true);
			}
		}while(tok!=Tag.START);
	}
	void stmtlist() {
		switch(tok) {
			case Tag.WRITE:
				eat(Tag.WRITE);eat(Tag.OPENPAR);
				simpleexpr();
				eat(Tag.CLOSEPAR);
				break;
			case Tag.READ:
				eat(Tag.READ);eat(Tag.OPENPAR);
				eat(Tag.ID);eat(Tag.CLOSEPAR);
				break;
			case Tag.IF:
				eat(Tag.IF);
				condition();
				eat(Tag.THEN);
				stmtlist();
				if(tok==Tag.END)eat(Tag.END);
				else if(tok==Tag.ELSE) {
					eat(Tag.ELSE);
					stmtlist();
					eat(Tag.END);
				}else {
					error();
				}
				break;
			case Tag.REPEAT:
				eat(Tag.REPEAT);
				stmtlist();
				stmtsuffix();
				break;
			case Tag.WHILE:
				eat(Tag.WHILE);
				condition();
				eat(Tag.DO);
				stmtlist();
				eat(Tag.END);
				break;
			case Tag.ID:
				eat(Tag.ID);
				eat(Tag.ATT);
				simpleexpr();
				break;
			default :
				error();
		}
	}
	void simpleexpr() {//?ambiguidade
		if(tok==Tag.OPENPAR||tok==Tag.NUM||tok==Tag.ID||tok==Tag.NOT||tok==Tag.MINUS)
			term();
		else {
			simpleexpr();
			addop();
			term();
		}
	}
	void factora() {
		switch(tok){
			case Tag.NOT:
				eat(Tag.NOT);
				break;
			case Tag.MINUS:
				eat(Tag.MINUS);
				break;
			default :
				factor();
		}
	}
	void factor() {
		switch(tok) {
			case Tag.ID:
				eat(Tag.ID);
				break;
			case Tag.NUM://Valores do tipo real, estão recebendo a tag da palavra reservada "real"
				eat(Tag.INT);
				break;
			case Tag.OPENPAR:
				eat(Tag.OPENPAR);
				expression();
				eat(Tag.CLOSEPAR);	
				break;
			default :
				error();
		}
	}
	void expression() {
		simpleexpr();
		if(tok==Tag.EQ||tok==Tag.GT||tok==Tag.GE
				||tok==Tag.LT||tok==Tag.LE||tok==Tag.NE) {
			relop();
			simpleexpr();
		}
		
	}
	void relop() {
		switch(tok) {
			case Tag.EQ:
				eat(Tag.EQ);
				break;
			case Tag.GT:
				eat(Tag.GT);
				break;
			case Tag.GE:
				eat(Tag.GE);
				break;
			case Tag.LT:
				eat(Tag.LT);
				break;
			case Tag.LE:
				eat(Tag.LE);
				break;
			case Tag.NE:
				eat(Tag.NE);
				break;
			default :
				error();
		}
	}
	void addop() {
		switch(tok) {
			case Tag.PLUS:
				eat(Tag.PLUS);
				break;
			case Tag.MINUS:
				eat(Tag.MINUS);
				break;
			case Tag.OR:
				eat(Tag.OR);
				break;
			default :
				error();
		}		
	}
	void mulop() {
		switch(tok) {
			case Tag.MULT:
				eat(Tag.MULT);
				break;
			case Tag.DIV:
				eat(Tag.DIV);
				break;
			case Tag.AND:
				eat(Tag.AND);
				break;
			default :
				error();
		}
	}
	void term() {//ambiguidade
		if(tok==Tag.OPENPAR||tok==Tag.ID||tok==Tag.NUM||tok==Tag.NOT||tok==Tag.MINUS)
			factora();
		else {
			term();
			mulop();
			factora();
		}
		
	}
	void condition(){
		expression();
	}
	void stmtsuffix() {
		eat(Tag.UNTIL);
		condition();
	}

	
}
