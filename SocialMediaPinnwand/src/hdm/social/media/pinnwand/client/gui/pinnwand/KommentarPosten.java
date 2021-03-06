package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.client.SocialMediaPinnwand;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Diese Klasse repr�sentiert die Dialog Box welche sich �ffnet wenn ein Nutzer auf den Button "Kommentieren" clickt. 
 * Mit Hilfe dieser DialogBox kann der Nutzer ein Kommentar hinzuf�gen.
 * 
 * @author remi
 *
 */
public class KommentarPosten extends DialogBox  {
	
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	
	private SocialMediaPinnwand s;
	
	private Label LabelInformation;
	private TextArea TextAreaKommentar;
	private Button ButtonSenden;
		
	public KommentarPosten(final SocialMediaPinnwand s, final Beitrag beitrag, final ShowBeitraege showBeitraege) {
		this.s = s;
			
		/**
		 *  Label welches einen kleinen Information-Text darstellen soll	
		 */
		LabelInformation = new Label("Bitte geben Sie hier ihr Kommentar ein:");
		LabelInformation.setStyleName("LabelInformation");
		
		/**
		 *  TextArea f�r den Inhalt des Kommentars 
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
		  *  Beim click auf den Button wird der Kommentar dem Beitrag hinzugef�gt und das Dialog Fenster geschlossen
		  */
		public void onClick(ClickEvent event) {
			if(TextAreaKommentar.getValue() != ""){
				Kommentar k = new Kommentar();
				k.setNutzer(s.getAktuellerNutzer());
				k.setBeitrag(beitrag);
				k.setInhalt(TextAreaKommentar.getText());
				PinnwandAdministration.createKommentar(k, new AsyncCallback<Kommentar>() {
					@Override
					public void onFailure (Throwable caught) { }
						 
					@Override
					public void onSuccess(Kommentar result) {
						KommentarPosten.this.hide();
						showBeitraege.refresh(s.getAktuellerNutzer());
					}
				});
			}
			else{
				hide();
				}
			}
		});
	
	/**
	 *  Es wird ein DogPanel initialisiert welches alle oben initialisierten Elemente b�ndelt. 
	 *  Dem Dock Panel werden die zuvor initialisierten Elemente hinzugef�gt 
	 */
	
	DockPanel dock = new DockPanel();
	dock.setSpacing(4);
	dock.add(LabelInformation, DockPanel.NORTH);
	dock.add(TextAreaKommentar, DockPanel.NORTH);
	dock.add(ButtonSenden, DockPanel.NORTH);
	dock.setWidth("100%");
	setWidget(dock);
	}
	
	
	
}
