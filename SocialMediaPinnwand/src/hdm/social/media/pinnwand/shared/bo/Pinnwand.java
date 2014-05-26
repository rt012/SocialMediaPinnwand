package hdm.social.media.pinnwand.shared.bo;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;


public class Pinnwand extends BusinessObject implements IsSerializable{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Beitrag> beitraege;
	private Nutzer nutzer;
	
	public ArrayList<Beitrag> getBeitraege() {
		return beitraege;
	}
	public void setBeitraege(ArrayList<Beitrag> beitraege) {
		this.beitraege = beitraege;
	}
	public Nutzer getNutzer() {
		return nutzer;
	}
	public void setNutzer(Nutzer nutzer) {
		this.nutzer = nutzer;
	}
	
	public String getAllBeitraege() {
		String allBeitraege = "";
		for(int i= 0; i <= beitraege.size(); i++) {
			allBeitraege += "Beitrag:"+ i + beitraege.get(i).getInhalt()+ "\n";
		}
		return allBeitraege;
	}
	@Override
	public String toString() {
		return "Pinnwand vom Nutzer:" + nutzer + "\n Beiträge dieser Pinnwand: \n " + getAllBeitraege();
	}
	
	
}
