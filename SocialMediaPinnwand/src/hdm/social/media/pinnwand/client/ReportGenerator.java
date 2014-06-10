package hdm.social.media.pinnwand.client;

import hdm.social.media.pinnwand.client.gui.LoginInfo;
import hdm.social.media.pinnwand.client.gui.NutzerVerwaltung;
import hdm.social.media.pinnwand.client.gui.report.ReportOptionen;
import hdm.social.media.pinnwand.client.gui.report.ReportRootPanel;
import hdm.social.media.pinnwand.shared.ReportGeneratorAdministration;
import hdm.social.media.pinnwand.shared.ReportGeneratorAdministrationAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

/*
 * EntryPoint Klasse f�r den Report Generator 
 * 
 * @author Blessing
 */

public class ReportGenerator implements EntryPoint {
	private SplitLayoutPanel split = new SplitLayoutPanel();
	private RootLayoutPanel rp = RootLayoutPanel.get();
	private ReportRootPanel reportRootPanel = null;
	private NutzerVerwaltung nutzerVerwaltung = null;
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final ReportGeneratorAdministrationAsync reportGenerator = GWT.create(ReportGeneratorAdministration.class);
	
	@Override
	public void onModuleLoad() {
		 /**
		  * <code>UserLogin</code> gibt den aktuell eingeloggten Nutzer zurück
		  * Wenn der Nutzer nicht bei Google angemeldet ist, gibt die Methode
		  * <code> UserLogin().getUser() null zurück
		  * 
		  * @author Eric Schmidt 
		 */
		// Check login status using login service. --> Nach Deploy: GWT.getHostPageBaseURL() 
		reportGenerator.login("http://127.0.0.1:8888/ReportGenerator.html?gwt.codesvr=127.0.0.1:9997", new AsyncCallback<LoginInfo>() {
				public void onFailure(Throwable error) {}

				public void onSuccess(LoginInfo result) {
			        LoginInfo loginInfo = result;
			        nutzerVerwaltung = new NutzerVerwaltung(loginInfo, ReportGenerator.this);
			        if(loginInfo.isLoggedIn()) {
			        	nutzerVerwaltung.nutzerInDatenbank(loginInfo);
			        } else {
			        	nutzerVerwaltung.loadLogin();
			        }
				}
		    });	
	} 
	 
	public void loadReportGenerator(){
		reportRootPanel = new ReportRootPanel();
		
		split.addWest(new ReportOptionen(reportRootPanel, nutzerVerwaltung), 1000);
		split.addEast(reportRootPanel, 1000);
		rp.add(split);
	}
}
