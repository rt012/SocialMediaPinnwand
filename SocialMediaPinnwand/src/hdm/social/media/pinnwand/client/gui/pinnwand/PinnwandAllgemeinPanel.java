package hdm.social.media.pinnwand.client.gui.pinnwand;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import hdm.social.media.pinnwand.client.SocialMediaPinnwand;
import hdm.social.media.pinnwand.client.gui.CustomOracle;
import hdm.social.media.pinnwand.client.gui.CustomSuggest;
import hdm.social.media.pinnwand.client.gui.NutzerVerwaltung;
import hdm.social.media.pinnwand.server.db.DBConnection;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PinnwandAllgemeinPanel extends VerticalPanel{

	/**
	 * Widgets werden definiert
	 */
	//private PinnwandBeitrag panel_PinnwandBeitrag;
	private ShowBeitraege flexTableBeitraege = null;
	//Label zum anzeigen des Pinnwand Namen
	private final HTML pinnwandName = new HTML("");
	//Verweis auf CustomOracle -> Verwaltung der SuggestBox um Nutzerobjekte zu speichern
	private CustomOracle oracle = new CustomOracle();
	private final SuggestBox SuggestBoxPinnwandSuche = new SuggestBox(oracle);
	// Flextable f�r die Abos
	private Abolist flexTableAbonniertePinnwaende;
	//Button um den aktuellenNutzer zu l�schen
	private Button aktuellerNutzerloeschen = null;
	
	/**
	 * Hilfsvariablen
	 */
	private SocialMediaPinnwand socialMediaPinnwand = null;
	private Nutzer aktuellerNutzer = null;
	private NutzerVerwaltung nutzerVerwaltung = null; 
	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	
	public PinnwandAllgemeinPanel(final SocialMediaPinnwand socialMediaPinnwand, final NutzerVerwaltung nutzerVerwaltung, final ShowBeitraege flexTableBeitraege){
		this.socialMediaPinnwand = socialMediaPinnwand;
		this.aktuellerNutzer = socialMediaPinnwand.getAktuellerNutzer();
		this.flexTableBeitraege = flexTableBeitraege;
		this.nutzerVerwaltung = nutzerVerwaltung;
		
		setStyleName("layout_west");
		
		/**
		 * Ueberschrift etwa: "SocialMediaPinnwand Ferdinand Gr�ner"
		 */
		pinnwandName.setStyleName("pinnwandName");
		pinnwandName.setHTML("SocialMediaPinnwand von </br>"+ aktuellerNutzer.getVorname()+' '+ aktuellerNutzer.getName());
		add(pinnwandName);
		
		/**
		 * Eigener Pinnwand Button
		 */
		Button eigenePinnwand = new Button("");
		eigenePinnwand.addStyleName("buttonEigenePinnwand");
		add(eigenePinnwand);
		eigenePinnwand.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				flexTableBeitraege.refresh(socialMediaPinnwand.getAktuellerNutzer());
			}
		});
		
				
		
		/**
		 * Logout Button
		 */
		Button logoutButton = new Button("");
		logoutButton.setStyleName("buttonLogout");
		logoutButton.addClickHandler(new ClickHandler(){
			
			@Override
			public void onClick(ClickEvent event) {
				nutzerVerwaltung.loadLogout();
			}

		});
		add(logoutButton);	
		
		/**
		 * Button um aktuellen Nutzer zu l�schen
		 */
		aktuellerNutzerloeschen = new Button("");
		aktuellerNutzerloeschen.setStyleName("deleteButton");
		aktuellerNutzerloeschen.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				nutzerVerwaltung.deleteNutzer(socialMediaPinnwand.getAktuellerNutzer());
			}	
		});
		add(aktuellerNutzerloeschen);	
		
				
		Label pinnwandSuche = new Label("Pinnwand suchen:");
		pinnwandSuche.setStyleName("pinnwandSuche");
		add(pinnwandSuche);
				

		/**
		 * Block fuer SuggestBox
		 * 
		 * @author Eric Schmidt
		 */
		SuggestBoxPinnwandSuche.setStyleName("SuggestBoxPinnwandSuche");
		add(SuggestBoxPinnwandSuche);
		SuggestBoxPinnwandSuche.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>(){
			public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event){
				Nutzer n = ((CustomSuggest)event.getSelectedItem()).getNutzer();
				abonniereNutzer(n);
			}
		});

		fillSuggestBox();



		/**
		 * Anzeigen einer Liste (FlexTable) mit den bereits abonnierten Pinnwaenden
		 * ScrollPanel sorgt daf�r, dass der FlexTable scrollbar wird.
		 */
		ScrollPanel scrollPanel = new ScrollPanel();

	
		
		flexTableAbonniertePinnwaende=new Abolist(aktuellerNutzer, socialMediaPinnwand,flexTableBeitraege);
		flexTableAbonniertePinnwaende.setStyleName("FlexTableAbonniertePinnwaende");
		flexTableAbonniertePinnwaende.setWidth("100%");
		
		scrollPanel.setSize("300", "200");    
		scrollPanel.add(flexTableAbonniertePinnwaende);
		add(scrollPanel);
		
	}
	/**
	 * Bef�llt die SuggestBox mit allen Nutzern der Anwendung
	 * 
	 * @param nutzer S�mtliche Nutzer Objekte
	 * @author Eric Schmidt
	 */

	private void fillSuggestBox(){
		PinnwandAdministration.getAllNutzer(new AsyncCallback<ArrayList<Nutzer>>() {
			 public void onFailure (Throwable caught) {
			 	Window.alert("Fehler bei laden der Nutzer in SuggestBox");
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
	private void abonniereNutzer(final Nutzer n){
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
						DialogBox dlg = new AbonnementCustomDialog(socialMediaPinnwand, "Abonnieren", "Pinnwand von"
					    		+ n.getVorname() + " wirklich abonnieren?", aktuellerNutzer, n,
					    		flexTableAbonniertePinnwaende, flexTableBeitraege);
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

	public void refreshFlexTableAbonniertePinnwaende(){
		flexTableAbonniertePinnwaende.refresh(socialMediaPinnwand, flexTableBeitraege);
	}
	
}
