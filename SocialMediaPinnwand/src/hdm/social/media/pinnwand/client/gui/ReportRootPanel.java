package hdm.social.media.pinnwand.client.gui;

import hdm.social.media.pinnwand.client.NutzerVerwaltung;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import java.text.ParseException;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

public class ReportRootPanel extends SplitLayoutPanel{
	
	public ReportRootPanel(){		
	}
	
	public void loadNutzerReport(Date datumVon, Date datumBis, Nutzer nutzer){
		clear();
		try {
			add(new NutzerReportPanel(datumVon, datumBis, nutzer));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadBeitragReport(Date datumVon, Date datumBis){
		clear();
		add (new BeitragReportPanel(datumVon, datumBis));
	}
	
	
}
