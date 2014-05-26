package hdm.social.media.pinnwand.server;


import java.util.ArrayList;
import java.util.Date;

import hdm.social.media.pinnwand.report.BeitragReport;
import hdm.social.media.pinnwand.report.Column;
import hdm.social.media.pinnwand.report.CompositeParagraph;
import hdm.social.media.pinnwand.report.NutzerReport;
import hdm.social.media.pinnwand.report.Report;
import hdm.social.media.pinnwand.report.Row;
import hdm.social.media.pinnwand.report.SimpleParagraph;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.ReportGenerator;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	   * Ein ReportGenerator benötigt Zugriff auf die BankAdministration, da diese die
	   * essentiellen Methoden für die Koexistenz von Datenobjekten (vgl.
	   * bo-Package) bietet.
	   */
	  private PinnwandAdministration administration = null;

	  /**
	   * <p>
	   * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
	   * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
	   * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
	   * Konstruktors ist durch die Client-seitige Instantiierung durch
	   * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
	   * möglich.
	   * </p>
	   * <p>
	   * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
	   * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
	   * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
	   * </p>
	   */
	  public ReportGeneratorImpl() throws IllegalArgumentException {
	  }

	  /**
	   * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor.
	   * 
	   * @see #ReportGeneratorImpl()
	   */
	  public void init() throws IllegalArgumentException {
	    /*
	     * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf eine
	     * BankVerwaltungImpl-Instanz.
	     */
	    PinnwandAdministrationImpl a = new PinnwandAdministrationImpl();
	    a.init();
	    this.administration = a;
	  }

	  /**
	   * Auslesen der zugehörigen BankAdministration (interner Gebrauch).
	   * 
	   * @return das BankVerwaltungsobjekt
	   */
	  protected PinnwandAdministration getPinnwandVerwaltung() {
	    return this.administration;
	  }


	  /**
	   * Erstellen von <code>AllAccountsOfCustomerReport</code>-Objekten.
	   * 
	   * @param c das Kundenobjekt bzgl. dessen der Report erstellt werden soll.
	   * @return der fertige Report
	   */
	  public BeitragReport createBeitragReport(
	      Beitrag b) throws IllegalArgumentException {

	    if (this.getPinnwandVerwaltung() == null)
	      return null;

	    /*
	     * Zunächst legen wir uns einen leeren Report an.
	     */
	    BeitragReport result = new BeitragReport();

	    // Jeder Report hat einen Titel (Bezeichnung / Überschrift).
	    result.setTitle("Alle Beiträge");


	    /*
	     * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
	     * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
	     */
	    result.setCreated(new Date());

	    /*
	     * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die oben
	     * auf dem Report stehen) des Reports. Die Kopfdaten sind mehrzeilig, daher
	     * die Verwendung von CompositeParagraph.
	     */
	    CompositeParagraph header = new CompositeParagraph();

	    // Name und Vorname des Kunden aufnehmen
	    header.addSubParagraph(new SimpleParagraph(b.getNutzer().getName() + ", "
	        + b.getNutzer().getVorname()));

	    // Kundennummer aufnehmen
	    header.addSubParagraph(new SimpleParagraph("Email:" + b.getNutzer().getEmail()));

	    // Hinzufügen der zusammengestellten Kopfdaten zu dem Report
	    result.setHeaderData(header);

	    /*
	     * Ab hier erfolgt ein zeilenweises Hinzufügen von Konto-Informationen.
	     */
	    
	    /*
	     * Zunächst legen wir eine Kopfzeile für die Konto-Tabelle an.
	     */
	    Row headline = new Row();

	    /*
	     * Wir wollen Zeilen mit 2 Spalten in der Tabelle erzeugen. In die erste
	     * Spalte schreiben wir die jeweilige Kontonummer und in die zweite den
	     * aktuellen Kontostand. In der Kopfzeile legen wir also entsprechende
	     * Überschriften ab.
	     */
	    headline.addColumn(new Column("Nutzer"));
	    headline.addColumn(new Column("Inhalt"));
	    headline.addColumn(new Column("Like-Anzahl"));
	    headline.addColumn(new Column("Kommentaranzahl"));

	    // Hinzufügen der Kopfzeile
	    result.addRow(headline);

	    /*
	     * Nun werden sämtliche Konten des Kunden ausgelesen und deren Kto.-Nr. und
	     * Kontostand sukzessive in die Tabelle eingetragen.
	     */
	    ArrayList<Beitrag> beitraege  = this.administration.findAllBeitraege();

	    for (Beitrag beitrag : beitraege) {
	      // Eine leere Zeile anlegen.
	      Row accountRow = new Row();

	      // Erste Spalte: Name hinzufügen
	      accountRow.addColumn(new Column(beitrag.getNutzer().getName()));

	      // Zweite Spalte: Inhalt hinzufügen
	      accountRow.addColumn(new Column(beitrag.getInhalt()));
	      
	      // Dritte Spalte: Like-Anzahl hinzufügen
	      accountRow.addColumn(new Column(String.valueOf(beitrag.getLikeList().size())));
	      
	      // Dritte Spalte: Like-Anzahl hinzufügen
	      accountRow.addColumn(new Column(String.valueOf(beitrag.getKommentarListe().size())));

	      // und schließlich die Zeile dem Report hinzufügen.
	      result.addRow(accountRow);
	    }

	    /*
	     * Zum Schluss müssen wir noch den fertigen Report zurückgeben.
	     */
	    return result;
	  }
	  
	  
	  /**
	   * Erstellen von <code>AllAccountsOfCustomerReport</code>-Objekten.
	   * 
	   * @param c das Kundenobjekt bzgl. dessen der Report erstellt werden soll.
	   * @return der fertige Report
	   */
	  public NutzerReport createNutzerReport(
	      Nutzer n) throws IllegalArgumentException {

	    if (this.getPinnwandVerwaltung() == null)
	      return null;

	    /*
	     * Zunächst legen wir uns einen leeren Report an.
	     */
	    NutzerReport result = new NutzerReport();

	    // Jeder Report hat einen Titel (Bezeichnung / Überschrift).
	    result.setTitle("Alle Informationen eines Nutzer");


	    /*
	     * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
	     * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
	     */
	    result.setCreated(new Date());

	    /*
	     * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die oben
	     * auf dem Report stehen) des Reports. Die Kopfdaten sind mehrzeilig, daher
	     * die Verwendung von CompositeParagraph.
	     */
	    CompositeParagraph header = new CompositeParagraph();

	    // Name und Vorname des Kunden aufnehmen
	    header.addSubParagraph(new SimpleParagraph(n.getName() + ", "
	        + n.getVorname()));

	    // Kundennummer aufnehmen
	    header.addSubParagraph(new SimpleParagraph("Email:" + n.getEmail()));

	    // Hinzufügen der zusammengestellten Kopfdaten zu dem Report
	    result.setHeaderData(header);

	    /*
	     * Ab hier erfolgt ein zeilenweises Hinzufügen von Konto-Informationen.
	     */
	    
	    /*
	     * Zunächst legen wir eine Kopfzeile für die Konto-Tabelle an.
	     */
	    Row headline = new Row();

	    /*
	     * Wir wollen Zeilen mit 2 Spalten in der Tabelle erzeugen. In die erste
	     * Spalte schreiben wir die jeweilige Kontonummer und in die zweite den
	     * aktuellen Kontostand. In der Kopfzeile legen wir also entsprechende
	     * Überschriften ab.
	     */
	    headline.addColumn(new Column("Abonnentenanzahl"));
	    headline.addColumn(new Column("Beitragsanzahl"));
	    headline.addColumn(new Column("Likes bekommen"));
	    headline.addColumn(new Column("Likes gegeben"));

	    // Hinzufügen der Kopfzeile
	    result.addRow(headline);
	    

	    /*
	     * Nun werden sämtliche Konten des Kunden ausgelesen und deren Kto.-Nr. und
	     * Kontostand sukzessive in die Tabelle eingetragen.
	     */
	    
	    Row accountRow = new Row();
	    
	    accountRow.addColumn(new Column(String.valueOf(n.getAbonnentenListe().size())));
	    accountRow.addColumn(new Column(String.valueOf(n.getPinnwand().getBeitraege().size())));
	    int likeAnzahl = 0;
	    for (Beitrag beitrag : n.getPinnwand().getBeitraege()) {
	      likeAnzahl += beitrag.getLikeList().size();
	    }
	    accountRow.addColumn(new Column(String.valueOf(likeAnzahl)));
	    accountRow.addColumn(new Column(String.valueOf(this.administration.getLikeCountByNutzer(n))));
	
	    // und schließlich die Zeile dem Report hinzufügen.
	    result.addRow(accountRow);
	    /*
	     * Zum Schluss müssen wir noch den fertigen Report zurückgeben.
	     */
	    return result;
	  }
}
