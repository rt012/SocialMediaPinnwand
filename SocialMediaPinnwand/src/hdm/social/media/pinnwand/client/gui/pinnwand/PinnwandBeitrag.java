package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.client.SocialMediaPinnwand;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;

import java.util.ArrayList;

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
 * Diese Klasse ist die Basisklasse aller Beiträge. Jeder Beitrag ist ein HorizontalPanel und lässt sich 
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
	private ShowBeitraege showBeitraege = null;
	
	
	
	 public PinnwandBeitrag(String Inhalt, String Beitragsautor, String LikeAnzahl, final Beitrag beitrag, final Nutzer nutzer, final ShowBeitraege showBeitraege) {   
		 /**
		  * Elemente ( Widgets)  eines Beitrags
		  */
		 this.beitrag = beitrag;
		 this.nutzer = nutzer;
		 this.showBeitraege = showBeitraege;
		 
		/**
		 * Anzeige des Inhalts 
		 */
		 LabelBeitragsInhalt = new Label(Inhalt);
		 LabelBeitragsInhalt.setStyleName("LabelBeitragsInhalt");
		 this.add(LabelBeitragsInhalt);
		 
		 /**
		  *  Autor + Erstellungszeitpunkt
		  */
		 
		 LabelBeitragsAutor = new Label(Beitragsautor);
		 this.add(LabelBeitragsAutor);
		 LabelBeitragsAutor.setStyleName("LabelBeitragsAutor");
		 
		 
		 /**
		  *  Like Anzahl
		  */
		 LabelBeitragLikeAnzahl = new Label(LikeAnzahl);
		 LabelBeitragLikeAnzahl.setStyleName("LabelBeitragLikeAnzahl");
		 this.add(LabelBeitragLikeAnzahl);
		 LabelBeitragLikeAnzahl.addClickHandler(new ClickHandler() {
		 /**
		  *  Bei Klick auf die Likeanzahl, öffnet sich eine Dialogbox, welche die likenden Nutzer anzeigt
		  */
				public void onClick(ClickEvent event) {
					final LikeListe dialog = new LikeListe(beitrag.getLikeList());
					DialogBox dlb = dialog;
					dlb.center();
				}
		});
		
		 
	
		/**
		 * Button für "Gefällt mir
		 */
		 ButtonBeitragGefaelltMir = new Button("");
		 ButtonBeitragGefaelltMir.setStyleName("ButtonBeitragGefälltMir");
		 this.add(ButtonBeitragGefaelltMir);
		 /**
		  *  Methode welche überprüft ob der Nutzer den Beitrag schon geliked hat oder nicht
		  */
		 PinnwandAdministration.checkIfliked(this.nutzer, this.beitrag, callbackBoolean);

		 
		 /**
		  * Button für Kommentieren
		  */
		  ButtonBeitragKommentieren = new Button("Kommentieren");
		  ButtonBeitragKommentieren.setStyleName("ButtonBeitragKommentieren");
		  this.add(ButtonBeitragKommentieren);
		  ButtonBeitragKommentieren.addClickHandler(new ClickHandler() {
				// Öffnet eine Dialog Box mit einer TextArea, um einen Kommentar zu schreiben
				public void onClick(ClickEvent event) {
					final KommentarPosten dialog = new KommentarPosten(nutzer, beitrag, showBeitraege);
					DialogBox dlb = dialog;
					dlb.center();
					
				}
		});
		
	  /**
	   * Button um Beitrag zu löschen
	   */
		  
		  ButtonBeitragLoeschen = new Button("Beitrag löschen");
		  ButtonBeitragLoeschen.setStyleName("ButtonBeitragLöschen");
		  this.add(ButtonBeitragLoeschen);
		  ButtonBeitragLoeschen.addClickHandler(new ClickHandler() {
				 // Nach click auf den "Beitrag Löschen"-Button wird der ausgewählte Beitrag gelöscht
				public void onClick(ClickEvent event) {
				PinnwandAdministration.deleteBeitrag(beitrag, callbackVoid);	
				SocialMediaPinnwand sp = new SocialMediaPinnwand();
				showBeitraege.refresh(nutzer);
				
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
	 
	/**
	 *  Methode welche den Button zu einem "Gefällt mir" - Button macht
	 */
	 public void setButtonToLike() {
		 ButtonBeitragGefaelltMir.setText("Gefällt mir"); 
		 ButtonBeitragGefaelltMir.addClickHandler(new ClickHandler() {
			 // Beim Click auf den Button wird der Like hinzugefügt und die Methode setButtonToDislike aufgerufen 
				public void onClick(ClickEvent event) {
					Like l = new Like();
					l.setBeitrag(beitrag);
					l.setNutzer(nutzer);
					PinnwandAdministration.createLike(l, callback); 
					setButtonToDislike();	
					showBeitraege.refresh(nutzer);
				}
		});
		 
	 }
	 
	 /**
	  *  Methode welche aus dem Button ein "Gefällt mir nicht mehr" - Button macht
	  */
	public void setButtonToDislike() {
		 
		 ButtonBeitragGefaelltMir.setText("Gefällt mir nicht mehr");
		 ButtonBeitragGefaelltMir.addClickHandler(new ClickHandler() {
			 // Löscht den Like und setzt den Button wieder auf "Gefällt mir"
				public void onClick(ClickEvent event) {
					Like l = new Like();
					l.setBeitrag(beitrag);
					l.setNutzer(nutzer);
					PinnwandAdministration.deleteLike(l, callbackVoid); 
					setButtonToLike();
					showBeitraege.refresh(nutzer);
				}
		 });
		 
	 }
}
