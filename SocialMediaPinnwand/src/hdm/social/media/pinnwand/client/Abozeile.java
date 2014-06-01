package hdm.social.media.pinnwand.client;

import hdm.social.media.pinnwand.shared.Abo;
import hdm.social.media.pinnwand.shared.Nutzer;
import hdm.social.media.pinnwand.client.PinnwandAdministration;
import hdm.social.media.pinnwand.server.PinnwandAdministrationImpl;
import hdm.social.media.pinnwand.client.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.client.SocialMediaPinnwand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class Abozeile extends HorizontalPanel{
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	private Label aboname;
	private Button buttonAboLoeschen;
	
	AsyncCallback<Void> callbackVoid = new AsyncCallback<Void>() {
		public void onFailure (Throwable caught) {
				// TODO: Do something with errors.
		}
	 
		@Override
		public void onSuccess(Void result) {
		}
	 };
	
	public Abozeile(final Abo a){
		
		aboname = new Label(a.getLieferant().getVorname()+" "+a.getLieferant().getName());
		aboname.setStyleName("aboname");
		this.add(aboname);
		
		buttonAboLoeschen = new Button("X");
		buttonAboLoeschen.setStyleName("buttonAboLoeschen");
		this.add(buttonAboLoeschen);
		
		buttonAboLoeschen.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				PinnwandAdministration.deleteAbo(a, callbackVoid);
			}
		});		
	}
}