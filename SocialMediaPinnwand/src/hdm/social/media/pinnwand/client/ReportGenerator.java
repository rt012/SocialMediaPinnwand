package hdm.social.media.pinnwand.client;

import hdm.social.media.pinnwand.client.gui.CustomOracle;
import hdm.social.media.pinnwand.client.gui.ReportOptionen;
import hdm.social.media.pinnwand.client.gui.ReportRootPanel;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;

/*
 * EntryPoint Klasse für den Report Generator 
 * 
 * @author Florian Blessing
 */

public class ReportGenerator implements EntryPoint {
	
	 @Override
	  public void onModuleLoad() {
	   SplitLayoutPanel split = new SplitLayoutPanel();
	   RootLayoutPanel rp = RootLayoutPanel.get();
	   
	   split.addWest(new ReportOptionen(), 1000);
	   split.addEast(new ReportRootPanel(), 1000);
	   rp.add(split);
	} 
}

	/*NutzerVerwaltung nutzerVerwaltung = null;
	private Nutzer aktuellerNutzer = null;
	
	private final Label pinnwandName = new Label("");
	private CustomOracle oracle = new CustomOracle();
	private final SuggestBox SuggestBoxPinnwandSuche = new SuggestBox(oracle);
	
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	
	/**
	 *
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 

	@Override
	public void onModuleLoad() {
	
	RootPanel.get("header").add(new HeaderPanel());    
	 

    
		
		PinnwandAdministration.login("http://127.0.0.1:8888/SocialMediaPinnwand.html?gwt.codesvr=127.0.0.1:9997", new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {}

			@Override
			public void onSuccess(LoginInfo result) {
				LoginInfo loginInfo = result;
		        nutzerVerwaltung = new NutzerVerwaltung(loginInfo);
		        if(loginInfo.isLoggedIn()) {
		        	nutzerVerwaltung.nutzerInDatenbank(loginInfo);
		        	loadReportGenerator();
		        } else {
		        	nutzerVerwaltung.loadLogin();
		        }
			}
	    });	
		
	}	

				public void loadReportGenerator(){
				SplitLayoutPanel split = new SplitLayoutPanel();
				split.setStyleName("rootSplitPanel");
				
				SplitLayoutPanel vsplit = new SplitLayoutPanel();
				RootLayoutPanel rp = RootLayoutPanel.get();	
			}
		
		}
	
	*/
