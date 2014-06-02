package hdm.social.media.pinnwand.client.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.ReportGenerator;
import hdm.social.media.pinnwand.shared.ReportGeneratorAsync;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class NutzerReportPanel extends SplitLayoutPanel{
	
	private Date dateFrom = new Date();
	private Date dateTo = new Date();
	private CustomOracle oracle = new CustomOracle();
	private final SuggestBox SuggestBoxPinnwandSuche = new SuggestBox(oracle);
	private DatePicker datePickerFrom = new DatePicker();
	private DatePicker datePickerTo = new DatePicker();
	private Button createReportButton = new Button("Report Erstellen");
	private Nutzer nutzer = null;
	private VerticalPanel showReportPanel = new VerticalPanel();
	private HorizontalPanel optionPanel = new HorizontalPanel();
	
	private final ReportGeneratorAsync reportGenerator = GWT.create(ReportGenerator.class);
	private final PinnwandAdministrationAsync pinnwandAdministration = GWT.create(PinnwandAdministration.class);
	
	public NutzerReportPanel(){
		fillSuggestenBox();
		SuggestBoxPinnwandSuche.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>(){
			public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event){
				nutzer = ((CustomSuggest)event.getSelectedItem()).getNutzer();
				SuggestBoxPinnwandSuche.setText(nutzer.getVorname() + " " + nutzer.getName());
			}
		});
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
				if(nutzer != null){
					reportGenerator.CreateNutzerReport(nutzer, new AsyncCallback<String>(){

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(String result) {
							HTML html = new HTML(result);
							showReportPanel.add(html);
							
						}
						
					});
				}
				//Im callback showReportPanel.add(report html)
			}
			
		});
		createReportButton.setWidth("20");
		createReportButton.setHeight("20");
		
		optionPanel.add(SuggestBoxPinnwandSuche);
		optionPanel.add(datePickerFrom);
		optionPanel.add(datePickerTo);
		optionPanel.add(createReportButton);
		

		addNorth(optionPanel,200);
		add(showReportPanel);
		
	}
	
	/**
	 * Befüllt die SuggestenBox mit allen Nutzern der Anwendung
	 * 
	 * @author Eric Schmidt
	 */
	public void fillSuggestenBox(){
		pinnwandAdministration.getAllNutzer(new AsyncCallback<ArrayList<Nutzer>>() {
			 public void onFailure
			 (Throwable caught) {
			 // TODO: Do something with errors.
			 }
			 
			@Override
			public void onSuccess(ArrayList<Nutzer> result) {
				ArrayList<CustomSuggest> suggestList = new ArrayList<CustomSuggest>();
				for (Nutzer n : result) {
					CustomSuggest suggest = new CustomSuggest(n);
					suggestList.add(suggest);
				}
				Collection<CustomSuggest> nutzerCollection = suggestList;
				oracle.setCollection(nutzerCollection);
				SuggestBoxPinnwandSuche.ensureDebugId("cwSuggestBox");
			}
		});
	}
}
