package hdm.social.media.pinnwand.client;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.server.PinnwandAdministrationImpl;
import hdm.social.media.pinnwand.shared.FieldVerifier;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;
import hdm.social.media.pinnwand.client.gui.AbonnementCustomDialog;
import hdm.social.media.pinnwand.client.gui.CustomOracle;
import hdm.social.media.pinnwand.client.gui.CustomSuggest;
import hdm.social.media.pinnwand.client.gui.LoginCustomDialog;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.CellList;
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

	private NutzerVerwaltung nutzerVerwaltung = null;
	// Widgets zur Realisierung des Logins f�r Anzeige
	private LoginInfo loginInfo = null;

	//Aktiver/Aktueller Nutzer
	private Nutzer aktuellerNutzer = null;

	//Verweis auf CustomOracle -> Verwaltungder Suggestionbox um Nutzerobjekte zu speichern

	private CustomOracle oracle = new CustomOracle();
	private final SuggestBox SuggestBoxPinnwandSuche = new SuggestBox(oracle);

	//private PinnwandBeitrag panel_PinnwandBeitrag;
	final FlexTable FlexTableBeitraege = new FlexTable();
	final Label pinnwandName = new Label("");

	// Flextable für die Abos
	final FlexTable FlexTableAbonniertePinnwaende = new FlexTable();

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
		 // Check login status using login service. --> Nach Deploy: GWT.getHostPageBaseURL() 
		PinnwandAdministration.login("http://127.0.0.1:8888/SocialMediaPinnwand.html?gwt.codesvr=127.0.0.1:9997", new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {}

			public void onSuccess(LoginInfo result) {
		        LoginInfo loginInfo = result;
		        nutzerVerwaltung = new NutzerVerwaltung(loginInfo, SocialMediaPinnwand.this);
		        if(loginInfo.isLoggedIn()) {
		        	nutzerVerwaltung.nutzerInDatenbank(loginInfo);
		        	loadSocialMediaPinnwand();
		        } else {
		        	nutzerVerwaltung.loadLogin();
		        }
			}
	    });	
	}	

	/**
	 * Lade die Widget Elemente der GWT Applikation
	 * 
	 * @author Eric Schmidt
	 */
	@SuppressWarnings("deprecation")
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

		//Eigener Pinnwand Button
		Button eigenePinnwand = new Button("Eigene Pinnwand");
		eigenePinnwand.addStyleName("buttonEigenePinnwand");
		west.add(eigenePinnwand);
		eigenePinnwand.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				refresh(aktuellerNutzer);
			}
		});


		//Logout Button
		Button b = new Button("LogOut Temp");
		b.addClickListener(new ClickListener(){

			@Override
			public void onClick(Widget sender) {
				 loadLogout();

			}

		});
		west.add(b);




		/**
		 * Block f�r SuggestBox
		 * 
		 * @author Eric Schmidt
		 */
		SuggestBoxPinnwandSuche.setStyleName("SuggestBoxPinnwandSuche");
		west.add(SuggestBoxPinnwandSuche);
		SuggestBoxPinnwandSuche.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>(){
			public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event){
				Nutzer n = ((CustomSuggest)event.getSelectedItem()).getNutzer();
				abonniereNutzer(n);
			}
		});


		fillSuggestBox();


		/**
		 * Anzeigen einer Liste mit den bereits abonnierten Pinnwänden
		 */
		FlexTableAbonniertePinnwaende.setStyleName("FlexTableAbonniertePinnwaende");
		west.add(FlexTableAbonniertePinnwaende);

		fillAboList();

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

				PinnwandAdministration.getPinnwandByNutzer(aktuellerNutzer, new AsyncCallback<Pinnwand>(){
					public void onFailure(Throwable caught){}
					public void onSuccess(Pinnwand result){
						Beitrag b = new Beitrag();					
						b.setInhalt(TextAreaBeitragVerfassen.getValue());
						b.setPinnwand(result);
						PinnwandAdministration.saveBeitrag(b, new AsyncCallback<Void>(){	

							public void onFailure (Throwable caught) {
								// TODO: Do something with errors.
							}

							@Override
							public void onSuccess(Void result) {
								refresh(aktuellerNutzer);
							}
						});
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

		FlexTableBeitraege.setStyleName("FlexTableBeitraege");
		east_down.add(FlexTableBeitraege);
		printOutBeitragJeNutzer(aktuellerNutzer);

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
	 * Schlie�t ein Abonnement zwischen dem Eingeloggtem Nutzer (aktuellerNutzer) i
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
				        refresh(aktuellerNutzer);
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
	public void printOutBeitragJeNutzer(Nutzer n) {

		if(n!=aktuellerNutzer){
			PinnwandAdministration.getAllBeitragByNutzer(n, new AsyncCallback<ArrayList<Beitrag>>(){
				public void onFailure(Throwable caught) {}

				public void onSuccess(ArrayList<Beitrag> result){
					// Hilfvariable um festzuhalten in welcher Row man sich befindet
					int aktuelleRow = 0;
					if(result!= null){
						for(int i= 0; i < result.size(); i++){
							// Hinzuf�gen eines neuen Beitrags in der aktuellen Zeile, Dabei wird ein neues LayoutObjekt initialsiert und der FlexTable hinzugef�gt
							FlexTableBeitraege.setWidget(
									aktuelleRow,
									0,
									new PinnwandBeitrag(result.get(i).getInhalt(),
														"von "+ result.get(i).getPinnwand().getNutzer().getName() +","+result.get(i).getErstellungsZeitpunkt(),
														+ result.get(i).getLikeList().size()+" Personen gefaellt das.",
														result.get(i),aktuellerNutzer ));
							// nachdem ein Beitrag der FlexTable hinzugef�gt wurde wird die aktuelle Zeile um 1 erh�ht.
							aktuelleRow += 1;
							// Nun werden alle Kommentare des zuvor hinzugef�gten Beitrages der FlexTable hinzugef�gt

							for(int a = 0; a < result.get(i).getKommentarListe().size(); a++) {
								FlexTableBeitraege.setWidget(aktuelleRow, 0, new BeitragKommentar(result.get(i).getKommentarListe().get(a), result.get(i).getKommentarListe().get(a).getInhalt(), " ,von " + result.get(i).getKommentarListe().get(a).getNutzer().getName(), String.valueOf(result.get(i).getKommentarListe().get(a).getErstellungsZeitpunkt())));
								aktuelleRow += 1;
							}
						}
					}
				}
			});
		}

		else{
			PinnwandAdministration.getAllBeitragByAktuellerNutzer(n, new AsyncCallback<ArrayList<Beitrag>>(){
				public void onFailure(Throwable caught) {}

				public void onSuccess(ArrayList<Beitrag> result){
					// Hilfvariable um festzuhalten in welcher Row man sich befindet
					int aktuelleRow = 0;
					if(result != null){
						for(int i= 0; i < result.size(); i++){
							// Hinzuf�gen eines neuen Beitrags in der aktuellen Zeile, Dabei wird ein neues LayoutObjekt initialsiert und der FlexTable hinzugef�gt

							//Da Likelist evtl null ist muss hier die Abfrage ausgelagert werden
							int likelistsize=0;
							if (result.get(i).getLikeList() != null){
								likelistsize = result.get(i).getLikeList().size();
							}

							FlexTableBeitraege.setWidget(
									aktuelleRow,
									0,
									new PinnwandBeitrag(result.get(i).getInhalt(),
														"von "+ result.get(i).getPinnwand().getNutzer().getName() +","+result.get(i).getErstellungsZeitpunkt(),
														+ likelistsize +" Personen gefaellt das.",
														result.get(i),aktuellerNutzer ));
							// nachdem ein Beitrag der FlexTable hinzugef�gt wurde wird die aktuelle Zeile um 1 erh�ht.
							aktuelleRow += 1;
							// Nun werden alle Kommentare des zuvor hinzugef�gten Beitrages der FlexTable hinzugef�gt
							if(result.get(i).getKommentarListe() != null){
								for(int a = 0; a < result.get(i).getKommentarListe().size(); a++) {
									FlexTableBeitraege.setWidget(aktuelleRow, 0, new BeitragKommentar(result.get(i).getKommentarListe().get(a), result.get(i).getKommentarListe().get(a).getInhalt(), " ,von " + result.get(i).getKommentarListe().get(a).getNutzer().getName(), String.valueOf(result.get(i).getKommentarListe().get(a).getErstellungsZeitpunkt())));
									aktuelleRow += 1;
								}
							}
						}
					}
				}
			});
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

	  public void fillAboList(){
	  PinnwandAdministration.getAboByNutzer(aktuellerNutzer.getId(), new AsyncCallback<ArrayList<Abo>>() {
			public void onFailure
			(Throwable caught) {
				// TODO: DO something with errors.
			}
			@Override
			public void onSuccess(final ArrayList<Abo> result) {
				for (int i=0; i<result.size(); i++){

					final int x=i;
					Button buttonZeigePinnwand = new Button("Zur Pinnwand");
					buttonZeigePinnwand.setStyleName("buttonZeigePinnwand");
					buttonZeigePinnwand.addClickHandler(new ClickHandler(){
						public void onClick(ClickEvent event) {
							FlexTableBeitraege.removeAllRows();
							printOutBeitragJeNutzer(result.get(x).getLieferant());
						}
					});

					FlexTableAbonniertePinnwaende.setWidget(i,0, new Abozeile(result.get(i)));
					FlexTableAbonniertePinnwaende.setWidget(i,1, buttonZeigePinnwand);




			}}

		});
	  }

	  public void refresh(Nutzer n){
		  FlexTableBeitraege.removeAllRows();
		  printOutBeitragJeNutzer(aktuellerNutzer);
		  fillAboList(); 
	  }

}