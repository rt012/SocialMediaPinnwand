package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.client.SocialMediaPinnwand;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Diese Klasse ist f�r die Anordnung der einzelnen Beitr�ge zust�ndig.
 * Es werden die jeweiligen Beitr�ge ausgelesen und in einer FlexTable angeordnet. 
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
	}
	/**
	 * Methode welche alle Beitr�ge eines bestimmten Nutzers in der FlexTable anordnet.
	 * @param n Nutzer
	 */
	public void printOutBeitragJeNutzer(Nutzer n) {
		
		if(n!=s.getAktuellerNutzer()){
			PinnwandAdministration.getAllBeitragByNutzer(n, new AsyncCallback<ArrayList<Beitrag>>(){
				public void onFailure(Throwable caught) {
				}
				
				public void onSuccess(ArrayList<Beitrag> result){
					Collections.sort(result); 
					/**
					 *  Hilfvariable um festzuhalten in welcher Row man sich befindet
					 */
					int aktuelleRow = 0;
					if(result!= null){
						for(int i= 0; i < result.size(); i++){
							
							
							/**
							 *  Hinzuf�gen eines neuen Beitrags in der aktuellen Zeile, Dabei wird ein neues LayoutObjekt initialsiert und der FlexTable hinzugef�gt
							 */
							

							PinnwandBeitrag pinnwandBeitrag=new PinnwandBeitrag(s,result.get(i).getInhalt(),

									result.get(i).getPinnwand().getNutzer().getVorname() +" "+ result.get(i).getPinnwand().getNutzer().getName(),formatDate(result.get(i).getErstellungsZeitpunkt()),
									result.get(i).getLikeList().size()+" Person(en) gefaellt das.",
									result.get(i),aktuellerNutzer, ShowBeitraege.this);
							
							pinnwandBeitrag.setStyleName("pinnwandBeitrag");
							ShowBeitraege.this.setWidget(aktuelleRow,0,pinnwandBeitrag);
							/**
							 *  nachdem ein Beitrag der FlexTable hinzugef�gt wurde wird die aktuelle Zeile um 1 erh�ht.
							 */
							aktuelleRow += 1;
							/**
							 *  Nun werden alle Kommentare des zuvor hinzugef�gten Beitrages der FlexTable hinzugef�gt
							 */
							
							for(int a = 0; a < result.get(i).getKommentarListe().size(); a++) {
								
								BeitragKommentar kommentar= new BeitragKommentar(s, aktuellerNutzer, result.get(i).getKommentarListe().get(a),
										result.get(i).getKommentarListe().get(a).getInhalt(),
										result.get(i).getKommentarListe().get(a).getNutzer().getVorname()+ " "  + result.get(i).getKommentarListe().get(a).getNutzer().getName(), 
										formatDate(result.get(i).getKommentarListe().get(a).getErstellungsZeitpunkt()), ShowBeitraege.this);
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
				public void onFailure(Throwable caught) {
				}
				
				public void onSuccess(ArrayList<Beitrag> result){
					/**
					 *  Hilfvariable um festzuhalten in welcher Row man sich befindet
					 */
					int aktuelleRow = 0;
					Collections.sort(result); 
					if(result != null){
						for(int i= 0; i < result.size(); i++){
							/**
							 *  Hinzuf�gen eines neuen Beitrags in der aktuellen Zeile, Dabei wird ein neues LayoutObjekt initialsiert und der FlexTable hinzugef�gt
							 */
														

							PinnwandBeitrag pinnwandBeitrag=new PinnwandBeitrag(s,result.get(i).getInhalt(),

									result.get(i).getPinnwand().getNutzer().getVorname() +" "+ result.get(i).getPinnwand().getNutzer().getName(),formatDate(result.get(i).getErstellungsZeitpunkt()),
									result.get(i).getLikeList().size()+" Person(en) gefaellt das.",
									result.get(i),aktuellerNutzer, ShowBeitraege.this);
							
							pinnwandBeitrag.setStyleName("pinnwandBeitrag");
							
							ShowBeitraege.this.setWidget(aktuelleRow,0,pinnwandBeitrag);
							
							/**
							 *  nachdem ein Beitrag der FlexTable hinzugef�gt wurde wird die aktuelle Zeile um 1 erh�ht.
							 */
							aktuelleRow += 1;
							/**
							 *  Nun werden alle Kommentare des zuvor hinzugef�gten Beitrages der FlexTable hinzugef�gt
							 */
							if(result.get(i).getKommentarListe() != null){
								for(int a = 0; a < result.get(i).getKommentarListe().size(); a++) {


									BeitragKommentar kommentar= new BeitragKommentar(s, aktuellerNutzer, result.get(i).getKommentarListe().get(a),
											result.get(i).getKommentarListe().get(a).getInhalt(),
											result.get(i).getKommentarListe().get(a).getNutzer().getVorname()+" "+result.get(i).getKommentarListe().get(a).getNutzer().getName(),
											formatDate(result.get(i).getKommentarListe().get(a).getErstellungsZeitpunkt()), ShowBeitraege.this);
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
	

	/**
	 * Methode die das Timestampdatum in einen angemessenen und leicht Lesbaren String verwandelt 
	 * @param date
	 * @return
	 */
	
	@SuppressWarnings("deprecation")
	public String formatDate(java.util.Date date){
		//Rechnet von 1900 an
		int y = date.getYear()+1900;
		//Month z�hlt ab 0
		int m = date.getMonth()+1;
		
		int day = date.getDate();
		int h = date.getHours();
		int min = date.getMinutes();
		int sec = date.getSeconds();
		
		// Format wie: 21:15:30  21.11.1989
		return h+":"+min+":"+sec+"  "+day+"."+m+"."+y ;
	}

}
