package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.client.SocialMediaPinnwand;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Diese Klasse ist für die Anordnung der einzelnen Beiträge zuständig.
 * Es werden die jeweiligen Beiträge ausgelesen und in einer FlexTable angeordnet. 
 *
 */

public class ShowBeitraege extends FlexTable{
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	private final SocialMediaPinnwand s;
	private final Nutzer aktuellerNutzer;
	
	
	public ShowBeitraege(Nutzer n, SocialMediaPinnwand s){
	
		
		setStyleName("FlexTableBeitraege");
		this.aktuellerNutzer = n;
		this.s=s;
		printOutBeitragJeNutzer(n);
	}
	/**
	 * Methode welche alle Beiträge eines bestimmten Nutzers in der FlexTable anordnet.
	 * @param n Nutzer
	 */
	public void printOutBeitragJeNutzer(Nutzer n) {
		
		if(n!=s.getAktuellerNutzer()){
			PinnwandAdministration.getAllBeitragByNutzer(n, new AsyncCallback<ArrayList<Beitrag>>(){
				public void onFailure(Throwable caught) {}
				
				public void onSuccess(ArrayList<Beitrag> result){
					Collections.sort(result); 
					/**
					 *  Hilfvariable um festzuhalten in welcher Row man sich befindet
					 */
					int aktuelleRow = 0;
					if(result!= null){
						for(int i= 0; i < result.size(); i++){
							
							
							/**
							 *  Hinzufï¿½gen eines neuen Beitrags in der aktuellen Zeile, Dabei wird ein neues LayoutObjekt initialsiert und der FlexTable hinzugefï¿½gt
							 */
							
							PinnwandBeitrag pinnwandBeitrag=new PinnwandBeitrag(result.get(i).getInhalt(),
									result.get(i).getPinnwand().getNutzer().getVorname() +" "+ result.get(i).getPinnwand().getNutzer().getName(),result.get(i).getErstellungsZeitpunkt().toString(),
									+ result.get(i).getLikeList().size()+" Person(en) gefaellt das.",
									result.get(i),aktuellerNutzer, ShowBeitraege.this);
							
							pinnwandBeitrag.setStyleName("pinnwandBeitrag");
							ShowBeitraege.this.setWidget(aktuelleRow,0,pinnwandBeitrag);
							/**
							 *  nachdem ein Beitrag der FlexTable hinzugefï¿½gt wurde wird die aktuelle Zeile um 1 erhï¿½ht.
							 */
							aktuelleRow += 1;
							/**
							 *  Nun werden alle Kommentare des zuvor hinzugefï¿½gten Beitrages der FlexTable hinzugefï¿½gt
							 */
							
							for(int a = 0; a < result.get(i).getKommentarListe().size(); a++) {
								
								BeitragKommentar kommentar= new BeitragKommentar(aktuellerNutzer, result.get(i).getKommentarListe().get(a), result.get(i).getKommentarListe().get(a).getInhalt(),result.get(i).getKommentarListe().get(a).getNutzer().getVorname()+ " "  + result.get(i).getKommentarListe().get(a).getNutzer().getName(), String.valueOf(result.get(i).getKommentarListe().get(a).getErstellungsZeitpunkt()), ShowBeitraege.this);
								kommentar.setStyleName("kommentar");
								ShowBeitraege.this.setWidget(aktuelleRow, 0, kommentar);
								aktuelleRow += 1;
							}
						}
					}
				}
			});
		}
		
		else{
			PinnwandAdministration.getAllBeitragByAktuellerNutzer(n, new AsyncCallback<ArrayList<Beitrag>>(){
				public void onFailure(Throwable caught) {}
				
				public void onSuccess(ArrayList<Beitrag> result){
					/**
					 *  Hilfvariable um festzuhalten in welcher Row man sich befindet
					 */
					int aktuelleRow = 0;
					Collections.sort(result); 
					if(result != null){
						for(int i= 0; i < result.size(); i++){
							/**
							 *  Hinzufï¿½gen eines neuen Beitrags in der aktuellen Zeile, Dabei wird ein neues LayoutObjekt initialsiert und der FlexTable hinzugefï¿½gt
							 */
														
							PinnwandBeitrag pinnwandBeitrag=new PinnwandBeitrag(result.get(i).getInhalt(),
									result.get(i).getPinnwand().getNutzer().getVorname() +" "+ result.get(i).getPinnwand().getNutzer().getName(),result.get(i).getErstellungsZeitpunkt().toString(),
									+ result.get(i).getLikeList().size()+" Person(en) gefaellt das.",
									result.get(i),aktuellerNutzer, ShowBeitraege.this);
							
							pinnwandBeitrag.setStyleName("pinnwandBeitrag");
							
							ShowBeitraege.this.setWidget(aktuelleRow,0,pinnwandBeitrag);
							
							/**
							 *  nachdem ein Beitrag der FlexTable hinzugefï¿½gt wurde wird die aktuelle Zeile um 1 erhï¿½ht.
							 */
							aktuelleRow += 1;
							/**
							 *  Nun werden alle Kommentare des zuvor hinzugefï¿½gten Beitrages der FlexTable hinzugefï¿½gt
							 */
							if(result.get(i).getKommentarListe() != null){
								for(int a = 0; a < result.get(i).getKommentarListe().size(); a++) {
									BeitragKommentar kommentar= new BeitragKommentar(aktuellerNutzer, result.get(i).getKommentarListe().get(a), result.get(i).getKommentarListe().get(a).getInhalt(), result.get(i).getKommentarListe().get(a).getNutzer().getVorname()+" "+result.get(i).getKommentarListe().get(a).getNutzer().getName(), String.valueOf(result.get(i).getKommentarListe().get(a).getErstellungsZeitpunkt()), ShowBeitraege.this);
									kommentar.setStyleName("kommentar");
									ShowBeitraege.this.setWidget(aktuelleRow, 0, kommentar);
									aktuelleRow += 1;
								}
							}
						}
					}
				}
			});
		}
	}
	
	public void refresh(Nutzer n){
		this.clear();
		printOutBeitragJeNutzer(n);
	}


}
