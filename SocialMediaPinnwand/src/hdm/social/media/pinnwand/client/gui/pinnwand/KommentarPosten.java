package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Diese Klasse repräsentiert die Dialog Box welche sich öffnet wenn ein Nutzer auf den Button "Kommentieren" clickt. 
 * Mit Hilfe dieser DialogBox kann der Nutzer ein Kommentar hinzufügen.
 * 
 * @author remi
 *
 */
public class KommentarPosten extends DialogBox  {
	
private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);

private Nutzer nutzer;
private Beitrag beitrag;

private Label LabelInformation;
private TextArea TextAreaKommentar;
private Button ButtonSenden;
	public KommentarPosten(final Nutzer nutzer, final Beitrag beitrag, final ShowBeitraege showBeitraege) {
		this.nutzer = nutzer;
		this.beitrag = beitrag;
		
	/**
	 *  Label welches einen kleinen Information-Text darstellen soll	
	 */
	LabelInformation = new Label("Bitte geben Sie hier ihr Kommentar ein:");
	LabelInformation.setStyleName("LabelInformation");
	
	/**
	 *  TextArea für den Inhalt des Kommentars 
	 */
	TextAreaKommentar = new TextArea();
	TextAreaKommentar.setStyleName("TextAreaKommentar");
	
	/**
	 *  Button um den Kommentar abzusenden 
	 */
	ButtonSenden = new Button("Senden");
	ButtonSenden.setStyleName("ButtonSenden");
	ButtonSenden.addClickHandler(new ClickHandler() {
		 /**
		  *  Beim click auf den Button wird der Kommentar dem Beitrag hinzugefügt und das Dialog Fenster geschlossen
		  */
			public void onClick(ClickEvent event) {
				Kommentar k = new Kommentar();
				k.setNutzer(nutzer);
				k.setBeitrag(beitrag);
				k.setInhalt(TextAreaKommentar.getText());
				PinnwandAdministration.createKommentar(k, callback);
				hide();
				showBeitraege.refresh(nutzer);
			}
	});
	
	/**
	 *  Es wird ein DogPanel initialisiert welches alle oben initialisierten Elemente bündelt. 
	 *  Dem Dock Panel werden die zuvor initialisierten Elemente hinzugefügt 
	 */
	
	DockPanel dock = new DockPanel();
	dock.setSpacing(4);
	dock.add(LabelInformation, DockPanel.NORTH);
	dock.add(TextAreaKommentar, DockPanel.NORTH);
	dock.add(ButtonSenden, DockPanel.NORTH);
	dock.setWidth("100%");
	setWidget(dock);
	}
	
	/**
	 *  	Callback für die Methode CreateKommentar
	 */
	AsyncCallback<Kommentar> callback
	 = new AsyncCallback<Kommentar>() {
	 public void onFailure
	 (Throwable caught) {
	 // TODO: Do something with errors.
	 }
	 
	@Override
	public void onSuccess(Kommentar result) {
	
		
	}
	 };
	
}
