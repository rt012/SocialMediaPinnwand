package hdm.social.media.pinnwand.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * Abonnement-Klasse, diese Klasse stellt das Abonnement eines Nutzers von einer Pinnwand dar. 
 * Ein Abonnement entsteht somit wenn ein Nutzer die Pinnwand eines anderen Nutzers abonniert.
 * In dieser Klasse befinden sich die Attribute Nutzer (Abonnent) und Nutzer (Lieferant).
 * 
 *  @author Blessing & Tessier 
 */
public class Abo extends BusinessObject implements IsSerializable {
	
	private static final long serialVersionUID = 1L;
	//Abonnement Nutzer
	private Nutzer abonnent;
	//Abonnierter Nutzer 
	private Nutzer lieferant;
	/**
	 * Methode zum Aulesen eines Abonnements
	 */
	public Nutzer getAbonnent() {
		return abonnent;
	}
	/**
	 * Methode zum Setzen eines Abonnements
	 */
	public void setAbonnent(Nutzer abonnent) {
		this.abonnent = abonnent;
	}
	/**
	 * Methode zum Auslesen eines Lieferanten 
	 */
	public Nutzer getLieferant() {
		return lieferant;
	}
	/**
	 * Methode zum Setzen eines Lieferanten 
	 */
	public void setLieferant(Nutzer lieferant) {
		this.lieferant = lieferant;
	}
	/**
	 * Wandelt ein Abonnementobjekt in ein Stringobjekt um. 
	 */
	@Override 
	public String toString() {
		return "Abo [abonent=" + abonnent.getName() + ", lieferant=" + lieferant.getName() + "]";
	}
	
	
	

}
