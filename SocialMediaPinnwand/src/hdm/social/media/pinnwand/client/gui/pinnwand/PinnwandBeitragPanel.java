package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.client.SocialMediaPinnwand;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Diese Klasse ermöglicht es Beiträge zu schreiben und zu senden.
 * Sie stellt ein Label, eine TextArea und einen Button dar.
 * @author remi
 *
 */
public class PinnwandBeitragPanel extends HorizontalPanel{
	/**
	 * Widgets
	 */
	private Label beitragSchreiben = new Label("Beitrag schreiben:");
	private final TextArea TextAreaBeitragVerfassen = new TextArea();
	private final Button ButtonBeitragSenden = new Button("");
	
	/**
	 * Hilfsvariable
	 */
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	
	public PinnwandBeitragPanel(final SocialMediaPinnwand socialMediaPinnwand){
		setStyleName("layout_east_up");
		
		//Textfeld fï¿½r Beitrag
		beitragSchreiben.setStyleName("beitragSchreiben");
		ButtonBeitragSenden.setStyleName("beitragSenden");
		TextAreaBeitragVerfassen.setStyleName("TextAreaBeitragVerfassen");
				
		add(beitragSchreiben);
		add(TextAreaBeitragVerfassen);
		add(ButtonBeitragSenden);

		/**
		 * ClickHandler für Beitrag posten
		 */
		
		ButtonBeitragSenden.addClickHandler(new ClickHandler() {
			/**
			 *  Beitrag wird der Pinnwand hinzugefï¿½gt
			 *  Anschlieï¿½end wird die Beitragsliste aktualisiert. 
			 */
			public void onClick(ClickEvent event) {
				if(TextAreaBeitragVerfassen.getValue()!=""){

				PinnwandAdministration.getPinnwandByNutzer(socialMediaPinnwand.getAktuellerNutzer(), new AsyncCallback<Pinnwand>(){
					public void onFailure(Throwable caught){}
					public void onSuccess(Pinnwand result){
						Beitrag b = new Beitrag();					
						b.setInhalt(TextAreaBeitragVerfassen.getValue());
						b.setPinnwand(result);
						PinnwandAdministration.saveBeitrag(b, new AsyncCallback<Void>(){	

							public void onFailure (Throwable caught) {
								// TODO: Do something with errors.
							}

							@Override
							public void onSuccess(Void result) {
								socialMediaPinnwand.getShowBeitraege().refresh(socialMediaPinnwand.getAktuellerNutzer());
							}
						});
					}
				});				
				}
			}
		});
		

	}
}
