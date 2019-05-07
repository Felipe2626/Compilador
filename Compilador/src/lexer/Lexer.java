package lexer;
import java.io.*;
import java.util.*;
import symbols.*;

public class Lexer {
	public static int line = 1;
	Character peek = ' ';
	BufferedReader buffRead;
	Hashtable<String, Word> words = new Hashtable<String, Word>();
	
	void reserve(Word w) {
		words.put(w.lexeme, w);
	}
	public Lexer(FileReader fread ) {
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
		reserve (new Word("integer",Tag.INT )); //Declaracaoo temporaria
		reserve (new Word("real",Tag.REAL));  // Declaracaoo temporaria
		//reserve(Type.INTEGER);
		//reserve(Type.REAL);
		
	}
	void readch() throws IOException {
		int val;
		if((val = buffRead.read()) == -1) {
			buffRead.close();
			throw new EOFException();
		}else {
			peek=Carval;
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
		do {
			readch();
			if(peek == ' ' ) continue;
			else if(peek == '\n') line = line + 1;
			else break;
		}while(true);
		
		//Descarta os comentario ate ler fim de linha
		if(peek=='%') {
			do {
				readch();
			}while(peek!='\n');
		}
		//Le literal
		if(peek=='{') {
			StringBuffer buffer = new StringBuffer();
			do {
				readch();
				buffer.append(peek);
			}while(peek!='}');
			Word w = (Word)words.get(buffer.toString());
			if(w != null) {
				return w;
			}
			w = new Word(buffer.toString(), Tag.LITERAL); //nao cria palavras repetidas
			words.put(buffer.toString(), w);
			return w;
		}
		
		
		switch(peek) {
		case '&':
			if(readch('&')) return Word.and; else return new Token('&');
		case '|':
			if(readch('|')) return Word.or; else return new Token('|');
		case '=':
			if(readch('=')) return Word.eq; else return new Token('=');
		case '!':
			if(readch('=')) return Word.ne; else return new Token('!');
		case '<':
			if(readch('=')) return Word.le; else return new Token('<');
		case '>':
			if(readch('=')) return Word.ge; else return new Token('>');
		case ':':
			if(readch('=')) return Word.att; else return null;
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

		}
		
		if(Character.isDigit(peek)) {
			int value = 0;
			do {
				value = 10*value + Character.digit(peek, 10); readch();
			} while (Character.isDigit(peek));
			
			if(peek != '.') // um ponto so
				return new Num(value);
			float realValue = value; float fraction = 10;
			for( ; ;) {
				readch();
				if(! Character.isDigit(peek)) break;
				realValue = realValue + Character.digit(peek, 10) / fraction; 
				fraction = fraction*10;
			}
			return new Real(realValue);
		}
		if (Character.isLetter(peek) || peek == '_') { //adaptado para suportar underscore
			StringBuffer buffer = new StringBuffer();
			do {
				buffer.append(peek); 
				readch();
			} while (Character.isLetterOrDigit(peek) || peek == '_');
			String s = buffer.toString();
			Word w = (Word)words.get(s);
			if(w != null) {
				return w;
			}
			w = new Word(s, Tag.ID); //nao cria palavras repetidas
			words.put(s, w);
			return w;
		}

		//caracteres restantes nao identificados como tokens regulares
		//criam-se tokens mesmo assim
		Token tok = new Token(peek); 
		peek = ' ';
		return tok;
	}

}
