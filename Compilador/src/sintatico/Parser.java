package sintatico;

import java.awt.List;
import java.io.FileReader;
import java.io.IOException;
import java.security.Identity;
import java.util.ArrayList;
import java.util.Scanner;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;
import symbols.Env;

public class Parser {
	Lexer lex;
	Env env = new Env();
	Token tok;
	int linha;
	Scanner sc;
	String aux1,aux2;
	float num1,num2;
	ArrayList<Token> banktoks;
	int loopstatus;//0 fora de loop,1 armazenando elemento do loop;2 consumindo loop//3 armazena sem consumir
	int index;
	public Parser(FileReader fr) {
		// TODO Auto-generated constructor stub
		loopstatus=0;
		banktoks= new ArrayList<Token>();
		env = new Env();
		aux1= new String();
		aux2= new String();
		lex  = new Lexer(fr,env);
		linha=0;
		sc= new Scanner(System.in); 
	}


	void getToken() {//Get next token
		try {
			if(loopstatus!=2&&loopstatus!=3)
				tok=lex.scan();
			if(loopstatus==1) {
				banktoks.add(tok);
			}
			if(loopstatus==2) {
				tok=banktoks.get(index);
				index++;
			}
			if( loopstatus==3) {
				banktoks.add(tok);
				lex.scan();
			}
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
		System.out.printf("\nErro sintatico, token inesperado: '%s' - linha %d\n",tok.toString(),linha);
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
		env.showItens();
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
			}else {
				error();
			}
			
			
		}while(tok.getTag()!=Tag.START);
	}
	void stmtlist() {
		switch(tok.getTag()) {
			case Tag.WRITE:
				eat(Tag.WRITE);eat(Tag.OPENPAR);
				if( tok.getTag()==Tag.LITERAL)
					System.out.printf(tok.toString()+"\n");
				if( tok.getTag()==Tag.ID) {
					aux1=env.getValue(tok.toString());
					if(aux1!=null)
						System.out.printf(aux1+"\n");
					else
						errorvar(tok.toString(),linha);
				}
				simpleexpr();
				eat(Tag.CLOSEPAR);
				break;
			case Tag.READ:
				eat(Tag.READ);eat(Tag.OPENPAR);
				if(tok.getTag()==Tag.ID) {
					sc.hasNext();
				}
				aux1=sc.next();
				if(!isNumeric(aux1))
					errorvar(tok.toString(), linha);
				env.setValue(tok.toString(), aux1);
				eat(Tag.ID);
				eat(Tag.CLOSEPAR);
				break;
				
				
			case Tag.IF:
				eat(Tag.IF);
				num1=condition();
				eat(Tag.THEN);
				if(num1==1)//Se o if for verdadeiro faça a condição
					stmtlist();
				else { //Coma todos os token ate achar end ou else
					int contif=0;//Conta ifs na parte a ser ignorada 
					
					do{
						if(tok.getTag()==Tag.IF)
							contif++;
						if(tok.getTag()==Tag.END&&contif>0)
							contif--;
						getToken();
					}while((tok.getTag()!=Tag.END && tok.getTag()!=Tag.ELSE)||contif!=0); 
				}
				if(tok.getTag()==Tag.END)
					eat(Tag.END);
				else if(tok.getTag()==Tag.ELSE) {
					eat(Tag.ELSE);
					if(num1==0)//Se o if for falso faça a condição
						stmtlist();
					else { // se não coma todos os token ate o final do else
						int contif=0;//Conta ifs na parte a ser ignorada 
						do{
							if(tok.getTag()==Tag.IF)
								contif++;
							if(tok.getTag()==Tag.END&&contif>0)
								contif--;
							getToken();
							
						}while(tok.getTag()!=Tag.END || contif!=0); 
					}
					eat(Tag.END);
					}else {
						error();
					}
				
				break;
				
			case Tag.REPEAT:
				if(loopstatus==0) {
					banktoks.add(tok);
					loopstatus=1;
				}
				eat(Tag.REPEAT);
				stmtlist(); //Armazenas todas os token dentro de um loop
				//loopstatus=0;
				stmtsuffix();
				break;
				
			case Tag.WHILE:
				float rtn;
				int contopen;
				if(loopstatus==0) { 
					banktoks.add(tok);
					loopstatus=1; //Entra no estado de armazenamento
				}
				eat(Tag.WHILE);
				rtn=condition();
				if( rtn == 0 ) { //Se for falso, le todas os toks ate achar o end
					contopen=0;
					banktoks.clear();
					if(loopstatus==2) {
						loopstatus=0;

						getToken();
						break;
					}
					do{
						if(tok.getTag()==Tag.IF||tok.getTag()==Tag.WHILE)
							contopen++;
						if(tok.getTag()==Tag.END&&contopen>0)
							contopen--;
						getToken();
					}while((tok.getTag()!=Tag.END)||contopen!=0);
					eat(Tag.END);
					loopstatus=0;
					break;
				}

				eat(Tag.DO);
				
				stmtlist();
				if(loopstatus==1) {
					loopstatus=2;

					//eat(Tag.END);

					tok = new  Token(Tag.DOTCOMMA);
					break;
				}
				
				tok = new  Token(Tag.DOTCOMMA);
				index=0;
				break;
				
			case Tag.ID:
				
				aux1=tok.toString();
				aux2=env.getValue(aux1);
				if(aux2==null)
					errorvar(tok.toString(),linha);
				eat(Tag.ID);
				eat(Tag.ATT);
				env.setValue(aux1,Float.toString(simpleexpr()));
				break;

			default :
				error();
		}
		if(tok.getTag()==Tag.DOTCOMMA) {
			eat(Tag.DOTCOMMA);
			stmtlist();
		}
		
	}
	float simpleexpr() {//?ambiguidade
		int op;	
		float val1,val2;
		val1=term();
		while(tok.getTag()==Tag.PLUS||tok.getTag()==Tag.MINUS||tok.getTag()==Tag.OR)  {
			op=tok.getTag();
			addop();
			val2=term();
			switch(op) {
				case Tag.PLUS:
					val1=val1+val2;
					break;
				case Tag.MINUS:
					val1=val1-val2;
					break;
				case Tag.OR:
					if(val1==1||val2==1)
						val1=1;
					else 
						val1=0;
					break;
				}
		}
		return val1;
		//}
	}
	float term() {//ambiguidade
			float val1,val2;
			int op;
			val1=factora();
			while(tok.getTag()==Tag.MULT||tok.getTag()==Tag.DIV ||tok.getTag()==Tag.AND) {
				op=tok.getTag();
				mulop();
				val2=factora();
				switch(op) {
					case Tag.MULT:
						val1=val1*val2;
						break;
					case Tag.DIV:
						val1=val1/val2;
						break;
					case Tag.AND:
						if( val1==val2)
							val1=1;
						else
							val1=0;
						break;
				}
			}
			return val1;
		
	}
	float factora() {
		float val = 0;
		switch(tok.getTag()){
			case Tag.NOT:
				eat(Tag.NOT);
				val=factor();
				if(val==1)
					val=0;
				else 
					val=1;
				break;
			case Tag.MINUS:
				eat(Tag.MINUS);
				val=factor();
				val=val*-1;
				break;
			default :
				val=factor();
		}
		return val;
	}
	float factor() {
		float val=0;
		switch(tok.getTag()) {
			case Tag.ID:
				aux1=env.getValue(tok.toString());
				if( aux1==null|| aux1.equals(""))
					errorvar(tok.toString(), linha);
				val= Float.parseFloat(aux1);
				eat(Tag.ID);
				break;
			case Tag.NUM://Valores do tipo real, estão recebendo a tag da palavra reservada "real"
				val=Float.parseFloat(tok.toString());
				eat(Tag.NUM);
				break;
			case Tag.OPENPAR:
				eat(Tag.OPENPAR);
				val=expression();
				eat(Tag.CLOSEPAR);	
				break;
			case Tag.LITERAL:
				eat(Tag.LITERAL);
				break;
			default :
				error();
		}
		return val;
	}
	float expression() {
		float val1,val2;
		val1=simpleexpr();
		int op;
		while(tok.getTag()==Tag.EQ||tok.getTag()==Tag.GT||tok.getTag()==Tag.GE
				||tok.getTag()==Tag.LT||tok.getTag()==Tag.LE||tok.getTag()==Tag.NE) {
			op=tok.tag;
			relop();
			val2=simpleexpr();
			switch(op) {
				case Tag.EQ:
					if(val1==val2)
						val1=1;
					else 
						val1=0;
					break;
				case Tag.GT:
					if(val1>val2)
						val1=1;
					else
						val1=0;
					break;
				case Tag.GE:
					if(val1>=val2)
						val1=1;
					else 
						val1=0;
					break;
				case Tag.LT:
					if(val1<val2)
						val1=1;
					else 
						val1=0;
					break;
				case Tag.LE:
					if(val1<=val2)
						val1=1;
					else 
						val1=0;
					break;
				case Tag.NE:
					if(val1!=val2)
						val1=1;
					else 
						val1=0;
					break;
				
			}
		}
		return val1;
		
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

	float condition(){
		return expression();
	}
	void stmtsuffix() {
		float rtn;
		eat(Tag.UNTIL);
		rtn=condition();
		if(rtn==1) {
			loopstatus=2; //o loop sera repitido
			index=0;
		}if(rtn==0) {
			loopstatus=0;
			banktoks.clear();
		}
	
	}

	public void errorvar(String var, int ln) {
		System.out.printf("\nErro semântico: Variável  "+var+" invalida na linha "+ln+"!\n");
		System.exit(0);
	}
	public static boolean isNumeric(String strNum) {
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
}
