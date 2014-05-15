package hdm.social.media.pinnwand.client;

import java.util.ArrayList;

import hdm.social.media.pinnwand.shared.Beitrag;
import hdm.social.media.pinnwand.shared.Like;
import hdm.social.media.pinnwand.shared.Nutzer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Diese Klasse ist die Basisklasse aller Beiträge. Jeder Beitrag ist ein HorizontalPanel und lässt sich 
 * somit unter GWT entsprechend anordnen 
 * @author remi
 *
 */

public class PinnwandBeitrag extends HorizontalPanel {
	
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	/**
	   * Jedes GWT Widget muss diese Methode implementieren. Sie gibt an, was
	   * geschehen soll, wenn eine Widget-Instanz zur Anzeige gebracht wird.
	   */
	private Label LabelBeitragsInhalt;
	private Label LabelBeitragsAutor;
	private Label LabelBeitragLikeAnzahl;
	private Button ButtonBeitragGefaelltMir;
	private Button ButtonBeitragKommentieren;
	
	private  Beitrag beitrag;
	private Nutzer nutzer;
	
	
	 public PinnwandBeitrag(String Inhalt, String Beitragsautor, String LikeAnzahl, final Beitrag beitrag, final Nutzer nutzer) {   
		 /**
		  * Elemente ( Widgets)  eines Beitrags
		  */
		 this.beitrag = beitrag;
		 this.nutzer = nutzer;
		 //Anzeige des Inhalts 
		 LabelBeitragsInhalt = new Label(Inhalt);
		 LabelBeitragsInhalt.setStyleName("LabelBeitragsInhalt");
		 this.add(LabelBeitragsInhalt);
		 
		 // Autor + Erstellungszeitpunkt
		 
		 LabelBeitragsAutor = new Label(Beitragsautor);
		 this.add(LabelBeitragsAutor);
		 LabelBeitragsAutor.setStyleName("LabelBeitragsAutor");
		 
		 
		 // Like Anzahl
		 LabelBeitragLikeAnzahl = new Label(LikeAnzahl);
		 LabelBeitragLikeAnzahl.setStyleName("LabelBeitragLikeAnzahl");
		 this.add(LabelBeitragLikeAnzahl);
		 
		 //Button für "Gefällt mir
		 ButtonBeitragGefaelltMir = new Button("Gefaellt mir");
		 ButtonBeitragGefaelltMir.setStyleName("ButtonBeitragGefälltMir");
		 ButtonBeitragGefaelltMir.addClickHandler(new ClickHandler() {
			 
				public void onClick(ClickEvent event) {
					Like l = new Like();
					l.setBeitrag(beitrag);
					l.setNutzer(nutzer);
					
					
					PinnwandAdministration.createLike(l, callback); 
							
				}
		});
		 
		 this.add(ButtonBeitragGefaelltMir);
		 
		 //Button für Kommentieren
		  ButtonBeitragKommentieren = new Button("Kommentieren");
		  ButtonBeitragKommentieren.setStyleName("ButtonBeitragKommentieren");
		  this.add(ButtonBeitragKommentieren);
	    
	 }
	public Label getLabelBeitragsInhalt() {
		return LabelBeitragsInhalt;
	}
	public void setLabelBeitragsInhalt(Label labelBeitragsInhalt) {
		LabelBeitragsInhalt = labelBeitragsInhalt;
	}
	public Label getLabelBeitragsAutor() {
		return LabelBeitragsAutor;
	}
	public void setLabelBeitragsAutor(Label labelBeitragsAutor) {
		LabelBeitragsAutor = labelBeitragsAutor;
	}
	public Label getLabelBeitragLikeAnzahl() {
		return LabelBeitragLikeAnzahl;
	}
	public void setLabelBeitragLikeAnzahl(Label labelBeitragLikeAnzahl) {
		LabelBeitragLikeAnzahl = labelBeitragLikeAnzahl;
	}
	public Button getButtonBeitragGefaelltMir() {
		return ButtonBeitragGefaelltMir;
	}
	public void setButtonBeitragGefaelltMir(Button buttonBeitragGefaelltMir) {
		ButtonBeitragGefaelltMir = buttonBeitragGefaelltMir;
	}
	public Button getButtonBeitragKommentieren() {
		return ButtonBeitragKommentieren;
	}
	public void setButtonBeitragKommentieren(Button buttonBeitragKommentieren) {
		ButtonBeitragKommentieren = buttonBeitragKommentieren;
	}
	
	 AsyncCallback<Like> callback
	 = new AsyncCallback<Like>() {
	 public void onFailure
	 (Throwable caught) {
	 // TODO: Do something with errors.
	 }
	 
	@Override
	public void onSuccess(Like result) {
	
		
	}
	 };
}
