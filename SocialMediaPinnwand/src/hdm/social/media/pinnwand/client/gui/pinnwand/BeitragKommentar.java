package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
/**
 * Diese Klasse ist f�r die Darstellung eines Kommentars zust�ndig.
 * Jeder Kommentar besteht aus einem Inhalt, einem Autor, einem Erstellungszeitpunkt und einem Button, welcher die M�glichkeit verschafft den Kommentar wieder zu l�schen.
 * @author remi
 *
 */
public class BeitragKommentar extends HorizontalPanel {
	
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	private Label LabelKommentarInhalt;
	private Label LabelKommentarAutor;
	private Label LabelErstellungszeitpunkt;
	private Button ButtonKommentarLoeschen;
	
	public BeitragKommentar(Nutzer nutzer, final Kommentar kommentar, String inhalt, String autor, String erstellungszeitpunkt){
		
		LabelKommentarInhalt = new Label(inhalt);
		LabelKommentarInhalt.setStyleName("LabelKommentarInhalt");
		this.add(LabelKommentarInhalt);
		
		LabelKommentarAutor = new Label(autor);
		LabelKommentarAutor.setStyleName("LabelKommentarAutor");
		this.add(LabelKommentarAutor);
		
		LabelErstellungszeitpunkt = new Label(erstellungszeitpunkt);
		LabelErstellungszeitpunkt.setStyleName("LabelErstellungszeitpunkt");
		this.add(LabelErstellungszeitpunkt);
		
		ButtonKommentarLoeschen = new Button("Kommentar l�schen");
		ButtonKommentarLoeschen.setStyleName("ButtonKommentarLoeschen");
		this.add(ButtonKommentarLoeschen);
		ButtonKommentarLoeschen.addClickHandler(new ClickHandler() {
		/**
		 * Beim Klick auf den "L�schen"-Button wird der Kommentar aus der Datenbank gel�scht.	 
		 */
				public void onClick(ClickEvent event) {
				PinnwandAdministration.deleteKommentar(kommentar, callbackVoid);	
			//	SocialMediaPinnwand sp = new SocialMediaPinnwand();
			//	sp.refresh();
				
				}
		});
		
		PinnwandAdministration.checkAuthorKommentar(nutzer, kommentar, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Boolean result) {
				if(result == true) {
					ButtonKommentarLoeschen.setVisible(true);
				} else ButtonKommentarLoeschen.setVisible(false);
				
			}
			  
		  });
	 }
		
	
	
	public Label getLabelKommentarInhalt() {
		return LabelKommentarInhalt;
	}

	public void setLabelKommentarInhalt(Label labelKommentarInhalt) {
		LabelKommentarInhalt = labelKommentarInhalt;
	}

	public Label getLabelKommentarAutor() {
		return LabelKommentarAutor;
	}

	public void setLabelKommentarAutor(Label labelKommentarAutor) {
		LabelKommentarAutor = labelKommentarAutor;
	}

	public Label getLabelErstellungszeitpunkt() {
		return LabelErstellungszeitpunkt;
	}

	public void setLabelErstellungszeitpunkt(Label labelErstellungszeitpunkt) {
		LabelErstellungszeitpunkt = labelErstellungszeitpunkt;
	}

	AsyncCallback<Void> callbackVoid
	 = new AsyncCallback<Void>() {
	 public void onFailure
	 (Throwable caught) {
	 // TODO: Do something with errors.
	 }
	 
	@Override
	public void onSuccess(Void result) {
	
		
	}
	 };
}
