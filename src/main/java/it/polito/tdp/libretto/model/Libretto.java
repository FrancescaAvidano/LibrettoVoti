package it.polito.tdp.libretto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Memorizza e gestisce un insieme di voti superati
 */

public class Libretto {
	private List<Voto> voti= new ArrayList<>();
	
	/**
	 * Crea un libretto nuovo e vuoto
	 */
	public Libretto() {
		super();
	}
	
	/**
	 * Copy constructor
	 * "shallow" (copia superficiale)
	 * @param lib
	 */
	public Libretto(Libretto lib) {
		super();
		this.voti.addAll(lib.voti);
	}
	
	/**
	 * Aggiunge un nuovo voto al libretto
	 * @param v voto da aggiungere
	 * @return {@code true} se ha inserito il voto, {@code false} se non ha inserito perchè
	 * conflitto o duplicato
	 */

	public boolean add(Voto v) {
		if(this.isConflitto(v) || this.isDuplicato(v)) {
			//non inserire il voto
			return false; //segnalo al chiamante che non ha avuto successo
		}else {
			//inserisci il voto perchè non ci sono duplicati o conflitti
			this.voti.add(v);
			return true;
		}
	}
	
	/**
	 * Dato un libretto, restituisce una stringa nella quale vi sono 
	 * solo i voti pari al valore specificato
	 * @param voto valore specificato
	 * @return stringa formattata 
	 */
	
	public String stampaVotiUguali(int voto) {
		String s = "";
		for(Voto v: this.voti) {
			if(v.getVoto()==voto) {
				s += v.toString()+"\n";	
			}			
		}
		return s;
	}
	
	/**
	 * Genera un libretto, a partire da quello esistente, che conterrà esclusivamente i 
	 * voti con votazione pari a quella specificata
	 * @param voto votazione specificata
	 * @return nuovo Libretto "ridotto"
	 */
	public Libretto estraiVotiUguali(int voto) {
		Libretto nuovo = new Libretto();
		for (Voto v: this.voti) {
			if (v.getVoto()==voto) {
				nuovo.add(v);
			}
		}
		return nuovo;
	}
	
	public String toString() {
		String s = "";
		for(Voto v: this.voti) {
			s += v.toString()+"\n";	
		}
		return s;
	}
	/**
	 * Dato il nome di un corso, ricerca se quell'esame esiste nel 
	 * libretto e, in caso affermativo, restituisce l'oggetto Voto corrispondente.
	 * Se l'esame non esiste, restituisce null
	 * 
	 * @param nomeCorso nome esame da cercare
	 * @return il voto corrispondente oppure null se non esiste
	 */
	public Voto cercaNomeCorso(String nomeCorso) {
		/*
		for (Voto v: this.voti) {
			if(nomeCorso.equals(v.getCorso())) {
				return v;
			}
		}
		return null;
		*/
		int pos = this.voti.indexOf(new Voto(nomeCorso, 0, null));
		if(pos!= -1) {
			return this.voti.get(pos);
		}else {
			return null;
		}
	}
	/**
	 * Ritorna {@code true} se il corso specificato da {@code v} 
	 * esiste nel libretto, con la stessa valutazione.
	 * Se non esiste o è diversa, ritorna {@code false}
	 * @param v il {@link Voto} da ricercare
	 * @return l'esistenza di un duplicato
	 */
	public boolean isDuplicato(Voto v) {
		Voto esiste = this.cercaNomeCorso(v.getCorso());
		if(esiste==null) //non trovato ==> non è duplicato
			return false;
		return (esiste.getVoto() == v.getVoto());
	}
	
	/**
	 * Determina se c'è un conflitto
	 */
	
	public boolean isConflitto(Voto v) {
		Voto esiste = this.cercaNomeCorso(v.getCorso());
		if(esiste==null) //non trovato ==> non è duplicato
			return false;
		return (esiste.getVoto() != v.getVoto());
	}
	
	/**
	 * Restituisce un nuovo libretto migliorando i voti di quello attuale
	 * @return
	 */
	public Libretto creaLibrettoMigliorato() {
		Libretto nuovo = new Libretto();
		for (Voto v : this.voti) {
			//Voto v2 = new Voto(v);
			Voto v2 = v.clone();
			if(v2.getVoto()>=24) {
				v2.setVoto(v2.getVoto()+2);
				if(v2.getVoto()>30)
					v2.setVoto(30);
			}else if (v2.getVoto()>=18) {
				v2.setVoto(v2.getVoto()+1);
			}
			nuovo.add(v2);
		}
		return nuovo;
	}

	/**
	 * Riordina i voti presenti nel libretto corrente 
	 * alfabeticamente per corso
	 */
	public void ordinaPerCorso() {
		Collections.sort(this.voti);		
	}
	
	public void ordinaPerVoto() {
		Collections.sort(this.voti, new ConfrontaVotiPerValutazione());		
	}
	
	public void cancellaVotiScarsi() {
		List<Voto> daRimuovere = new ArrayList<>();
		for (Voto v: this.voti) {
			if (v.getVoto()<24) {
				daRimuovere.add(v);
			}
		}
		
		this.voti.removeAll(daRimuovere);
		//for (Voto v: daRimuovere) {
		//	this.voti.remove(v);
		//}
	}
}
