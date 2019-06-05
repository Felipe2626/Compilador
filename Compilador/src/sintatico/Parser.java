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
	Token tok;
	int linha;
	public Parser(FileReader fr) {
		// TODO Auto-generated constructor stub
		env = new Env();
		lex  = new Lexer(fr,env);
		linha=0;
	}


	void getToken() {//Get next token
		try {
			tok=lex.scan();
			linha=lex.getline();
		} catch (IOException e) {
			// TODO Auto-generated  catch block
			System.out.printf("Erro lexico");
			System.exit(0);
			//e.printStackTrace();
		}
	}
	void eat(int tag){
		 if(tag==Tag.EOF) {
			 System.out.printf("\nLeitura encerrada");
		 }
		 if (tok.getTag()==tag) getToken();
		 else error();
	}
	
	void error() {
		System.out.printf("Erro sintatico, token inesperado: '%s' - linha %d\n",tok.toString(),linha);
		/*try {
			throw new Exception();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 System.exit(0); 
	}
	public void S() {//Implement program,identifier
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
		eat(Tag.EOF);
	}
	void decllist(){ //decl-list,decl, indenfier
		do {
			if(tok.getTag()==Tag.INT) {
				eat(Tag.INT);
				do {
					eat(Tag.ID);
					if(tok.getTag()==Tag.DOTCOMMA) {//;
						eat(Tag.DOTCOMMA);
						break;
					}
					eat(Tag.COMMA);
				}while(true);
			}else if(tok.getTag()==Tag.REAL) {
				eat(Tag.REAL);
				do {
					eat(Tag.ID);
					if(tok.getTag()==Tag.DOTCOMMA) {
						eat(Tag.DOTCOMMA);
						break;
					}
					eat(Tag.COMMA);
				}while(true);
			}
			
			
		}while(tok.getTag()!=Tag.START);
	}
	void stmtlist() {
		switch(tok.getTag()) {
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
				if(tok.getTag()==Tag.END)eat(Tag.END);
				else if(tok.getTag()==Tag.ELSE) {
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
		if(tok.getTag()==Tag.DOTCOMMA) {
			eat(Tag.DOTCOMMA);
			stmtlist();
		}
		
	}
	void simpleexpr() {//?ambiguidade
		//if(tok==Tag.OPENPAR||tok==Tag.NUM||tok==Tag.ID||tok==Tag.NOT||tok==Tag.MINUS)
			
		//else {
		term();
		while(tok.getTag()==Tag.PLUS||tok.getTag()==Tag.MINUS||tok.getTag()==Tag.OR)  {
			addop();
			term();
		}
		
		//}
	}
	void term() {//ambiguidade
			factora();
			while(tok.getTag()==Tag.MULT||tok.getTag()==Tag.DIV||tok.getTag()==Tag.AND) {
				mulop();
				factora();
			}
		
	}
	void factora() {	
		switch(tok.getTag()){
			case Tag.NOT:
				eat(Tag.NOT);
				factor();
				break;
			case Tag.MINUS:
				eat(Tag.MINUS);
				factor();
				break;
			default :
				factor();
		}
	}
	void factor() {
		switch(tok.getTag()) {
			case Tag.ID:
				eat(Tag.ID);
				break;
			case Tag.NUM://Valores do tipo real, estão recebendo a tag da palavra reservada "real"
				eat(Tag.NUM);
				break;
			case Tag.OPENPAR:
				eat(Tag.OPENPAR);
				expression();
				eat(Tag.CLOSEPAR);	
				break;
			case Tag.LITERAL:
				eat(Tag.LITERAL);
				break;
			default :
				error();
		}
	}
	void expression() {
		simpleexpr();
		while(tok.getTag()==Tag.EQ||tok.getTag()==Tag.GT||tok.getTag()==Tag.GE
				||tok.getTag()==Tag.LT||tok.getTag()==Tag.LE||tok.getTag()==Tag.NE) {
			relop();
			simpleexpr();
		}
		
	}
	void relop() {
		switch(tok.getTag()) {
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
		switch(tok.getTag()) {
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
		switch(tok.getTag()) {
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

	void condition(){
		expression();
	}
	void stmtsuffix() {
		eat(Tag.UNTIL);
		condition();
	}

	
}
