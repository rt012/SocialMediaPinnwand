package hdm.social.media.pinnwand.shared.bo;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Ein Nutzer zeichnet sich durch einen Vornamen, Nachnamen, Emailadresse, 
 * Nickname, einer Pinnwand, und einer Abonnenten Liste aus.  
 * 
 * @author Blessing & Tessier
 *
 */

public class Nutzer extends BusinessObject implements IsSerializable {


private static final long serialVersionUID = 1L;
/**
 *  Vorname eines Nutzers 	
 */
	private String vorname;
/**
 * Nachname eines Nutzers
 */
	private String name;
/**
 * Emailadresse eines Nutzers
 */
	private String email;
/**
 * Nickname eines Nutzers
 */
	private String nickname;
/**
 * Pinnwand eines Nutzers
 */
	private Pinnwand pinnwand;
/**
 * Liste von Abonnenten eines Nutzers 
 */
	private ArrayList<Abo> abonnentenListe;
	
/**
 * 
 * Auslesen des Vornamens 
 */
	public String getVorname() {
		return vorname;
	}
	
/**
 * 
 * Setzen eines Vornamens 
 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
/**
 * Auslesen eines Nachnamens
 */
	public String getName() {
		return name;
	}
	
/**
 * Setzen eines Nachnamens 
 */
	public void setName(String name) {
		this.name = name;
	}
	
/**
 * 
 * Auslesen der Emailadresse eines Nutzers 
 */
	public String getEmail() {
		return email;
	}
	
/**
 * 
 * Setzen einer Emailadresse
 */
	public void setEmail(String email) {
		this.email = email;
	}
	
/**
 * 
 * Auslesen eines Nicknames
 */
	public String getNickname() {
		return nickname;
	}
/**
 * 
 * Setzen eines Nicknames
 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
/**
 * 
 * Auslesen der Pinnwand
 */
	public Pinnwand getPinnwand() {
		return pinnwand;
	}
/**
 * Setzen der Pinnwand
 * @param pinnwand
 */
	public void setPinnwand(Pinnwand pinnwand) {
		this.pinnwand = pinnwand;
	}

/**
 * 
 * Auslesen der Abonnentenliste
 */
	public ArrayList<Abo> getAbonnentenListe() {
		return abonnentenListe;
	}
	
/**
 * 
 * Setzen der Abonnentenliste
 */
	public void setAbonnentenListe(ArrayList<Abo> abonnentenListe) {
		this.abonnentenListe = abonnentenListe;
	}
	
/**
 * 
 * Repräsentierung der Abonnentenliste als String. Dabei wird jeweils der Name des Abonnenten und der Name des Lieferanten ausgegeben. 
 */
	
	public String getAllAbos() {
		String Abos = null;
		for(int i=0; i <= abonnentenListe.size(); i++){

			 Abos +=  "Abonnent:" + abonnentenListe.get(i).getAbonnent().name + "Lieferant:"+ abonnentenListe.get(i).getLieferant().name +"\n";

			
		}
		return Abos;
	}
	
/**
 * Repräsentierung einer Nutzerinstanz als String
 */
	@Override
	public String toString() {
		return "Nutzer [vorname=" + vorname + " \n, name=" + name + ", email="
				+ email + ", nickname=" + nickname + " \n abonnentenListe=" + getAllAbos() + "]";
	}
	
	/**
	 * Überschriebene equals Methode welche false zurück gibt,
	 * wenn das beide Objekte unterschiedlich sind und true wenn
	 * sie gleich sind
	 * 
	 * @param n Das zu vergleichende Nutzer Objekt
	 * @author Eric Schmidt
	 */
	@Override
	public boolean equals(Object n){
		if (n instanceof Nutzer){
			Nutzer vgl = (Nutzer) n;
			if (vgl.getId() == this.getId()){
				return true;
			}			
		}
		return false;
	}

	
	
}
