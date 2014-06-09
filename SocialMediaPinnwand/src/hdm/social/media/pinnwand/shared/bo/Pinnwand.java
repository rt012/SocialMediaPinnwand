package hdm.social.media.pinnwand.shared.bo;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;
<<<<<<< HEAD


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
=======
/**
 * Die Pinnwandklasse ist die Darstellungsfläche von jenem Nutzer. 
 * Auf der Pinnwand werden die Beiträge dargestellt. 
 * Eine Pinnwand hat somit einen Nutzer(Betreiber) und eine Liste mit Beiträgen.
 * 
 * @author Blessing & Tessier
 *
 */

public class Pinnwand extends BusinessObject implements IsSerializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Liste für Beiträge
	 *
	 */
	private ArrayList<Beitrag> beitraege;
	/**
	 * Betreiber und Inhaber der Pinnwand
	 */
	private Nutzer nutzer;
	/**
	 * Methode zum Auslesen der Beiträge
	 *
	 */
	public ArrayList<Beitrag> getBeitraege() {
		return beitraege;
	}
	/**
	 * Methode zum Setzen der Beiträge
	 *
	 */
	public void setBeitraege(ArrayList<Beitrag> beitraege) {
		this.beitraege = beitraege;
	}
	/**
	 * Methode zum Auslesen der Nutzer
	 *
	 */
	public Nutzer getNutzer() {
		return nutzer;
	}
	/**
	 * Methode zum Setzen der Nutzer
	 *
	 */
	public void setNutzer(Nutzer nutzer) {
		this.nutzer = nutzer;
	}
	/**
	 * Methode zum Auslesen aller Beiträge
	 *
	 */
	public String getAllBeitraege() {
		String allBeitraege = "";
		for(int i= 0; i <= beitraege.size(); i++) {
			allBeitraege += "Beitrag:"+ i + beitraege.get(i).getInhalt()+ "\n";
		}
		return allBeitraege;
	}
	/**
	 * Umwandlung einer Pinnwandinstanz in einen String
	 */
>>>>>>> refs/remotes/origin/Eric
	@Override
	public String toString() {
		return "Pinnwand vom Nutzer:" + nutzer + "\n Beiträge dieser Pinnwand: \n " + getAllBeitraege();
	}
	
	
}
