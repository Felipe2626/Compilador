package lexer;
import java.io.*;
import java.util.*;

import inter.Id;
import symbols.*;

public class Lexer {
	public int line ;
	char peek;
	char buff;
	String test ;
	Env env;
	int type,readerInt;
	BufferedReader buffRead;
	Hashtable<String, Word> words = new Hashtable<String, Word>();
	Scanner sc;	
	void reserve(Word w) {
		words.put(w.lexeme, w);
	}
	public Lexer(FileReader fread,Env p_env ) {
		 line=1;
		env=p_env;
		type=0;
		buffRead = new BufferedReader(fread);
		reserve(new Word("start",Tag.START));
		reserve(new Word("stop", Tag.STOP));
		reserve(new Word("if", Tag.IF));
		reserve(new Word("then", Tag.THEN));
		reserve(new Word("end",Tag.END));
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("repeat",Tag.REPEAT));
		reserve(new Word("do",Tag.DO));
		reserve(new Word("read",Tag.READ));
		reserve(new Word("write",Tag.WRITE));
		reserve(new Word("until",Tag.UNTIL));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("app", Tag.APP));
		reserve (new Word("int",Tag.INT )); //Declaracao temporaria
		reserve (new Word("real",Tag.REAL));  // Declaracao temporaria
		//reserve(Type.INTEGER);
		//reserve(Type.REAL);
		buff='§';
	}
	void readch() throws IOException {
	
		if(buff != '§') {
				peek=buff;
				buff='§';
				return;
				}
		if((readerInt = buffRead.read()) == -1) {
			buffRead.close();
		//	throw new EOFException();
		}else {
			peek=(char)readerInt;
			/*if(peek==';')
				peek=')';
			if(peek=='\r')
				peek=';';*/
		}
	}
	
	boolean readch(char c) throws IOException{
		readch();
		if(peek != c)
			return false;
		peek= ' ';
		return true;
	}
	
	public Token scan() throws IOException {
		if(readerInt==-1)
			//throw new EOFException();
			return new Token(Tag.EOF);
		do {
			readch();
			if(readerInt==-1)
				return new Token(Tag.EOF);
			if(peek == ' ' ) continue;
			else if(peek == '\n' || peek=='\r' || peek=='\t') {if(peek=='\n')line = line + 1;}
			else break;
		}while(true);
		
		//Descarta os comentario ate ler fim de linha
		while(peek=='%') {
			do {
				readch();
			}while(peek!='\n');
			do {
				readch();	
			}while(peek==' '||peek=='\n'||peek=='\r');
			line++;
		}
		//Le literal
		if(peek=='{') {
			StringBuffer buffer = new StringBuffer();
			do {
				readch();
				buffer.append(peek);
				if(readerInt==-1)
					throw new NullPointerException();
			}while(peek!='}');
			buffer.deleteCharAt(buffer.length()-1);
			readch();
			Word w = (Word)words.get(buffer.toString());
			if(w != null) {
				return w;
			}
			w = new Word(buffer.toString(), Tag.LITERAL); //nao cria palavras repetidas
			 buff=peek;
			words.put(buffer.toString(), w);
			return w;
		}
	
		switch(peek) {
		case '&':
			if(readch('&')) 
				return Word.and; else 
				{ buff=peek; return new Token('&');}
		case '|':
			if(readch('|')) return Word.or; else { buff=peek;return new Token('|');}
		case '=':
			 return Word.eq;
		case '!':
			if(readch('=')) return Word.ne; 
			else  { buff=peek;
			return new Token('!');}
		case '<':
			if(readch('=')) { return Word.le;} else{ buff=peek; return Word.lt;}
		case '>':
			if(readch('=')) return Word.ge; else { buff=peek; return Word.gt;}
		case ':':
			if(readch('=')) return Word.att; else throw new NullPointerException();
		case '+':
			return Word.plus;
		case '-':
			return Word.minus;
		case '*':
			return Word.mult;
		case '/':
			return Word.div;
		case ',':
			return Word.comma;
		case '(':
			return Word.openpar;
		case ')':
			return Word.closepar;
		case ';':
			type=0;
			return Word.dotcomma;
		}
		
		if(Character.isDigit(peek)) {
			int value = 0;
			do {
				value = 10*value + Character.digit(peek, 10); readch();
			} while (Character.isDigit(peek));
			buff=peek;
			if(peek != '.') // um ponto so
				return new Num(value);
			readch();
			float realValue = value; float fraction = 10;
			for( ; ;) {
				readch();
				if(! Character.isDigit(peek)) break;
				realValue = realValue + Character.digit(peek, 10) / fraction; 
				fraction = fraction*10;
			}
			buff=peek;
			return new Num(realValue);
			
		}
		if (Character.isLetter(peek) || peek == '_') { //adaptado para suportar underscore
		
			StringBuffer buffer = new StringBuffer();
			do {
				buffer.append(peek); 
				readch();
			} while ((Character.isLetterOrDigit(peek) || peek == '_' )&&(readerInt!=-1));
			
			String s = buffer.toString();
		    buff=peek;
			Word w = (Word)words.get(s);
			
			if(w != null) { //Encerra leitura de palavras reservadas
				setTypeSymbol(w); //Ate ler um ;, o algotimo seta que todos os ID seguintes terao o tipo declarado
//				if(w.toString().equals("start")) {//aumenta o nivel da tabela de simbolos
//					env=env.getBotton();
//				}
				return w;
			}
			w = new Word(s, Tag.ID); //nao cria palavras repetidas
			setTableSymbol(w); //Guarda na tabela de simbolos
			//env.put( );
			words.put(s, w);
			return w;
		}

		//caracteres restantes nao identificados como tokens regulares
		//criam-se tokens mesmo assim
		Token tok = new Token(peek); 
	//	peek = ' ';
		//return tok;
		System.out.printf("\nErro lexico, simbolo inesperado: '%c' - linha %d\n",peek,line);
	//s	throw new NullPointerException();
		return tok;
	}
	void setTypeSymbol(Word w) {
		if(w.toString().equals("real")) {
			type=Tag.REAL;
		}else if(w.toString().equals("int")) {
			type=Tag.INT;
		}
	}
	void setTableSymbol(Word w) {
		if(type!=0) { //Se foi lido um tipo antes de um ; in
		  Word wtipo = null;
		  Id ide = new Id(w.toString(),""); 
		  if(type==Tag.INT)
			  wtipo=words.get("int");
		  if(type==Tag.REAL)
			  wtipo=words.get("real");
		  env.put(ide,wtipo);
		}
	}
	public int getline() {
		return line;
	}

}
