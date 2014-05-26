package hdm.social.media.pinnwand.client;

import java.util.ArrayList;

import hdm.social.media.pinnwand.server.PinnwandAdministrationImpl;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.FieldVerifier;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;

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
import com.google.gwt.user.client.ui.Grid;
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
	
	//private PinnwandBeitrag panel_PinnwandBeitrag;
	private FlexTable FlexTableBeitraege;
	final Label pinnwandName = new Label("");
	int pinnwandId;
	Nutzer nutzer;
	Pinnwand pinnwand;
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
		
	

		
		//dummy Nutzer
		nutzer = new Nutzer("Ferdinand", "Grüner", "fg035@hdm-stuttgart.de","suenos" );
		nutzer.setId(2);
		pinnwand = new Pinnwand();
		pinnwand.setId(2);
		pinnwand.setNutzer(nutzer);
		nutzer.setPinnwand(pinnwand);
		
		
		SplitLayoutPanel split = new SplitLayoutPanel();
		split.setStyleName("rootSplitPanel");
		
		SplitLayoutPanel vsplit = new SplitLayoutPanel();	
		RootLayoutPanel rp = RootLayoutPanel.get();
		int rootWidthSize = rp.getOffsetWidth();
		int rootHeightSize = rp.getOffsetHeight();
		
		
		
		
		
		VerticalPanel west = new VerticalPanel();
		HorizontalPanel east_up = new HorizontalPanel();
		HorizontalPanel east_down = new HorizontalPanel();
		
		 
	
		
		
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
		
		FlexTableAbonniertePinnwaende.setText(0, 0, "Name");
		FlexTableAbonniertePinnwaende.setText(1, 0, "Remi");
		
		/**
		 * Widgets der rechten Seite 
		 * Rechts-Oben:
		 */
		final TextArea TextAreaBeitragVerfassen = new TextArea();
		TextAreaBeitragVerfassen.setStyleName("TextAreaBeitragVerfassen");
		east_up.add(TextAreaBeitragVerfassen);
		
		final Button ButtonBeitragSenden = new Button("Senden");
		ButtonBeitragSenden.setStyleName("beitragSenden");
		
		
		/**
		 * Beitrag posten
		 */
		ButtonBeitragSenden.addClickHandler(new ClickHandler() {
			 /**
			  *  Beitrag wird der Pinnwand hinzugefügt
			  *  Anschließend wird die Beitragsliste aktualisiert. 
			  */
				public void onClick(ClickEvent event) {
					Beitrag b = new Beitrag();					
					b.setInhalt(TextAreaBeitragVerfassen.getValue());
					b.setPinnwand(pinnwand);
					
					PinnwandAdministration.saveBeitrag(b, callbackBeitrag);
					PinnwandAdministration.findAllBeitraege(callback);
					
				}
		});
		east_up.add(ButtonBeitragSenden);
		/**
		 * Rechts-unten:
		 */
		final Label LabelPinnwandInformation = new Label();
		LabelPinnwandInformation.setStyleName("LabelPinnwandInformation");
		east_down.add(LabelPinnwandInformation);
		
		FlexTableBeitraege = new FlexTable();
		FlexTableBeitraege.setStyleName("FlexTableBeitraege");
	
	
		/**
		 * Hinzufügen der Panels dem Rootpanel
		 */east_down.add(FlexTableBeitraege);
		split.addWest(west, (rootWidthSize/2));
		split.addEast(vsplit, (rootWidthSize/2));
		vsplit.addNorth(east_up, (rootHeightSize/2));
		vsplit.addSouth(east_down, (rootHeightSize/2));
		rp.add(split);
		 PinnwandAdministration.findAllBeitraege(callback);
		 
		 


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
		 
		 
		 AsyncCallback<Pinnwand> callbackGetPinnwandById
		 = new AsyncCallback<Pinnwand>() {
		 public void onFailure
		 (Throwable caught) {
		 // TODO: Do something with errors.
		 }

		@Override
		public void onSuccess(Pinnwand result) {
			getPinnwandById(result);
			
		}
		 };
		 
		 AsyncCallback<Nutzer> callbackNutzerbyId
		 = new AsyncCallback<Nutzer>() {
		 public void onFailure
		 (Throwable caught) {
		 // TODO: Do something with errors.
		 }

		@Override
		public void onSuccess(Nutzer result1) {
			getNutzerById(result1);
			
		}
		 };
		 
		 AsyncCallback<Void> callbackBeitrag
		 = new AsyncCallback<Void>() {
		 public void onFailure
		 (Throwable caught) {
		 // TODO: Do something with errors.
		 }

		@Override
		public void onSuccess(Void result1) {
			
			
		}
		 };
	
	private void getPinnwandById(Pinnwand result) {
		pinnwandId = result.getId();
	}
	private void getNutzerById(Nutzer result1) {
		nutzer = result1;
		System.out.println(nutzer.getName());
	}
	
	// Methode welche alle Beiträge ausgibt
	public void printOutAll(ArrayList<Beitrag> result) {
		// Hilfvariable um festzuhalten in welcher Row man sich befindet
		int aktuelleRow = 0;
		for(int i= 0; i < result.size(); i++){
		// Hilfsmethode welche den Nutzer sucht ( braucht man nicht mehr nach Erik seinem Stand) 
		PinnwandAdministration.getNutzerById(result.get(i).getPinnwand().getId(), callbackNutzerbyId);
		
		// Hinzufügen eines neuen Beitrags in der aktuellen Zeile, Dabei wird ein neues LayoutObjekt initialsiert und der FlexTable hinzugefügt
		FlexTableBeitraege.setWidget(aktuelleRow, 0, new PinnwandBeitrag(result.get(i).getInhalt(), "von "+ result.get(i).getNutzer().getName() +","+result.get(i).getErstellungsZeitpunkt(),  + result.get(i).getLikeList().size()  + " Personen gefaellt das.", result.get(i),nutzer ));
		// nachdem ein Beitrag der FlexTable hinzugefügt wurde wird die aktuelle Zeile um 1 erhöht.
		aktuelleRow += 1;
		// Nun werden alle Kommentare des zuvor hinzugefügten Beitrages der FlexTable hinzugefügt
		for(int a = 0; a < result.get(i).getKommentarListe().size(); a++) {
			
			FlexTableBeitraege.setWidget(aktuelleRow, 0, new BeitragKommentar(result.get(i).getKommentarListe().get(a), result.get(i).getKommentarListe().get(a).getInhalt(), " ,von " + result.get(i).getKommentarListe().get(a).getNutzer().getName(), String.valueOf(result.get(i).getKommentarListe().get(a).getErstellungsZeitpunkt())));
			aktuelleRow += 1;
			
		}
		}
		
	}

	public void refresh() {
		PinnwandAdministration.findAllBeitraege(callback);
		
	}
	
	
	
	
	}
	