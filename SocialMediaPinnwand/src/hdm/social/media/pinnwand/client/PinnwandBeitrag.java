package hdm.social.media.pinnwand.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Diese Klasse ist die Basisklasse aller Beiträge. Jeder Beitrag ist ein HorizontalPanel und lässt sich 
 * somit unter GWT entsprechend anordnen 
 * @author remi
 *
 */

public class PinnwandBeitrag extends HorizontalPanel  {
	
	/**
	   * Jedes GWT Widget muss diese Methode implementieren. Sie gibt an, sas
	   * geschehen soll, wenn eine Widget-Instanz zur Anzeige gebracht wird.
	   */
	  public void onLoad() {
	    /*
	     * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es der
	     * Superklasse eine Initialisierung vorzunehmen.
	     */
	    super.onLoad();
	    
		 /**
		  * Elemente ( Widgets)  eines Beitrags
		  */
		 //Anzeige des Inhalts 
		  Label LabelBeitragsInhalt = new Label();
		 LabelBeitragsInhalt.setStyleName("LabelBeitragsInhalt");
		 
		 
		 // Autor + Erstellungszeitpunkt
		 Label LabelBeitragsAutor = new Label();
		 LabelBeitragsAutor.setStyleName("LabelBeitragsAutor");
		 
		 
		 // Like Anzahl
		 Label LabelBeitragLikeAnzahl = new Label();
		 LabelBeitragLikeAnzahl.setStyleName("LabelBeitragLikeAnzahl");
		 
		 
		 //Button für "Gefällt mir
		  Button ButtonBeitragGefälltMir = new Button("Gefällt mir");
		 ButtonBeitragGefälltMir.setStyleName("ButtonBeitragGefälltMir");
		 
		 //Button für Kommentieren
		  Button ButtonBeitragKommentieren = new Button("Kommentieren");
		 ButtonBeitragKommentieren.setStyleName("ButtonBeitragKommentieren");
		 
	    
	  }

}
