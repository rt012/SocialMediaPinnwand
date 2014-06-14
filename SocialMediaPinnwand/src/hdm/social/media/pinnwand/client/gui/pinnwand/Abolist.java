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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;

public class Abolist extends FlexTable{
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	private final SocialMediaPinnwand s;
	private ShowBeitraege flexTableBeitraege;
	
	public Abolist(Nutzer n, SocialMediaPinnwand s, final ShowBeitraege flexTableBeitraege){		
		this.s=s;
		this.flexTableBeitraege = flexTableBeitraege;
		fillAboList(n);
	}
	
	public void fillAboList(final Nutzer n){
		
		PinnwandAdministration.getAboByNutzer(n.getId(), new AsyncCallback<ArrayList<Abo>>() {
			public void onFailure
			(Throwable caught) {
				// TODO: DO something with errors.
			}
			@Override
			public void onSuccess(final ArrayList<Abo> result) {
				
				for (int i=0; i<result.size(); i++){
					
					final int x=i;
					Button buttonZeigePinnwand = new Button("");
					buttonZeigePinnwand.setStyleName("buttonZeigePinnwand");
					buttonZeigePinnwand.addClickHandler(new ClickHandler(){
						public void onClick(ClickEvent event) {
							flexTableBeitraege.refresh(result.get(x).getLieferant());
						}
					});
					
					Abozeile a = new Abozeile(result.get(i), s, flexTableBeitraege);
					a.setStyleName("Abozeile");
					Abolist.this.setWidget(i,0, a);
					Abolist.this.setWidget(i,1, buttonZeigePinnwand);
					
	
					}
			}			
		});
		
	} 
	
	public void refresh(Nutzer n){
		this.clear();
		fillAboList(n);
	}
	
	

}
