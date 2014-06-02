package hdm.social.media.pinnwand.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

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
