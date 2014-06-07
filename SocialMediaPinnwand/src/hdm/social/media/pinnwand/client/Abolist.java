package hdm.social.media.pinnwand.client;

import hdm.social.media.pinnwand.shared.Abo;
import hdm.social.media.pinnwand.shared.Nutzer;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;

public class Abolist extends FlexTable{
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	private final SocialMediaPinnwand s;
	
	public Abolist(Nutzer n, SocialMediaPinnwand s){
		this.s=s;
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
					Button buttonZeigePinnwand = new Button("Zur Pinnwand");
					buttonZeigePinnwand.setStyleName("buttonZeigePinnwand");
					buttonZeigePinnwand.addClickHandler(new ClickHandler(){
						public void onClick(ClickEvent event) {
							s.FlexTableBeitraege.refresh(result.get(x).getLieferant());
						}
					});
					
					Abozeile a = new Abozeile(result.get(i), s);
					a.setStyleName("Abozeile");
					Abolist.this.setWidget(i,0, a);
					Abolist.this.setWidget(i,1, buttonZeigePinnwand);
					
	
					}
			}			
		});
		
	} 
	
	public void refresh(Nutzer n){
		fillAboList(n);
	}
	
	

}
