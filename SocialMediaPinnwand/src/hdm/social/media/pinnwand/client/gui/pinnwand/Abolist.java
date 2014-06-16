package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.client.SocialMediaPinnwand;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;

public class Abolist extends FlexTable{
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	private final SocialMediaPinnwand s;
	
	/**
	 * Konstruktor
	 * @param Nutzer n
	 * @param SocialMediaPinnwand s
	 * @param flexTableBeitraege
	 */
	
	public Abolist(Nutzer n, SocialMediaPinnwand s, final ShowBeitraege flexTableBeitraege){		
		this.s=s;
		refresh(s, flexTableBeitraege);
	}
	
	/**
	 * 
	 * Aboliste f�llen mit den Abos des aktuellen Nutzers
	 * @param n
	 */
	
	public void fillAboList(final SocialMediaPinnwand s, final ShowBeitraege showbeitraege){
		
		PinnwandAdministration.getAboByNutzer(s.getAktuellerNutzer().getId(), new AsyncCallback<ArrayList<Abo>>() {
			public void onFailure
			(Throwable caught) {
				Window.alert("Hoppala! Da gab es wohl ein Problem beim Laden der Aboliste-Liste... Bitte Laden Sie die Seite neu!");
			}
			@Override
			public void onSuccess(final ArrayList<Abo> result) {
				
				for (int i=0; i<result.size(); i++){
					// f�r jeden gefundenen Nutzer legen wir eine Zeile an die einen Button zum Anzeigen der Pinnwand und ein Widget der Abozeilen Klasse beinhaltet;
					final int x=i;
					Button buttonZeigePinnwand = new Button("");
					buttonZeigePinnwand.setStyleName("buttonZeigePinnwand");
					buttonZeigePinnwand.addClickHandler(new ClickHandler(){
						public void onClick(ClickEvent event) {
							showbeitraege.refresh(result.get(x).getLieferant());
						}
					});
					
					Abozeile a = new Abozeile(result.get(i), s, showbeitraege);
					a.setStyleName("Abozeile");
					Abolist.this.setWidget(i,0, a);
					Abolist.this.setWidget(i,1, buttonZeigePinnwand); 
				}
				showbeitraege.refresh(s.getAktuellerNutzer());
			}			
		});
		
	} 
	
	public void refresh(SocialMediaPinnwand s, ShowBeitraege showbeitraege){
		this.removeAllRows();
		fillAboList(s, showbeitraege);
	}
	
	

}
