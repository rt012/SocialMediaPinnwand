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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SocialMediaPinnwand implements EntryPoint {
	private NutzerVerwaltung nutzerVerwaltung = null;

	//Aktiver/Aktueller Nutzer
	private Nutzer aktuellerNutzer = null;
	
	/**
	 * Custom Widgets der SocialMediaPinnwand
	 */
	private ShowBeitraege showBeitraege;
	private PinnwandAllgemeinPanel pinnwandAllgemeinPanel;
	private PinnwandBeitragPanel pinnwandBeitragPanel;
	
	public Nutzer getAktuellerNutzer(){
		return aktuellerNutzer;
	}
	
	public void setAktuellerNutzer(Nutzer aktuellerNutzer){
		this.aktuellerNutzer=aktuellerNutzer;
	}

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
		
		//ScrollPanel sorgt daf�r, dass der FlexTable scrollbar wird.
		ScrollPanel scrollPanel = new ScrollPanel();
		showBeitraege.setWidth("100%");
		scrollPanel.setSize("300", "200");    
		scrollPanel.add(showBeitraege);
		
		
		SplitLayoutPanel split = new SplitLayoutPanel();
		split.setStyleName("rootSplitPanel");

		SplitLayoutPanel vsplit = new SplitLayoutPanel();	
		RootLayoutPanel rp = RootLayoutPanel.get();
		
		int rootHeightSize = rp.getOffsetHeight();
		split.addWest(pinnwandAllgemeinPanel, 300);
		split.addEast(vsplit, 700);
		vsplit.addNorth(pinnwandBeitragPanel, rootHeightSize*3/10);
		vsplit.addSouth(scrollPanel, rootHeightSize*7/10);
		rp.add(split);
	}
	
	public PinnwandAllgemeinPanel getPinnwandAllgemeinPanel(){
		return this.pinnwandAllgemeinPanel;
	}
	
	public ShowBeitraege getShowBeitraege(){
		return this.showBeitraege;
	}
}

