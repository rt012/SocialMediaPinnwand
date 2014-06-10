package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.client.SocialMediaPinnwand;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;

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
	
	public void printOutBeitragJeNutzer(Nutzer n) {
		
		if(n!=s.getAktuellerNutzer()){
			PinnwandAdministration.getAllBeitragByNutzer(n, new AsyncCallback<ArrayList<Beitrag>>(){
				public void onFailure(Throwable caught) {}
				
				public void onSuccess(ArrayList<Beitrag> result){
					Collections.sort(result); 
					// Hilfvariable um festzuhalten in welcher Row man sich befindet
					int aktuelleRow = 0;
					if(result!= null){
						for(int i= 0; i < result.size(); i++){
							// Hinzuf�gen eines neuen Beitrags in der aktuellen Zeile, Dabei wird ein neues LayoutObjekt initialsiert und der FlexTable hinzugef�gt
							ShowBeitraege.this.setWidget(
									aktuelleRow,
									0,
									new PinnwandBeitrag(result.get(i).getInhalt(),
														"von "+ result.get(i).getPinnwand().getNutzer().getName() +","+result.get(i).getErstellungsZeitpunkt(),
														+ result.get(i).getLikeList().size()+" Personen gefaellt das.",
														result.get(i),aktuellerNutzer, ShowBeitraege.this ));
							// nachdem ein Beitrag der FlexTable hinzugef�gt wurde wird die aktuelle Zeile um 1 erh�ht.
							aktuelleRow += 1;
							// Nun werden alle Kommentare des zuvor hinzugef�gten Beitrages der FlexTable hinzugef�gt
							
							for(int a = 0; a < result.get(i).getKommentarListe().size(); a++) {
								ShowBeitraege.this.setWidget(aktuelleRow, 0, new BeitragKommentar(result.get(i).getKommentarListe().get(a), result.get(i).getKommentarListe().get(a).getInhalt(), " ,von " + result.get(i).getKommentarListe().get(a).getNutzer().getName(), String.valueOf(result.get(i).getKommentarListe().get(a).getErstellungsZeitpunkt())));
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
					// Hilfvariable um festzuhalten in welcher Row man sich befindet
					int aktuelleRow = 0;
					Collections.sort(result); 
					if(result != null){
						for(int i= 0; i < result.size(); i++){
							// Hinzuf�gen eines neuen Beitrags in der aktuellen Zeile, Dabei wird ein neues LayoutObjekt initialsiert und der FlexTable hinzugef�gt
							
							//Da Likelist evtl null ist muss hier die Abfrage ausgelagert werden
							int likelistsize=0;
							if (result.get(i).getLikeList() != null){
								likelistsize = result.get(i).getLikeList().size();
							}
														
							ShowBeitraege.this.setWidget(
									aktuelleRow,
									0,
									new PinnwandBeitrag(result.get(i).getInhalt(),
														"von "+ result.get(i).getPinnwand().getNutzer().getName() +","+result.get(i).getErstellungsZeitpunkt(),
														+ likelistsize +" Personen gefaellt das.",
														result.get(i),aktuellerNutzer, ShowBeitraege.this ));
							// nachdem ein Beitrag der FlexTable hinzugef�gt wurde wird die aktuelle Zeile um 1 erh�ht.
							aktuelleRow += 1;
							// Nun werden alle Kommentare des zuvor hinzugef�gten Beitrages der FlexTable hinzugef�gt
							if(result.get(i).getKommentarListe() != null){
								for(int a = 0; a < result.get(i).getKommentarListe().size(); a++) {
									ShowBeitraege.this.setWidget(aktuelleRow, 0, new BeitragKommentar(result.get(i).getKommentarListe().get(a), result.get(i).getKommentarListe().get(a).getInhalt(), " ,von " + result.get(i).getKommentarListe().get(a).getNutzer().getName(), String.valueOf(result.get(i).getKommentarListe().get(a).getErstellungsZeitpunkt())));
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
