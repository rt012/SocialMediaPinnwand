package hdm.social.media.pinnwand.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class BeitragKommentar extends HorizontalPanel {
	
	private Label LabelKommentarInhalt;
	private Label LabelKommentarAutor;
	private Label LabelErstellungszeitpunkt;
	
	public BeitragKommentar(String inhalt, String autor, String erstellungszeitpunkt){
		
		LabelKommentarInhalt = new Label(inhalt);
		LabelKommentarInhalt.setStyleName("LabelKommentarInhalt");
		this.add(LabelKommentarInhalt);
		
		LabelKommentarAutor = new Label(autor);
		LabelKommentarAutor.setStyleName("LabelKommentarAutor");
		this.add(LabelKommentarAutor);
		
		LabelErstellungszeitpunkt = new Label(erstellungszeitpunkt);
		LabelErstellungszeitpunkt.setStyleName("LabelErstellungszeitpunkt");
		this.add(LabelErstellungszeitpunkt);
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

	
}
