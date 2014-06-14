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

/**
 * EntryPoint Klasse für den Report Generator 
 * 
 * @author Blessing
 */

public class ReportGenerator implements EntryPoint {
	private SplitLayoutPanel split = new SplitLayoutPanel();
	private RootLayoutPanel rp = RootLayoutPanel.get();
	private ReportRootPanel reportRootPanel = null;
	private NutzerVerwaltung nutzerVerwaltung = null;
	/**
	 * Stellt eine Verbindung über einen Proxy her, um mit der Serverseite zu kommunizeren
	 * 
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
		/**
		 * Überprüft den Login Status --> Nach Deploy: GWT.getHostPageBaseURL() 
		 */
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
	
	/**
	 * Die Methode loadReportGenerator(), erstellt ein Rootpanel und
	 * fügt dem Splitpanel einmal das Rootpanel zur rechten Seite hinzu.
	 * Zweites fügt er auf der linken Seite die ReportOptionen hinzu.
	 */
	 
	public void loadReportGenerator(){
		reportRootPanel = new ReportRootPanel();
		reportRootPanel.setStyleName("ReportRoot");
		ReportOptionen reportOptionen = new ReportOptionen(reportRootPanel, nutzerVerwaltung);
		reportOptionen.setStyleName("ReportOptionen");
		split.addWest(reportOptionen, 1000);
		split.addEast(reportRootPanel, 1000);
		rp.add(split);
	}
}
