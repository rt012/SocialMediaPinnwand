package hdm.social.media.pinnwand.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *  Diese Klasse stellt die Basisklasse von den Klassen {@link Beitrag} & {@link Kommentar} dar. 
 * @author Blessing & Tessier
 *
 */
public abstract class Document extends BusinessObject implements IsSerializable {

	/**
	 * Der Inhalt eines Dokuments ( Beitrag, Kommentar) 
	 */
private String inhalt;

/**
 * 
 * Auslesen des Inhalts
 */
 
 public String getInhalt() {
	return inhalt;
}
/**
 * 
 * Setzen des Inhalts
 */
public void setInhalt(String inhalt) {
	this.inhalt = inhalt;
}
}
