package hdm.social.media.pinnwand.client;

import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class BeitragKommentar extends HorizontalPanel {
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	private Label LabelKommentarInhalt;
	private Label LabelKommentarAutor;
	private Label LabelErstellungszeitpunkt;
	private Button ButtonKommentarLoeschen;
	
	public BeitragKommentar(final Kommentar kommentar, String inhalt, String autor, String erstellungszeitpunkt){
		
		LabelKommentarInhalt = new Label(inhalt);
		LabelKommentarInhalt.setStyleName("LabelKommentarInhalt");
		this.add(LabelKommentarInhalt);
		
		LabelKommentarAutor = new Label(autor);
		LabelKommentarAutor.setStyleName("LabelKommentarAutor");
		this.add(LabelKommentarAutor);
		
		LabelErstellungszeitpunkt = new Label(erstellungszeitpunkt);
		LabelErstellungszeitpunkt.setStyleName("LabelErstellungszeitpunkt");
		this.add(LabelErstellungszeitpunkt);
		
		ButtonKommentarLoeschen = new Button("Kommentar löschen");
		ButtonKommentarLoeschen.setStyleName("ButtonKommentarLoeschen");
		this.add(ButtonKommentarLoeschen);
		ButtonKommentarLoeschen.addClickHandler(new ClickHandler() {
			 
				public void onClick(ClickEvent event) {
				PinnwandAdministration.deleteKommentar(kommentar, callbackVoid);	
			//	SocialMediaPinnwand sp = new SocialMediaPinnwand();
			//	sp.refresh();
				
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
