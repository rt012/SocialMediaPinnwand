package hdm.social.media.pinnwand.client;

<<<<<<< HEAD

=======

>>>>>>> refs/remotes/origin/Eric
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

<<<<<<< HEAD
import hdm.social.media.pinnwand.server.PinnwandAdministrationImpl;
import hdm.social.media.pinnwand.shared.FieldVerifier;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
=======
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.server.PinnwandAdministrationImpl;
import hdm.social.media.pinnwand.shared.FieldVerifier;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;
>>>>>>> refs/remotes/origin/Eric
import hdm.social.media.pinnwand.client.gui.AbonnementCustomDialog;
import hdm.social.media.pinnwand.client.gui.CustomOracle;
import hdm.social.media.pinnwand.client.gui.CustomSuggest;
import hdm.social.media.pinnwand.client.gui.LoginCustomDialog;
<<<<<<< HEAD
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;
=======
>>>>>>> refs/remotes/origin/Eric

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
<<<<<<< HEAD
	
	//	private PinnwandBeitrag panel_PinnwandBeitrag;
	
=======

	//	private PinnwandBeitrag panel_PinnwandBeitrag;

	private NutzerVerwaltung nutzerVerwaltung = null;
>>>>>>> refs/remotes/origin/Eric
	// Widgets zur Realisierung des Logins f�r Anzeige
	private LoginInfo loginInfo = null;

	//Aktiver/Aktueller Nutzer
	private Nutzer aktuellerNutzer = null;
<<<<<<< HEAD
	
	public Nutzer getAktuellerNutzer(){
		return aktuellerNutzer;
	}
=======
>>>>>>> refs/remotes/origin/Eric

	//Verweis auf CustomOracle -> Verwaltungder Suggestionbox um Nutzerobjekte zu speichern

	private CustomOracle oracle = new CustomOracle();
	private final SuggestBox SuggestBoxPinnwandSuche = new SuggestBox(oracle);

	//private PinnwandBeitrag panel_PinnwandBeitrag;
<<<<<<< HEAD
	ShowBeitraege FlexTableBeitraege;
	final Label pinnwandName = new Label("");

	// Flextable für die Abos
	Abolist FlexTableAbonniertePinnwaende;
	
=======
	final FlexTable FlexTableBeitraege = new FlexTable();
	final Label pinnwandName = new Label("");

	// Flextable für die Abos
	final FlexTable FlexTableAbonniertePinnwaende = new FlexTable();

>>>>>>> refs/remotes/origin/Eric
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
<<<<<<< HEAD
		 * <code>UserLogin</code> gibt den aktuell eingeloggten Nutzer zur�ck
=======
		 * <code>UserLogin</code> gibt den aktuell eingeloggten Nutzer zurück
>>>>>>> refs/remotes/origin/Eric
		 * Wenn der Nutzer nicht bei Google angemeldet ist, gibt die Methode
<<<<<<< HEAD
		 * <code> UserLogin().getUser() null zur�ck
=======
		 * <code> UserLogin().getUser() null zurück
>>>>>>> refs/remotes/origin/Eric
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
<<<<<<< HEAD
		        	nutzerInDatenbank(result);
=======
		        	nutzerVerwaltung.nutzerInDatenbank(loginInfo);
		        	loadSocialMediaPinnwand();
>>>>>>> refs/remotes/origin/Eric
		        } else {
		        	nutzerVerwaltung.loadLogin();
		        }
			}
	    });	
	}	

<<<<<<< HEAD
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
	
=======
>>>>>>> refs/remotes/origin/Eric
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
<<<<<<< HEAD
				
=======
		int rootWidthSize = rp.getOffsetWidth();
		int rootHeightSize = rp.getOffsetHeight();

>>>>>>> refs/remotes/origin/Eric
		VerticalPanel west = new VerticalPanel();
		west.setStyleName("layout_west");
		HorizontalPanel east_up = new HorizontalPanel();
		east_up.setStyleName("layout_east_up");
		HorizontalPanel east_down = new HorizontalPanel();
<<<<<<< HEAD
		east_down.setStyleName("layout_east_down");
		
		
=======


>>>>>>> refs/remotes/origin/Eric
		/**
		 * Widgets der linken Seite 
		 */
<<<<<<< HEAD
		
=======

>>>>>>> refs/remotes/origin/Eric
		//�berschrift etwa: "Ferdis SocialMediaPinnwand"
		pinnwandName.setStyleName("pinnwandName");
		pinnwandName.setText("Social MediaPinnwand von "+aktuellerNutzer.getName());
		west.add(pinnwandName);
<<<<<<< HEAD
		
		//Eigener Pinnwand Button
		Button eigenePinnwand = new Button("Eigene Pinnwand");
		eigenePinnwand.addStyleName("buttonEigenePinnwand");
		west.add(eigenePinnwand);
		eigenePinnwand.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				FlexTableBeitraege.refresh(aktuellerNutzer);
			}
		});
		
		
=======

		//Eigener Pinnwand Button
		Button eigenePinnwand = new Button("Eigene Pinnwand");
		eigenePinnwand.addStyleName("buttonEigenePinnwand");
		west.add(eigenePinnwand);
		eigenePinnwand.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				refresh(aktuellerNutzer);
			}
		});


