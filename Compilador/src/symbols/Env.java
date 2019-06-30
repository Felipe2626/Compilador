package symbols;

import java.util.Hashtable;
import java.util.Map;

import javax.swing.text.StyledEditorKit.BoldAction;

import lexer.*;
import inter.*;

public class Env {
	//Tipo nome valor
	private Hashtable table;

	String str ;
	boolean check;
	int type;
	public Env() { 
		table = new Hashtable<Id,Word>();
		str= new String();

	}

	public boolean put( Id i,Token w) { //ID guarda o no nome //guarda a tipagem
		if(existVar(w.toString())) {
			return false;
		}
		table.put(i,w);
		return true;
		
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
	public boolean existVar(String name) {
		Map<Id,Word> map = table; 
		check =false;
		map.forEach((k, v) -> { 
			if ( k.name.equals(name)) {
				check=true; 
			}
		}); 
		
		
		return check;
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
	public int getType(String name) {
		Map<Id,Word> map = table; 
		type=0;
		map.forEach((k, v) -> { 
			if ( k.name.equals(name)) {
				type=v.getTag();
			}
		}); 
		return type;
	}

	
}
