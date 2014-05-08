package hdm.social.media.pinnwand.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Mit Hilfe dieser Klasse werden Objekte der Klasse Kommentar instanziiert. Einem Kommentar wird ein {@link Beitrag} zugewiesen
 * und wird von einem {@link Nutzer} geschrieben.
 * @author remi
 *
 */

public class Kommentar extends Document implements IsSerializable {
/**
 * Beitrag welcher einem Kommentar zugewiesen wird 
 */
	private Beitrag beitrag;
	
/**
 * Autor eines Kommentars
 */
	private Nutzer nutzer;
	
/**
 * 
 * Auslesen eines Beitrags
 */
	public Beitrag getBeitrag() {
		return beitrag;
	}
/**
 * Setzen eines Beitrags
 * 
 */
	public void setBeitrag(Beitrag beitrag) {
		this.beitrag = beitrag;
	}
/**
 * Auslesen eines Autors
 * 
 */
	public Nutzer getNutzer() {
		return nutzer;
	}
	
/**
 * 
 * Setzen eines Autors 
 */
	public void setNutzer(Nutzer nutzer) {
		this.nutzer = nutzer;
	}

/**
 * Repräsentiert eine Kommentarinstanz als String 
 */
	@Override
	public String toString() {
		return "Kommentar:"+ getInhalt();
	}
	
	
}