>>>>>>> refs/remotes/origin/Eric
		//Logout Button
		Button b = new Button("LogOut Temp");
		b.setStyleName("buttonLogout");
		b.addClickListener(new ClickListener(){

			@Override
			public void onClick(Widget sender) {
				 loadLogout();

			}

		});
		west.add(b);
<<<<<<< HEAD
		
		
		Label pinnwandSuche = new Label("Pinnwand suchen:");
		pinnwandSuche.setStyleName("pinnwandSuche");
		west.add(pinnwandSuche);
		
=======




>>>>>>> refs/remotes/origin/Eric
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

<<<<<<< HEAD
	
		fillSuggestBox();
		
		
=======

		fillSuggestBox();


>>>>>>> refs/remotes/origin/Eric
		/**
		 * Anzeigen einer Liste mit den bereits abonnierten Pinnwänden
		 */
<<<<<<< HEAD

		FlexTableAbonniertePinnwaende=new Abolist(aktuellerNutzer, this);
		FlexTableAbonniertePinnwaende.setStyleName("FlexTableAbonniertePinnwaende");	
=======
		FlexTableAbonniertePinnwaende.setStyleName("FlexTableAbonniertePinnwaende");
>>>>>>> refs/remotes/origin/Eric
		west.add(FlexTableAbonniertePinnwaende);
<<<<<<< HEAD
		
		

		
=======

		fillAboList();

>>>>>>> refs/remotes/origin/Eric
		/**
		 * Widgets der rechten Seite 
		 * Rechts-Oben:
		 */
<<<<<<< HEAD
		 
		//Textfeld f�r Beitrag
		
		Label beitragSchreiben = new Label("Beitrag schreiben:");
		beitragSchreiben.setStyleName("beitragSchreiben");
		east_up.add(beitragSchreiben);
=======

		//Textfeld f�r Beitrag
>>>>>>> refs/remotes/origin/Eric

		final TextArea TextAreaBeitragVerfassen = new TextArea();
		TextAreaBeitragVerfassen.setStyleName("TextAreaBeitragVerfassen");
		east_up.add(TextAreaBeitragVerfassen);
<<<<<<< HEAD
		
		//Button zum Absenden von Textbeitrag
		
=======

		//Button zum Absenden von Textbeitrag

>>>>>>> refs/remotes/origin/Eric
		final Button ButtonBeitragSenden = new Button("Senden");
		ButtonBeitragSenden.setStyleName("beitragSenden");
		east_up.add(ButtonBeitragSenden);
<<<<<<< HEAD
		
		
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
							}
						});
					}
				});				
			}
		});
		
		
		
=======


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



>>>>>>> refs/remotes/origin/Eric
		/**
		 * Rechts-unten:
		 */
<<<<<<< HEAD
		
=======

>>>>>>> refs/remotes/origin/Eric
		final Label LabelPinnwandInformation = new Label();
		LabelPinnwandInformation.setStyleName("LabelPinnwandInformation");
		east_down.add(LabelPinnwandInformation);
<<<<<<< HEAD
		
		FlexTableBeitraege = new ShowBeitraege(aktuellerNutzer, this); 
=======

>>>>>>> refs/remotes/origin/Eric
		FlexTableBeitraege.setStyleName("FlexTableBeitraege");
		east_down.add(FlexTableBeitraege);
<<<<<<< HEAD
=======
		printOutBeitragJeNutzer(aktuellerNutzer);
>>>>>>> refs/remotes/origin/Eric

		/**
		 * Hinzuf�gen der Panels dem Rootpanel
		 */
<<<<<<< HEAD
		
		int rootHeightSize = rp.getOffsetHeight();
		split.addWest(west, 300);
		split.addEast(vsplit, 700);
		vsplit.addNorth(east_up, rootHeightSize*3/10);
		vsplit.addSouth(east_down, rootHeightSize*7/10);
		rp.add(split);
	};

	
=======

		split.addWest(west, (rootWidthSize/2));
		split.addEast(vsplit, (rootWidthSize/2));
		vsplit.addNorth(east_up, (rootHeightSize/2));
		vsplit.addSouth(east_down, (rootHeightSize/2));
		rp.add(split);
	};


>>>>>>> refs/remotes/origin/Eric
	/**
	 * Bef�llt die SuggestenBox mit allen Nutzern der Anwendung
	 * 
	 * @param nutzer S�mtliche Nutzer Objekte
	 * @author Eric Schmidt
	 */
<<<<<<< HEAD
	
=======

>>>>>>> refs/remotes/origin/Eric
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
<<<<<<< HEAD
	
	
	// Methode welche alle Beitr�ge ausgibt
	
	
=======


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

>>>>>>> refs/remotes/origin/Eric

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
<<<<<<< HEAD
	  
	  
	  
}
	
=======

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
	  public void setAktuellerNutzer(Nutzer aktuellerNutzer){
		  this.aktuellerNutzer = aktuellerNutzer;
	  }
	  

	  public void refresh(Nutzer n){
		  FlexTableBeitraege.removeAllRows();
		  printOutBeitragJeNutzer(aktuellerNutzer);
		  fillAboList(); 
	  }

}
>>>>>>> refs/remotes/origin/Eric
