package hdm.social.media.pinnwand.client.gui;

import java.util.Date;

import hdm.social.media.pinnwand.shared.ReportGenerator;
import hdm.social.media.pinnwand.shared.ReportGeneratorAsync;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

public class NutzerReportPanel extends SplitLayoutPanel{
	
	private final ReportGeneratorAsync reportGenerator = GWT.create(ReportGenerator.class);
	
	public NutzerReportPanel(Date datumVon, Date datumBis, Nutzer nutzer){
		reportGenerator.CreateNutzerReport(nutzer, datumVon, datumBis, new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {}

			@Override
			public void onSuccess(String result) {
				HTML html = new HTML(result);
				add(html);	
			}	
		});
	}
}