package lexer;
import java.io.*;
import java.util.*;
import symbols.*;

public class Lexer {
	public static int line = 1;
	char peek = ' ';
	Hashtable words = new Hashtable();
	void reserve(Word w) {
		words.put(w.lexeme, w);
	}
	public Lexer() {
		reserve(new Word("if", Tag.IF));
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(Type.Int);
		reserve(Type.Real);
		
	}
	void readch() throws IOException {
		peek = (char)System.in.read();
	}
	
	boolean readch(char c) throws IOException{
		readch();
		if(peek != c)
			return false;
		peek= ' ';
		return true;
	}
	
	public Token scan() throws IOException {
		for( ; ; readch()) {
			if(peek == ' ' || peek == '\t' ) continue;
			else if(peek == '\n') line = line + 1;
			else break;
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
