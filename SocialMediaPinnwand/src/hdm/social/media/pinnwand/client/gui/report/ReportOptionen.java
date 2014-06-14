package hdm.social.media.pinnwand.client.gui.report;


import hdm.social.media.pinnwand.client.gui.CustomOracle;
import hdm.social.media.pinnwand.client.gui.CustomSuggest;
import hdm.social.media.pinnwand.client.gui.NutzerVerwaltung;
import hdm.social.media.pinnwand.shared.ReportGeneratorAdministration;
import hdm.social.media.pinnwand.shared.ReportGeneratorAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class ReportOptionen extends SplitLayoutPanel{
	
	private Label reportName = new Label();
	private Date datumVon = new Date();
	private Date datumBis= new Date();
	private DatePicker datePickerVon = new DatePicker();
	private DatePicker datePickerBis = new DatePicker();
	private Nutzer nutzer = null;
	private CustomOracle oracle = new CustomOracle();
	private final SuggestBox suggestBoxPinnwandSuche = new SuggestBox(oracle);
	private Button createReportButton = new Button("");
	private ReportRootPanel reportRootPanel = null;
	private RadioButton radioButtonBeitrag = new RadioButton("Report Auswahl" , "Beitrag");
	private RadioButton radioButtonNutzer = new RadioButton("Report Auswahl" , "Nutzer");
	private Button logout = new Button("");
	
	private final ReportGeneratorAdministrationAsync reportGenerator = GWT.create(ReportGeneratorAdministration.class);
	
	public ReportOptionen(final ReportRootPanel reportRootPanel,final NutzerVerwaltung nutzerVerwaltung){
		this.reportRootPanel = reportRootPanel;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("verticalPanel");
		fillSuggestenBox();
		
		
		
		/**
		 * Events der Widgets
		 */
		
		reportName.setText("ReportGenerator");
		reportName.setStyleName("reportName");
		
		
		//Logout Button
		
		logout.setStyleName("buttonLogout");
		logout.addClickHandler(new ClickHandler(){		
			@Override
			public void onClick(ClickEvent event) {
				nutzerVerwaltung.loadLogout();
			}
		});
		
		suggestBoxPinnwandSuche.setVisible(false);
		datePickerVon.setVisible(false);
		datePickerBis.setVisible(false);
		createReportButton.setVisible(false);
		createReportButton.setStyleName("createReportButton");
		
		radioButtonBeitrag.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				datePickerVon.setVisible(true);
				datePickerBis.setVisible(true);
				createReportButton.setVisible(true);
				suggestBoxPinnwandSuche.setVisible(false);
			}
			
		});
		
		radioButtonNutzer.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				suggestBoxPinnwandSuche.setVisible(true);
				datePickerVon.setVisible(true);
				datePickerBis.setVisible(true);
				createReportButton.setVisible(true);
			}
			
		});

		
		
		suggestBoxPinnwandSuche.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>(){
			public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event){
				nutzer = ((CustomSuggest)event.getSelectedItem()).getNutzer();
				suggestBoxPinnwandSuche.setText(nutzer.getVorname() + " " + nutzer.getName());
			}
		});
		
		datePickerVon.addValueChangeHandler(new ValueChangeHandler<Date>() {
		      public void onValueChange(ValueChangeEvent<Date> event) {
		    	  datumVon = event.getValue();
		         
		        }
		      });
		datePickerBis.addValueChangeHandler(new ValueChangeHandler<Date>() {
		      public void onValueChange(ValueChangeEvent<Date> event) {
		    	  datumBis = event.getValue();
		        }
		      });
		createReportButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(radioButtonNutzer.getValue()){
					reportRootPanel.loadNutzerReport(datumVon, datumBis, nutzer);
				}else if(radioButtonBeitrag.getValue()){
					reportRootPanel.loadBeitragReport(datumVon, datumBis);
				}else{
					Window.alert("Bitte wählen Sie einen Report Typ aus");
				}
				
			}
			
		});
		
		verticalPanel.add(reportName);
		verticalPanel.add(logout);
		verticalPanel.add(radioButtonBeitrag);
		verticalPanel.add(radioButtonNutzer);
		verticalPanel.add(suggestBoxPinnwandSuche);
		verticalPanel.add(datePickerVon);
		verticalPanel.add(datePickerBis);
		verticalPanel.add(createReportButton);
		add(verticalPanel);
		
	}
	
	/**
	 * Befüllt die SuggestenBox mit allen Nutzern der Anwendung
	 * 
	 * @author Eric Schmidt
	 */
	public void fillSuggestenBox(){
		reportGenerator.getAllNutzer(new AsyncCallback<ArrayList<Nutzer>>() {
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
				suggestBoxPinnwandSuche.ensureDebugId("cwSuggestBox");
			}
		});
	}
}
	


