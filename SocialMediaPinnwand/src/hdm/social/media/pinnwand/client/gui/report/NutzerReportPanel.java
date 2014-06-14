package hdm.social.media.pinnwand.client.gui.report;

import java.text.ParseException;
import java.util.Date;
import hdm.social.media.pinnwand.shared.ReportGeneratorAdministration;
import hdm.social.media.pinnwand.shared.ReportGeneratorAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

/**
 * 
 * Klasse zur Darstellung von Nutzerreports.
 */
public class NutzerReportPanel extends SplitLayoutPanel{
	
	
	private final ReportGeneratorAdministrationAsync reportGenerator = GWT.create(ReportGeneratorAdministration.class);
	
	@SuppressWarnings("deprecation")
	public NutzerReportPanel(Date datumVon, Date datumBis, Nutzer nutzer) throws ParseException{
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
		/**
		 * Erstellung des Nutzerreports
		 */
		reportGenerator.CreateNutzerReport(nutzer, datumVonString, datumBisString, new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {}
			/**
			 * Das Ergebnis wird dem Layout hinzugefügt. 
			 * @param result
			 */
			@Override
			public void onSuccess(String result) {
				HTML html = new HTML(result);
				html.setStyleName("nutzerReport");
				add(html);	
			}	
		});
	}
}