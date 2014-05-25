package hdm.social.media.pinnwand.client;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import hdm.social.media.pinnwand.server.PinnwandAdministrationImpl;
import hdm.social.media.pinnwand.shared.Abo;
import hdm.social.media.pinnwand.shared.Beitrag;
import hdm.social.media.pinnwand.shared.FieldVerifier;
import hdm.social.media.pinnwand.shared.Like;
import hdm.social.media.pinnwand.shared.Nutzer;
import hdm.social.media.pinnwand.shared.Pinnwand;
import hdm.social.media.pinnwand.client.gui.AbonnementCustomDialog;
import hdm.social.media.pinnwand.client.gui.CustomOracle;
import hdm.social.media.pinnwand.client.gui.CustomSuggest;
import hdm.social.media.pinnwand.client.gui.LoginCustomDialog;
import hdm.social.media.pinnwand.shared.Abo;
import hdm.social.media.pinnwand.shared.Nutzer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SocialMediaPinnwand implements EntryPoint {
	
	//	private PinnwandBeitrag panel_PinnwandBeitrag;
	
	// Widgets zur Realisierung des Logins f�r Anzeige
	private LoginInfo loginInfo = null;

	//Aktiver/Aktueller Nutzer
	private Nutzer aktuellerNutzer = null;

	//Verweis auf CustomOracle -> Verwaltungder Suggestionbox um Nutzerobjekte zu speichern

	private CustomOracle oracle = new CustomOracle();
	private final SuggestBox SuggestBoxPinnwandSuche = new SuggestBox(oracle);

	//private PinnwandBeitrag panel_PinnwandBeitrag;
	private FlexTable FlexTableBeitraege;
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
		/**
		 * <code>UserLogin</code> gibt den aktuell eingeloggten Nutzer zur�ck
		 * Wenn der Nutzer nicht bei Google angemeldet ist, gibt die Methode
		 * <code> UserLogin().getUser() null zur�ck
		 * 
		 * @author Eric Schmidt 
		 */
		 // Check login status using login service. --> Nach Deploy: GWT.getHostPageBaseURL() 
		PinnwandAdministration.login("http://127.0.0.1:8888/SocialMediaPinnwand.html?gwt.codesvr=127.0.0.1:9997", new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {}

			public void onSuccess(LoginInfo result) {
		        loginInfo = result;
		        if(loginInfo.isLoggedIn()) {
		        	nutzerInDatenbank(result);
		        } else {
		        	loadLogin();
		        }
			}
	    });	
	}	

	/**
	 * Pr�ft anhand der Email-Adresse ob der angemeldete Nutzer bereits in der Datenbank ist
	 * 
	 * @author Eric Schmidt
	 */
	
	public void nutzerInDatenbank(final LoginInfo googleNutzer){
		PinnwandAdministration.getNutzerByEmail(googleNutzer.getEmailAddress(), new AsyncCallback<Nutzer>() {
			public void onFailure
			 (Throwable caught) {
			 // TODO: Do something with errors.
			 }
			 
			@Override
			public void onSuccess(Nutzer result) {
				if (result != null && result.getEmail() == googleNutzer.getEmailAddress()){
						aktuellerNutzer = result;
						loadSocialMediaPinnwand();
				}
		
				else{
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
		 * Fordert den Nutzer auf Vor-, Nachname und Nickname einzugeben,
		 * da diese Information nicht �ber die Google API bezogen werden kann
		 * bzw. ge�ndert werden soll
		 * 
		 * @author Eric Schmidt
		 */
		final LoginCustomDialog dialog = new LoginCustomDialog(googleNutzer.getNickname());
		DialogBox dlb = dialog;
		dlb.center();
		
		dlb.addCloseHandler(new CloseHandler<PopupPanel>(){
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
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
						* Update die SuggestBox mit neuen Nutzer
						*/
						fillSuggestBox();
						loadSocialMediaPinnwand();
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
		//Anpassen der rootGr��e anhand von Fenstergr��e
		int rootWidthSize = rp.getOffsetWidth();
		int rootHeightSize = rp.getOffsetHeight();
		
		VerticalPanel west = new VerticalPanel();
		HorizontalPanel east_up = new HorizontalPanel();
		HorizontalPanel east_down = new HorizontalPanel();
		
		
		/**
		 * Widgets der linken Seite 
		 */
		
		//�berschrift etwa: "Ferdis SocialMediaPinnwand"
		pinnwandName.setStyleName("pinnwandName");
		pinnwandName.setText("Social MediaPinnwand von "+aktuellerNutzer.getName());
		west.add(pinnwandName);
		
		/**
		 * Block f�r SuggestBox
		 * 
		 * @author Eric Schmidt
		 */
		SuggestBoxPinnwandSuche.setStyleName("SuggestBoxPinnwandSuche");

		
		//Einf�gen in Layout
		Button b = new Button("LogOut Temp");
		b.addClickListener(new ClickListener(){

			@Override
			public void onClick(Widget sender) {
				 loadLogout();
				
			}
			
		});
		west.add(b);
		west.add(SuggestBoxPinnwandSuche);
		SuggestBoxPinnwandSuche.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>(){
			public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event){
				Nutzer n = ((CustomSuggest)event.getSelectedItem()).getNutzer();
				abonniereNutzer(n);
			}
		});

	
		fillSuggestBox();
		
		final FlexTable FlexTableAbonniertePinnwaende = new FlexTable();
		FlexTableAbonniertePinnwaende.setStyleName("FlexTableAbonniertePinnwaende");
		west.add(FlexTableAbonniertePinnwaende);
		
		PinnwandAdministration.getAboByNutzer(aktuellerNutzer.getId(), new AsyncCallback<ArrayList<Abo>>() {
			public void onFailure
			(Throwable caught) {
				// TODO: DO something with errors.
			}
			@Override
			public void onSuccess(ArrayList<Abo> result) {
				for (int i=0; i<result.size(); i++){
					FlexTableAbonniertePinnwaende.setText(i, 0, result.get(i).getLieferant().getVorname()+" "+
							result.get(i).getLieferant().getName());
				}
			}
		});
		
		
		/**
		 * Widgets der rechten Seite 
		 * Rechts-Oben:
		 */
		 
		//Textfeld f�r Beitrag

		final TextArea TextAreaBeitragVerfassen = new TextArea();
		TextAreaBeitragVerfassen.setStyleName("TextAreaBeitragVerfassen");
		east_up.add(TextAreaBeitragVerfassen);
		
		//Button zum Absenden von Textbeitrag
		
		final Button ButtonBeitragSenden = new Button("Senden");
		ButtonBeitragSenden.setStyleName("beitragSenden");
		east_up.add(ButtonBeitragSenden);
		
		
		/**
		 * Beitrag posten
		 */
		
		ButtonBeitragSenden.addClickHandler(new ClickHandler() {
			/**
			 *  Beitrag wird der Pinnwand hinzugef�gt
			 *  Anschlie�end wird die Beitragsliste aktualisiert. 
			 */
			public void onClick(ClickEvent event) {
				Beitrag b = new Beitrag();					
				b.setInhalt(TextAreaBeitragVerfassen.getValue());
				b.setPinnwand(aktuellerNutzer.getPinnwand());
				
				PinnwandAdministration.saveBeitrag(b, new AsyncCallback<Void>(){	
				
					public void onFailure (Throwable caught) {
						// TODO: Do something with errors.
					}
					 
					@Override
					public void onSuccess(Void result) {
						//TODO: Liste aktualisieren
					}
				});
			}
		});
		
		
		
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
		 * Hinzuf�gen der Panels dem Rootpanel
		 */
		
		split.addWest(west, (rootWidthSize/2));
		split.addEast(vsplit, (rootWidthSize/2));
		vsplit.addNorth(east_up, (rootHeightSize/2));
		vsplit.addSouth(east_down, (rootHeightSize/2));
		rp.add(split);
	};

	
	/**
	 * Bef�llt die SuggestenBox mit allen Nutzern der Anwendung
	 * 
	 * @param nutzer S�mtliche Nutzer Objekte
	 * @author Eric Schmidt
	 */
	
	public void fillSuggestBox(){
		PinnwandAdministration.getAllNutzer(new AsyncCallback<ArrayList<Nutzer>>() {
			 public void onFailure
			 (Throwable caught) {
			 // TODO: Do something with errors.
			 }
			 
			@Override
			public void onSuccess(ArrayList<Nutzer> result) {
				ArrayList<CustomSuggest> suggestList = new ArrayList<CustomSuggest>();
				for (Nutzer n : result) {
					CustomSuggest suggest = new CustomSuggest(n);
					suggestList.add(suggest);
				}
				Collection<CustomSuggest> nutzerCollection = suggestList;
				oracle.setCollection(nutzerCollection);
				SuggestBoxPinnwandSuche.ensureDebugId("cwSuggestBox");
			}
		});
	}
	
	/**
	 * Schlie�t ein Abonnement zwischen dem Eingeloggtem Nutzer (aktuellerNutzer) 
	 * und dem �bergeben Nutzer. Dabei wird gepr�ft, ob es sich bei dem zu abonnierendem 
	 * Nutzer um sich selbst handelt oder ob die Abonnement Beziehung bereits eingegangen 
	 * wurde.
	 * 
	 * @param n Nutzer mit welchem ein Abonnement eingegangen werden soll
	 * @author Eric Schmidt
	 */
	public void abonniereNutzer(final Nutzer n){
		if (!n.equals(aktuellerNutzer)){
			PinnwandAdministration.getAboByNutzer(aktuellerNutzer.getId(), new AsyncCallback<ArrayList<Abo>>(){
	
				@Override
				public void onFailure(Throwable caught) {}
	
				@Override
				public void onSuccess(ArrayList<Abo> result) {
					boolean existiert = false;
					for (Abo a : result){
						if (a.getAbonnent().equals(aktuellerNutzer) && a.getLieferant().equals(n)){
							existiert = true;
						}
					}
					if (!existiert){
						DialogBox dlg = new AbonnementCustomDialog("Abonnieren", "Pinnwand von"
					    		+ n.getVorname() + " wirklich abonnieren?", aktuellerNutzer, n);
				        dlg.center();
				        loadSocialMediaPinnwand();
					}else{
						Window.alert("Sie sind bereits eine Abonnementbeziehung mit diesem Nutzer eingegangen");
					}
				}	
			});
		}else{
			Window.alert("Es kann keine Abonnementbeziehung mit sich selbst eingegangen werden");
		}
	}
	
	
	// Methode welche alle Beitr�ge ausgibt
	public void printOutAll(ArrayList<Beitrag> result) {
		// Hilfvariable um festzuhalten in welcher Row man sich befindet
		int aktuelleRow = 0;
		for(int i= 0; i < result.size(); i++){
			// Hinzuf�gen eines neuen Beitrags in der aktuellen Zeile, Dabei wird ein neues LayoutObjekt initialsiert und der FlexTable hinzugef�gt
			FlexTableBeitraege.setWidget(aktuelleRow, 0, new PinnwandBeitrag(result.get(i).getInhalt(), "von "+ result.get(i).getNutzer().getName() +","+result.get(i).getErstellungsZeitpunkt(),  + result.get(i).getLikeList().size()  + " Personen gefaellt das.", result.get(i),aktuellerNutzer ));
			// nachdem ein Beitrag der FlexTable hinzugef�gt wurde wird die aktuelle Zeile um 1 erh�ht.
			aktuelleRow += 1;
			// Nun werden alle Kommentare des zuvor hinzugef�gten Beitrages der FlexTable hinzugef�gt
			
			for(int a = 0; a < result.get(i).getKommentarListe().size(); a++) {
				FlexTableBeitraege.setWidget(aktuelleRow, 0, new BeitragKommentar(result.get(i).getKommentarListe().get(a), result.get(i).getKommentarListe().get(a).getInhalt(), " ,von " + result.get(i).getKommentarListe().get(a).getNutzer().getName(), String.valueOf(result.get(i).getKommentarListe().get(a).getErstellungsZeitpunkt())));
				aktuelleRow += 1;
			}
		}
	}
		

	/**
	 * Sollte der User nicht bei Google angemeldet sein, 
	 * verlinke auf login Seite von Google
	 * 
	 * @author Eric Schmidt
	 */
	  private void loadLogin() {	  
		  Window.Location.assign(loginInfo.getLoginUrl());
	  }
	  
	  /**
	   * Logge den aktuellen Nutzer aus und verlinke auf das Login Fenster
	   * 
	   * @author Eric Schmidt
	   */
	  private void loadLogout() {	  
		  Window.Location.assign(loginInfo.getLogoutUrl());
	  }
}
	
