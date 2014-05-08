package hdm.social.media.pinnwand.shared;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * <p>
 * Die Klasse <code>BusinessObject</code> stellt die Basisklasse aller in diesem
 * Projekt für die Umsetzung des Fachkonzepts relevanten Klassen dar.
 * </p>
 * Zentrales Merkmal ist, dass jedes <code>BusinessObject</code> eine Nummer
 * besitzt, die man in einer relationalen Datenbank auch als Primärschlüssel
 * bezeichnen würde. Fernen ist jedes <code>BusinessObject</code> als
 * {@link Serializable} gekennzeichnet. Durch diese Eigenschaft kann jedes
 * <code>BusinessObject</code> automatisch in eine textuelle Form überführt und
 * z.B. zwischen Client und Server transportiert werden. Bei GWT RPC ist diese
 * textuelle Notation in JSON (siehe http://www.json.org/) kodiert.
 * </p>
 * @author Blessing & Tessier
 *
 */

public abstract class BusinessObject implements IsSerializable {
	/**
	 * Erstellungszeitpunkt der Instanz
	 */
private Date erstellungsZeitpunkt;
	/**
	 * Die eindeutige Identifikationsnummer einer Instanz
	 */
private int id;

/**
 * Auslesen des Erstellungszeitpunktes
 * 
 */
public Date getErstellungsZeitpunkt() {
	return erstellungsZeitpunkt;
}

/**
 * Setzen des Erstellungszeitpunkt
 * 
 */
public void setErstellungsZeitpunkt(Date erstellungsZeitpunkt) {
	this.erstellungsZeitpunkt = erstellungsZeitpunkt;
}

/**
 * Auslesen der Identifikationsnummer
 *
 */
public int getId() {
	return id;
}

/**
 * Setzten der Identifikationsnummer
 * 
 */
public void setId(int id) {
	this.id = id;
} 
}
