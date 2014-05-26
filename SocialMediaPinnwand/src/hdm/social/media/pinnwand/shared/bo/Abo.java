package hdm.social.media.pinnwand.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Abo extends BusinessObject implements IsSerializable {
	
	private static final long serialVersionUID = 1L;
	
	private Nutzer abonnent;
	private Nutzer lieferant;
	
	public Nutzer getAbonnent() {
		return abonnent;
	}
	
	public void setAbonnent(Nutzer abonnent) {
		this.abonnent = abonnent;
	}
	public Nutzer getLieferant() {
		return lieferant;
	}
	public void setLieferant(Nutzer lieferant) {
		this.lieferant = lieferant;
	}

	@Override 
	public String toString() {
		return "Abo [abonent=" + abonnent.getName() + ", lieferant=" + lieferant.getName() + "]";
	}
	
	
	

}
