package hdm.social.media.pinnwand.client.gui;

import hdm.social.media.pinnwand.shared.bo.Nutzer;

import java.util.Date;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabBar;


public class ReportRootPanel extends SplitLayoutPanel{
	
	public ReportRootPanel(){
		
	}
	
	public void loadNutzerReport(Date datumVon, Date datumBis, Nutzer nutzer){
		add(new NutzerReportPanel(datumVon, datumBis, nutzer));
	}
	
	public void loadBeitragReport(Date datumVon, Date datumBis){
		add (new BeitragReportPanel(datumBis, datumBis));
	}
	
	
}
