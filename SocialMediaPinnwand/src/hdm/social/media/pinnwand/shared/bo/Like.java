package hdm.social.media.pinnwand.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Abonnement-Klasse, diese Klasse stellt das Abonnement eines Nutzers von einer Pinnwand dar. 
 * Ein Abonnement entsteht somit wenn ein Nutzer die Pinnwand eines anderen Nutzers abonniert.
 * In dieser Klasse befinden sich die Attribute Nutzer (Abonnent) und Nutzer (Lieferant).
 * 
 *  @author Blessing & Tessier 
 */

public class Like extends BusinessObject implements IsSerializable{

	private Beitrag beitrag;
	private Nutzer nutzer;
	
	
	public Beitrag getBeitrag() {
		return beitrag;
	}
	public void setBeitrag(Beitrag beitrag) {
		this.beitrag = beitrag;
	}
	public Nutzer getNutzer() {
		return nutzer;
	}
	public void setNutzer(Nutzer nutzer) {
		this.nutzer = nutzer;
	}
	
	
}
