package symbols;

import java.util.Hashtable;
import java.util.Map;

import lexer.*;
import inter.*;

public class Env {
	//Tipo nome valor
	private Hashtable table;

	String str ;

	public Env() { 
		table = new Hashtable<Id,Word>();
		str= new String();

	}

	public void put( Id i,Token w) { //ID guarda o no nome //guarda a tipagem
		table.put(i,w);
		
	}
	public void showItens() {
		Map<Id,Word> map = table; 

		map.forEach((k, v) -> { 
			System.out.printf(v.toString()+"\t"+k.name+"\t"+k.value+"\n");  	
        }); 
			
	}
	public void setValue(String name,String value) {
		Map<Id,Word> map = table; 
		map.forEach((k, v) -> { 
			if ( k.name.equals(name)) {
				k.value=value;
					
			}
		}); 
		
	}
	
	public String getValue(String name) {
		Map<Id,Word> map = table; 
		str=null;
		map.forEach((k, v) -> { 
			if ( k.name.equals(name)) {
				str= new String (k.value);	
			}
		}); 
		
		
		return str;
		
	}

	public Id get(Token w) { //Busca em ascensão
/*		for(Env e = this; e != null; e = e.up) {
			Id found = (Id)(e.table.get(w));
			if(found != null )
				return found;
		}*/
		Id found = (Id)(table.get(w));
		if(found != null )
			return found;
		return null;
	}
}
