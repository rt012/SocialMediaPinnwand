package hdm.social.media.pinnwand.client.gui.report;

import java.util.Date;

import hdm.social.media.pinnwand.shared.ReportGeneratorAdministration;
import hdm.social.media.pinnwand.shared.ReportGeneratorAdministrationAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

public class BeitragReportPanel extends SplitLayoutPanel {
	
	/**
	 * Klasse für die Darstellung eines Beitragsreport.
	 */
	
private final ReportGeneratorAdministrationAsync reportGenerator = GWT.create(ReportGeneratorAdministration.class);
	
	@SuppressWarnings("deprecation")
	public BeitragReportPanel(Date datumVon, Date datumBis){
		/**
		 * Setze Stunden, Minuten und Sekunden der übergebenen Daten, um das selbe
		 * Format wie in der Datenbank zu erhalten. Anschließend wird über das 
		 * SimpleDateFormat auf das Format (yyyy-MM-dd HH:mm:ss) der Datenbank formatiert.
		 */
		DateTimeFormat simpleDateFormat = DateTimeFormat.getFormat("yyyy-MM-dd' 'HH:mm:ss");
		
		datumBis.setHours(0);
		datumBis.setMinutes(0);
		datumBis.setSeconds(0);
		datumBis.setDate(datumBis.getDate() +1);
		
		datumVon.setHours(0);
		datumVon.setMinutes(0);
		datumVon.setSeconds(0);
		
		String datumBisString = simpleDateFormat.format(datumBis);
		String datumVonString = simpleDateFormat.format(datumVon);
		
		reportGenerator.createBeitragReport(datumVonString, datumBisString, new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {}

			@Override
			public void onSuccess(String result) {
				//ScrollPanel sorgt dafür, dass das HTML scrollbar wird.
				ScrollPanel scrollPanel = new ScrollPanel();
				scrollPanel.setStyleName("ScrollPanel");
				HTML html = new HTML(result);
				
				html.setWidth("100%");
				scrollPanel.setSize("300", "200");    
				scrollPanel.add(html);
				
				add(scrollPanel);	
			}	
		});
	}
}