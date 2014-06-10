package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.client.SocialMediaPinnwand;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.server.PinnwandAdministrationImpl;

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
	private SocialMediaPinnwand s=null;
	private Label aboname;
	private Button buttonAboLoeschen;
	
	AsyncCallback<Void> callbackVoid = new AsyncCallback<Void>() {
		public void onFailure (Throwable caught) {
				// TODO: Do something with errors.
		}
	 
		@Override
		public void onSuccess(Void result) {
			s.getPinnwandAllgemeinPanel().refreshFlexTableAbonniertePinnwaende();
		}
	 };
	
	public Abozeile(final Abo a, final SocialMediaPinnwand s,final ShowBeitraege flexTableBeitraege){
		
		this.s=s;
		
		aboname = new Label(a.getLieferant().getVorname()+" "+a.getLieferant().getName());
		aboname.setStyleName("aboname");
		this.add(aboname);
		
		buttonAboLoeschen = new Button("X");
		buttonAboLoeschen.setStyleName("buttonAboLoeschen");
		this.add(buttonAboLoeschen);
		
		buttonAboLoeschen.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				PinnwandAdministration.deleteAbo(a, callbackVoid);
				flexTableBeitraege.refresh(s.getAktuellerNutzer());
			}
		});		
	}
}