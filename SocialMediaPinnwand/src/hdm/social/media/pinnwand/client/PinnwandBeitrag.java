package hdm.social.media.pinnwand.client;

import java.util.ArrayList;

import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Diese Klasse ist die Basisklasse aller Beitr�ge. Jeder Beitrag ist ein HorizontalPanel und l�sst sich 
 * somit unter GWT entsprechend anordnen 
 * @author remi
 *
 */

public class PinnwandBeitrag extends HorizontalPanel {
	
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	
	private Label LabelBeitragsInhalt;
	private Label LabelBeitragsAutor;
	private Label LabelBeitragLikeAnzahl;
	private Button ButtonBeitragGefaelltMir;
	private Button ButtonBeitragKommentieren;
	private Button ButtonBeitragLoeschen;
	private Beitrag beitrag;
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
		 LabelBeitragLikeAnzahl.addClickHandler(new ClickHandler() {
		 // Bei Klick auf die Likeanzahl, �ffnet sich eine Dialogbox, welche die likenden Nutzer anzeigt
				public void onClick(ClickEvent event) {
					final LikeListe dialog = new LikeListe(beitrag.getLikeList());
					DialogBox dlb = dialog;
					dlb.center();
				}
		});
		
		 
	
		 //Button f�r "Gef�llt mir
		 ButtonBeitragGefaelltMir = new Button("");
		 ButtonBeitragGefaelltMir.setStyleName("ButtonBeitragGef�lltMir");
		 this.add(ButtonBeitragGefaelltMir);
		 // Methode welche �berpr�ft ob der Nutzer den Beitrag schon geliked hat oder nicht
		 PinnwandAdministration.checkIfliked(this.nutzer, this.beitrag, callbackBoolean);
		 
		 //Button f�r Kommentieren
		  ButtonBeitragKommentieren = new Button("Kommentieren");
		  ButtonBeitragKommentieren.setStyleName("ButtonBeitragKommentieren");
		  this.add(ButtonBeitragKommentieren);
		  ButtonBeitragKommentieren.addClickHandler(new ClickHandler() {
				// �ffnet eine Dialog Box mit einer TextArea, um einen Kommentar zu schreiben
				public void onClick(ClickEvent event) {
					final KommentarPosten dialog = new KommentarPosten(nutzer, beitrag);
					DialogBox dlb = dialog;
					dlb.center();
				}
		});
		
	  //Button um Beitrag zu l�schen
		  
		  ButtonBeitragLoeschen = new Button("Beitrag l�schen");
		  ButtonBeitragLoeschen.setStyleName("ButtonBeitragL�schen");
		  this.add(ButtonBeitragLoeschen);
		  ButtonBeitragLoeschen.addClickHandler(new ClickHandler() {
				 // Nach click auf den "Beitrag L�schen"-Button wird der ausgew�hlte Beitrag gel�scht
				public void onClick(ClickEvent event) {
				PinnwandAdministration.deleteBeitrag(beitrag, callbackVoid);	
				SocialMediaPinnwand sp = new SocialMediaPinnwand();
				sp.refresh();
				
				}
		});
		 
	    
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
	 
	 AsyncCallback<Boolean> callbackBoolean
	 = new AsyncCallback<Boolean>() {
	 public void onFailure
	 (Throwable caught) {
	 // TODO: Do something with errors.
	 }
	 
	@Override
	public void onSuccess(Boolean result) {
	/**
	 * Bearbeitet das Ergebnis von CheckifLiked Methode von PinnwandAdministration
	 */
		// true bedeutet der Nutzer hat den Beitrag noch nicht geliked
		if(result == true) { 
		setButtonToLike();
		
		}
		
		else{ setButtonToDislike();
					
			}
	
	}
	 };
	 
	 // Methode welche den Button zu einem "Gef�llt mir" - Button macht
	 public void setButtonToLike() {
		 ButtonBeitragGefaelltMir.setText("Gef�llt mir"); 
		 ButtonBeitragGefaelltMir.addClickHandler(new ClickHandler() {
			 // Beim Click auf den Button wird der Like hinzugef�gt und die Methode setButtonToDislike aufgerufen 
				public void onClick(ClickEvent event) {
					Like l = new Like();
					l.setBeitrag(beitrag);
					l.setNutzer(nutzer);
					
					
					PinnwandAdministration.createLike(l, callback); 
					setButtonToDislike();	
				}
		});
		 
	 }
	 
	 // Methode welche aus dem Button ein "Gef�llt mir nicht mehr" - Button macht
	public void setButtonToDislike() {
		 
		 ButtonBeitragGefaelltMir.setText("Gef�llt mir nicht mehr");
		 ButtonBeitragGefaelltMir.addClickHandler(new ClickHandler() {
			 // L�scht den Like und setzt den Button wieder auf "Gef�llt mir"
				public void onClick(ClickEvent event) {
					Like l = new Like();
					l.setBeitrag(beitrag);
					l.setNutzer(nutzer);
					
					
					PinnwandAdministration.deleteLike(l, callbackVoid); 
					setButtonToLike();
				}
		 });
		 
	 }
}
