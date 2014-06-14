package hdm.social.media.pinnwand.client;

import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;
import hdm.social.media.pinnwand.client.gui.LoginInfo;
import hdm.social.media.pinnwand.client.gui.NutzerVerwaltung;
import hdm.social.media.pinnwand.client.gui.pinnwand.PinnwandAllgemeinPanel;
import hdm.social.media.pinnwand.client.gui.pinnwand.PinnwandBeitragPanel;
import hdm.social.media.pinnwand.client.gui.pinnwand.ShowBeitraege;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

/**
 * Diese Klasse stellt den Entry point für die SocialMediaPinnwand dar. 
 * 
 */
public class SocialMediaPinnwand implements EntryPoint {
	private NutzerVerwaltung nutzerVerwaltung = null;

	/** 
	 * Aktiver/Aktueller Nutzer
	 */
	private Nutzer aktuellerNutzer = null;
	
	/**
	 * Custom Widgets der SocialMediaPinnwand
	 */
	private ShowBeitraege showBeitraege;
	private PinnwandAllgemeinPanel pinnwandAllgemeinPanel;
	private PinnwandBeitragPanel pinnwandBeitragPanel;
	
	/**
	 * Methode zum Auslesen des aktuellen Nutzers
	 * @return
	 */
	public Nutzer getAktuellerNutzer(){
		return aktuellerNutzer;
	}
	/**
	 * Methode zum Setzen des aktuellen Nutzers
	 * @param aktuellerNutzer
	 */
	
	public void setAktuellerNutzer(Nutzer aktuellerNutzer){
		this.aktuellerNutzer=aktuellerNutzer;
	}

	/**
	 *  Stellt eine Verbindung über einen Proxy her
	 */
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);

	/**
	 * Methode um den Entry Point zu laden
	 */
	public void onModuleLoad() {		
		/**
		 * <code>UserLogin</code> gibt den aktuell eingeloggten Nutzer zurÃ¼ck
		 * Wenn der Nutzer nicht bei Google angemeldet ist, gibt die Methode
		 * <code> UserLogin().getUser() null zurÃ¼ck
		 * 
		 * @author Eric Schmidt 
		 */
		/**
		 * Überprüft den Login Status --> Nach Deploy: GWT.getHostPageBaseURL() 
		 */

		PinnwandAdministration.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			/**
			 * Bei einer fehlgeschlagenen Verbindung wird ein Fehler ausgegeben
			 */
			public void onFailure(Throwable error) {}
		/**
		 * Bei einer erfolgreichen Verbindung kann der Nutzer eingeloggt werden. 
		 * 
		 */
			public void onSuccess(LoginInfo result) {
		        LoginInfo loginInfo = result;
		        nutzerVerwaltung = new NutzerVerwaltung(loginInfo, SocialMediaPinnwand.this);
		        if(loginInfo.isLoggedIn()) {
		        	nutzerVerwaltung.nutzerInDatenbank(loginInfo);
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
	public void loadSocialMediaPinnwand(){
		
		showBeitraege = new ShowBeitraege(aktuellerNutzer, SocialMediaPinnwand.this);
		pinnwandBeitragPanel = new PinnwandBeitragPanel(SocialMediaPinnwand.this);
		pinnwandAllgemeinPanel= new PinnwandAllgemeinPanel(SocialMediaPinnwand.this, nutzerVerwaltung, showBeitraege);
		
		pinnwandAllgemeinPanel.setStyleName("pinnwandAllgemein");
		pinnwandBeitragPanel.setStyleName("beitragPanel");
		/**
		 * ScrollPanel sorgt dafür, dass der FlexTable scrollbar wird.
		 */
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setStyleName("scrollPanel");
		showBeitraege.setWidth("100%");
		scrollPanel.setSize("300", "200");    
		scrollPanel.add(showBeitraege);
		
		
		SplitLayoutPanel split = new SplitLayoutPanel();
		split.setStyleName("rootSplitPanel");

		SplitLayoutPanel vsplit = new SplitLayoutPanel();	
		RootLayoutPanel rp = RootLayoutPanel.get();
		
		int rootHeightSize = rp.getOffsetHeight();
		split.addWest(pinnwandAllgemeinPanel, 300);
		split.addEast(vsplit,700);
		vsplit.addNorth(pinnwandBeitragPanel, 206);
		vsplit.addSouth(scrollPanel, rootHeightSize);
		rp.add(split);
	}
	/**
	 * Methode zum Auslesen von PinnwandAllgemeinPanel-Objekten
	 */
	public PinnwandAllgemeinPanel getPinnwandAllgemeinPanel(){
		return this.pinnwandAllgemeinPanel;
	}
	/**
	 * Methode zum Auslesen von ShowBeitraege-Objekten
	 */
	
	public ShowBeitraege getShowBeitraege(){
		return this.showBeitraege;
	}
}

