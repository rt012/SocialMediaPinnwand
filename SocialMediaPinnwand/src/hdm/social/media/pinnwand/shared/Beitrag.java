package hdm.social.media.pinnwand.shared;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Mit dieser Klasse werrden Beitragsobjekte realisiert. Ein Beitrag besteht aus einem Inhalt,
 * welcher von der Superklasse {@link Document} geerbt wird.
 * Auﬂerdem besteht ein Beitragsobjekt aus einer Liste von Likes,
 * einer Liste von Kommentarenund steht auf einer bestimmten Pinnwand ({@link Pinnwand})
 *
 * @author Blessing & Tessier
 */
public class Beitrag extends Document implements IsSerializable , Comparable<Beitrag>{

	private static final long serialVersionUID = 1L;
	/**
	 * Liste aller Likes einer Beitragsinstanz
	 */
	private ArrayList<Like> likeList;
	/**
	 * Liste aller Kommentare einer Beitragsinstanz
	 */
	private ArrayList<Kommentar> kommentarListe;
	/**
	 * Pinnwand, die einer Beitragsinstanz zugeordnet ist.
	 */
	private Pinnwand pinnwand;
	
	private Nutzer nutzer;
	
	public Nutzer getNutzer() {
		return nutzer;
	}

	public void setNutzer(Nutzer nutzer) {
		this.nutzer = nutzer;
	}

	/*
	 * Auslesen einer Liste von Likes 
	 */
	public ArrayList<Like> getLikeList() {
		return likeList;
	}
	
	/**
	 * Setzen einer Liste von Likes
	 * 
	 */
	public void setLikeList(ArrayList<Like> likeList) {
		this.likeList = likeList;
	}
	
	/**
	 * Auslesen von einer Liste von Kommentaren 
	 * 
	 */
	public ArrayList<Kommentar> getKommentarListe() {
		Collections.sort(kommentarListe);
		return kommentarListe;
	}
	/**
	 * 
	 * Setzen einer Liste von Kommentaren 
	 */
	public void setKommentarListe(ArrayList<Kommentar> kommentarListe) {
		this.kommentarListe = kommentarListe;
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
	 * 
	 */
	public void setPinnwand(Pinnwand pinnwand) {
		this.pinnwand = pinnwand;
	}
	
	/*
	 * Ausgeben eines String mit allen Namen der Nutzer welche einen Beitrag geliked haben. 
	 */
	public String getAllLikeNutzer() {
		String likeList1 = "";
		for(int i = 0; i <= likeList.size(); i++) {
			likeList1 +=  ""+ likeList.get(i).getNutzer().getName() + "\n";
		}
		return likeList1;
	}
	/**
	 * Repr‰sentiert ein Beitragsobjekt als String. 
	 */
	@Override
	public String toString() {
		return "Erstellungszeitpunnkt" + getErstellungsZeitpunkt() + "Beitrag:" + getInhalt() + "\n Autor:" + pinnwand.getNutzer() + "\n Benutzer die diesen Beitrag geliked haben:"+ getAllLikeNutzer();
	}

	/**
	 * Notwendig um Arraylisten ordnen zu kˆnnen
	 * Regul‰r w¸rden Beitr‰ge von ‰ltestem zu neuestem geordnet werden, daher umdrehen der R¸ckgabewerte
	 */
	@Override
	public int compareTo(Beitrag b) {
		if(this.getErstellungsZeitpunkt().compareTo(b.getErstellungsZeitpunkt())==1){
			return -1;
		}
		if(this.getErstellungsZeitpunkt().compareTo(b.getErstellungsZeitpunkt())==-1){
			return 1;
		}
		else{
			return this.getErstellungsZeitpunkt().compareTo(b.getErstellungsZeitpunkt());
			}
		}
}
	

