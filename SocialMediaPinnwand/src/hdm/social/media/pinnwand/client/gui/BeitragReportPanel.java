package hdm.social.media.pinnwand.client.gui;

import java.util.Date;

import hdm.social.media.pinnwand.shared.ReportGenerator;
import hdm.social.media.pinnwand.shared.ReportGeneratorAsync;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class BeitragReportPanel extends SplitLayoutPanel {
	
	
private final ReportGeneratorAsync reportGenerator = GWT.create(ReportGenerator.class);
	
	public BeitragReportPanel(Date datumVon, Date datumBis){
		reportGenerator.CreatBeitragReport(datumVon, datumBis, new AsyncCallback<String>(){
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