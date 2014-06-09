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

import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;

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
	
	public Nutzer getAktuellerNutzer(){
		return aktuellerNutzer;
	}
	
	public void setAktuellerNutzer(Nutzer aktuellerNutzer){
		this.aktuellerNutzer=aktuellerNutzer;
	}


	//Verweis auf CustomOracle -> Verwaltungder Suggestionbox um Nutzerobjekte zu speichern

	private CustomOracle oracle = new CustomOracle();
	private final SuggestBox SuggestBoxPinnwandSuche = new SuggestBox(oracle);

	//private PinnwandBeitrag panel_PinnwandBeitrag;
	ShowBeitraege FlexTableBeitraege;
	final Label pinnwandName = new Label("");

	// Flextable für die Abos
	Abolist FlexTableAbonniertePinnwaende;
	

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

		VerticalPanel west = new VerticalPanel();
		west.setStyleName("layout_west");
		HorizontalPanel east_up = new HorizontalPanel();
		east_up.setStyleName("layout_east_up");
		HorizontalPanel east_down = new HorizontalPanel();
		east_down.setStyleName("layout_east_down");


		/**
		 * Widgets der linken Seite 
		 */

		//�berschrift etwa: "Ferdis SocialMediaPinnwand"
		pinnwandName.setStyleName("pinnwandName");
		pinnwandName.setText("Social MediaPinnwand von "+aktuellerNutzer.getVorname()+' '+aktuellerNutzer.getName());
		west.add(pinnwandName);
		
		//Eigener Pinnwand Button
		Button eigenePinnwand = new Button("Eigene Pinnwand");
		eigenePinnwand.addStyleName("buttonEigenePinnwand");
		west.add(eigenePinnwand);
		eigenePinnwand.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				FlexTableBeitraege.refresh(aktuellerNutzer);
			}
		});
		
		
		//Logout Button
		Button b = new Button("LogOut Temp");
		b.setStyleName("buttonLogout");
		b.addClickListener(new ClickListener(){

			@Override
			public void onClick(Widget sender) {
				 nutzerVerwaltung.loadLogout();

			}

		});
		west.add(b);		
		
		Label pinnwandSuche = new Label("Pinnwand suchen:");
		pinnwandSuche.setStyleName("pinnwandSuche");
		west.add(pinnwandSuche);
		

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
		
		FlexTableAbonniertePinnwaende=new Abolist(aktuellerNutzer, this);
		FlexTableAbonniertePinnwaende.setStyleName("FlexTableAbonniertePinnwaende");	

		west.add(FlexTableAbonniertePinnwaende);



		/**
		 * Widgets der rechten Seite 
		 * Rechts-Oben:
		 */

		 
		//Textfeld f�r Beitrag
		
		Label beitragSchreiben = new Label("Beitrag schreiben:");
		beitragSchreiben.setStyleName("beitragSchreiben");
		east_up.add(beitragSchreiben);

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
		
		FlexTableBeitraege = new ShowBeitraege(aktuellerNutzer, this); 


		FlexTableBeitraege.setStyleName("FlexTableBeitraege");
		east_down.add(FlexTableBeitraege);


		/**
		 * Hinzuf�gen der Panels dem Rootpanel
		 */

		
		int rootHeightSize = rp.getOffsetHeight();
		split.addWest(west, 300);
		split.addEast(vsplit, 700);
		vsplit.addNorth(east_up, rootHeightSize*3/10);
		vsplit.addSouth(east_down, rootHeightSize*7/10);
		rp.add(split);
};



	/**
	 * Bef�llt die SuggestBox mit allen Nutzern der Anwendung
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
					}else{
						Window.alert("Sie sind bereits eine Abonnementbeziehung mit diesem Nutzer eingegangen");
					}
				}	
			});
		}else{
			Window.alert("Es kann keine Abonnementbeziehung mit sich selbst eingegangen werden");
		}
	}


}

