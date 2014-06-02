package hdm.social.media.pinnwand.client.gui;

import java.util.Date;

import hdm.social.media.pinnwand.shared.ReportGenerator;
import hdm.social.media.pinnwand.shared.ReportGeneratorAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class BeitragReportPanel extends SplitLayoutPanel {
	
	private Date dateFrom = new Date();
	private Date dateTo = new Date();
	private DatePicker datePickerFrom = new DatePicker();
	private DatePicker datePickerTo = new DatePicker();
	private Button createReportButton = new Button();
	private VerticalPanel showReportPanel = new VerticalPanel();
	
	private final ReportGeneratorAsync reportGenerator = GWT.create(ReportGenerator.class);
	
	public BeitragReportPanel(){
		datePickerFrom.addValueChangeHandler(new ValueChangeHandler<Date>() {
		      public void onValueChange(ValueChangeEvent<Date> event) {
		    	  dateFrom = event.getValue();
		         
		        }
		      });
		datePickerTo.addValueChangeHandler(new ValueChangeHandler<Date>() {
		      public void onValueChange(ValueChangeEvent<Date> event) {
		    	  dateTo = event.getValue();
		        }
		      });
		
		createReportButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//reportGenerator.CreateNutzerReport(n, callback);
				//Im callback showReportPanel.add(report html)
			}
			
		});
		
		addWest(datePickerFrom, 1);
		addWest(datePickerTo, 1);
		addEast(createReportButton, 1);
		addSouth(showReportPanel, 1);
		
	}
}
	
