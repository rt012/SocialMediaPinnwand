package hdm.social.media.pinnwand.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import hdm.social.media.pinnwand.client.gui.AbonnementCustomDialog;
import hdm.social.media.pinnwand.client.gui.CustomOracle;
import hdm.social.media.pinnwand.client.gui.CustomSuggest;
import hdm.social.media.pinnwand.client.gui.LoginCustomDialog;
import hdm.social.media.pinnwand.shared.Nutzer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SocialMediaPinnwand implements EntryPoint {
	
//	private PinnwandBeitrag panel_PinnwandBeitrag;
	
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access the StockWatcher application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Nutzer aktuellerNutzer = null;
	
	private final Label pinnwandName = new Label("");
	private CustomOracle oracle = new CustomOracle();;
	private final SuggestBox SuggestBoxPinnwandSuche = new SuggestBox(oracle);
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
		/**
		 * <code>UserLogin</code> gibt den aktuell eingeloggten Nutzer zurück
		 * Wenn der Nutzer nicht bei Google angemeldet ist, gibt die Methode
		 * <code> UserLogin().getUser() null zurück
		 * 
		 * @author Eric Schmidt 
		 */
		 // Check login status using login service.
		PinnwandAdministration.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {}

			public void onSuccess(LoginInfo result) {
		        loginInfo = result;
		        if(loginInfo.isLoggedIn()) {
		        	nutzerInDatenbank(result);
		        	loadSocialMediaPinnwand();
		        } else {
		        	loadLogin();
		        }
			}
	    });	
		
	}	

	/**
	 * Prüft anhand der Email-Adresse ob der angemeldete Nutzer bereits in der Datenbank ist
	 * 
	 * @author Eric Schmidt
	 */
	public void nutzerInDatenbank(final LoginInfo googleNutzer){
		//getNutzerByEmail wäre hier schöner!
		PinnwandAdministration.getAllNutzer(new AsyncCallback<ArrayList<Nutzer>>() {
			 public void onFailure
			 (Throwable caught) {
			 // TODO: Do something with errors.
			 }
			 
			@Override
			public void onSuccess(ArrayList<Nutzer> result) {
				for (Nutzer n : result){
					if (n.getEmail() == googleNutzer.getEmailAddress()){
						aktuellerNutzer = n;
					}
				}
				if (aktuellerNutzer == null){
					createNutzer(googleNutzer);
				}
			}
		});
	}

	/**
	 * Legt den Nutzer in der Datenbank an
	 * 
	 * @author Eric Schmidt 
	 */
	public void createNutzer(final LoginInfo googleNutzer){
		final Nutzer nutzer = new Nutzer();
		nutzer.setEmail(googleNutzer.getEmailAddress());
		nutzer.setErstellungsZeitpunkt(new Date());
		
		
		/**
		 * Vordert den Nutzer auf Vor-, Nachname und Nickname einzugeben,
		 * da diese Information nicht über die Google API bezogen werden kann
		 * bzw. geändert werden soll
		 * 
		 * @author Eric Schmidt
		 */
		final LoginCustomDialog dialog = new LoginCustomDialog(googleNutzer.getNickname());
		DialogBox dlb = dialog;
		dlb.center();
		
		dlb.addCloseHandler(new CloseHandler(){
			@Override
			public void onClose(CloseEvent event) {
				nutzer.setVorname(dialog.getVorname());
				nutzer.setName(dialog.getNachname());
				nutzer.setNickname(dialog.getNickname());
				PinnwandAdministration.createNutzer(nutzer, new AsyncCallback<Nutzer>(){
					@Override
					public void onFailure(Throwable caught) {}

					@Override
					public void onSuccess(Nutzer result) {
						aktuellerNutzer = result;
						
						/**
						 * Update die SuggestBox
						 */
						PinnwandAdministration.getAllNutzer(new AsyncCallback<ArrayList<Nutzer>>() {
							 public void onFailure
							 (Throwable caught) {
							 // TODO: Do something with errors.
							 }
							 
							@Override
							public void onSuccess(ArrayList<Nutzer> result) {
							 fillSuggestenBox(result);
							}
						});
					}
					
				});
			}
		});

	}
	
	/**
	 * Lade die Widget Elemente der GWT Applikation
	 * 
	 * @author Eric Schmidt
	 */
	public void loadSocialMediaPinnwand(){
		
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
		
		SuggestBoxPinnwandSuche.setStyleName("SuggestBoxPinnwandSuche");
		west.add(SuggestBoxPinnwandSuche);
		SuggestBoxPinnwandSuche.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>(){
			public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event){
				Nutzer n = ((CustomSuggest)event.getSelectedItem()).getNutzer();
				DialogBox dlg = new AbonnementCustomDialog("Abonnieren", "Pinnwand von"
			    		+ n.getVorname() + " wirklich abonnieren?", n, aktuellerNutzer);
		        dlg.center();
			}
		});
		/**
		 * Läd alle Nutzer in das Such-Feld
		 */
		PinnwandAdministration.getAllNutzer(new AsyncCallback<ArrayList<Nutzer>>() {
			 public void onFailure
			 (Throwable caught) {
			 // TODO: Do something with errors.
			 }
			 
			@Override
			public void onSuccess(ArrayList<Nutzer> result) {
			 fillSuggestenBox(result);
			}
		});
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
		
		/**
		 * Hinzufügen der Panels dem Rootpanel
		 */
		 rp.add(split);
	}
	
	/**
	 * Befüllt die SuggestenBox mit den Nutzern
	 * 
	 * @param nutzer Sämtliche Nutzer Objekte
	 */
	public void fillSuggestenBox(ArrayList<Nutzer> nutzer){
		ArrayList<CustomSuggest> suggestList = new ArrayList<CustomSuggest>();
		for (Nutzer n : nutzer) {
			CustomSuggest suggest = new CustomSuggest(n);
			suggestList.add(suggest);
		}
		Collection<CustomSuggest> nutzerCollection = suggestList;
		oracle.setCollection(nutzerCollection);
		SuggestBoxPinnwandSuche.ensureDebugId("cwSuggestBox");
	}
	
	/**
	 * Sollte der User nicht bei Google angemeldet sein, 
	 * verlinke auf login Seite von Google
	 * 
	 */
	  private void loadLogin() {
		    // Assemble login panel.
		    signInLink.setHref(loginInfo.getLoginUrl());
		    loginPanel.add(loginLabel);
		    loginPanel.add(signInLink);
		    RootPanel.get().add(loginPanel);
	  }
}
	