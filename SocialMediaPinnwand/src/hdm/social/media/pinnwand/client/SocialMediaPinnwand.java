package hdm.social.media.pinnwand.client;

import java.util.ArrayList;

import hdm.social.media.pinnwand.server.PinnwandAdministrationImpl;
import hdm.social.media.pinnwand.server.db.AboMapper;
import hdm.social.media.pinnwand.server.db.NutzerMapper;
import hdm.social.media.pinnwand.shared.Abo;
import hdm.social.media.pinnwand.shared.Beitrag;
import hdm.social.media.pinnwand.shared.FieldVerifier;
import hdm.social.media.pinnwand.shared.Nutzer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SocialMediaPinnwand implements EntryPoint {
	
//	private PinnwandBeitrag panel_PinnwandBeitrag;
	
	final Label pinnwandName = new Label("");
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
	//	panel_PinnwandBeitrag = new PinnwandBeitrag();
		
		
		SplitLayoutPanel split = new SplitLayoutPanel();
		split.setStyleName("rootSplitPanel");
		
		SplitLayoutPanel vsplit = new SplitLayoutPanel();
		RootLayoutPanel rp = RootLayoutPanel.get();
		int rootWidthSize = rp.getOffsetWidth();
		int rootHeightSize = rp.getOffsetHeight();
		
		
		
		
		
		VerticalPanel west = new VerticalPanel();
		HorizontalPanel east_up = new HorizontalPanel();
		HorizontalPanel east_down = new HorizontalPanel();
		
		 
		split.addWest(west, (rootWidthSize/2));
		split.addEast(vsplit, (rootWidthSize/2));
		vsplit.addNorth(east_up, (rootHeightSize/2));
		vsplit.addSouth(east_down, (rootHeightSize/2));
		
		
		/**
		 * Widgets der linken Seite 
		 */
		
		pinnwandName.setStyleName("pinnwandName");
		west.add(pinnwandName);
		
		final SuggestBox SuggestBoxPinnwandSuche = new SuggestBox();
		SuggestBoxPinnwandSuche.setStyleName("SuggestBoxPinnwandSuche");
		west.add(SuggestBoxPinnwandSuche);

		
		
		final FlexTable FlexTableAbonniertePinnwaende = new FlexTable();
		FlexTableAbonniertePinnwaende.setStyleName("FlexTableAbonniertePinnwaende");
		west.add(FlexTableAbonniertePinnwaende);
		
		PinnwandAdministration.getAboByNutzer(new AsyncCallback<ArrayList<Abo>>() {
			public void onFailure
			 (Throwable caught) {
			 // TODO: Do something with errors.
			 }
			
			@Override
			public void onSuccess(ArrayList<Abo> result) {
				for (int i=0; i <result.size(); i++){
					FlexTableAbonniertePinnwaende.setText(i, 0, result.get(i).getLieferant().getVorname()+ " "+ 
							result.get(i).getLieferant().getName());	
				}
				
			}
		});
		
		
		/**
		 * Widgets der rechten Seite 
		 * Rechts-Oben:
		 */
		final TextArea TextAreaBeitragVerfassen = new TextArea();
		TextAreaBeitragVerfassen.setStyleName("TextAreaBeitragVerfassen");
		east_up.add(TextAreaBeitragVerfassen);
		
		final Button ButtonBeitragSenden = new Button("Senden");
		ButtonBeitragSenden.setStyleName("beitragSenden");
		east_up.add(ButtonBeitragSenden);
		
		/**
		 * Rechts-unten:
		 */
		final Label LabelPinnwandInformation = new Label();
		LabelPinnwandInformation.setStyleName("LabelPinnwandInformation");
		east_down.add(LabelPinnwandInformation);
		
		final FlexTable FlexTableBeitraege = new FlexTable();
		FlexTableBeitraege.setStyleName("FlexTableBeitraege");
		east_down.add(FlexTableBeitraege);
		
		//FlexTableBeitraege.add(panel_PinnwandBeitrag);
		
		/**
		 * Hinzufügen der Panels dem Rootpanel
		 */

		 rp.add(split);
		// PinnwandAdministration.findAllBeitraege(callback);
		 
		 /**
		  * CustomLayout für Beiträge
		  */
//		 HorizontalPanel pinnwandBeitrag = new HorizontalPanel();
		 /**
		  * Elemente ( Widgets)  eines Beitrags
		  */
/*		 //Anzeige des Inhalts 
		 final Label LabelBeitragsInhalt = new Label();
		 LabelBeitragsInhalt.setStyleName("LabelBeitragsInhalt");
		 pinnwandBeitrag.add(LabelBeitragsInhalt);
		 
		 // Autor + Erstellungszeitpunkt
		 final Label LabelBeitragsAutor = new Label();
		 LabelBeitragsAutor.setStyleName("LabelBeitragsAutor");
		 pinnwandBeitrag.add(LabelBeitragsAutor);
		 
		 // Like Anzahl
		 final Label LabelBeitragLikeAnzahl = new Label();
		 LabelBeitragLikeAnzahl.setStyleName("LabelBeitragLikeAnzahl");
		 pinnwandBeitrag.add(LabelBeitragLikeAnzahl);
		 
		 //Button für "Gefällt mir
		 final Button ButtonBeitragGefälltMir = new Button("Gefällt mir");
		 ButtonBeitragGefälltMir.setStyleName("ButtonBeitragGefälltMir");
		 pinnwandBeitrag.add(ButtonBeitragGefälltMir);
		 
		 //Button für Kommentieren
		 final Button ButtonBeitragKommentieren = new Button("Kommentieren");
		 ButtonBeitragKommentieren.setStyleName("ButtonBeitragKommentieren");
		 pinnwandBeitrag.add(ButtonBeitragKommentieren);
		 */
	}
	
		 AsyncCallback<ArrayList<Beitrag>> callback
		 = new AsyncCallback<ArrayList<Beitrag>>() {
		 public void onFailure
		 (Throwable caught) {
		 // TODO: Do something with errors.
		 }
		 
		@Override
		public void onSuccess(ArrayList<Beitrag> result) {
		 printOutAll(result);
			
		}
		 };

	
	public void printOutAll(ArrayList<Beitrag> result) {
		for(int i= 0; i < result.size(); i++){
		System.out.println(result.get(i).getInhalt()); }
		
	}
	}

	
