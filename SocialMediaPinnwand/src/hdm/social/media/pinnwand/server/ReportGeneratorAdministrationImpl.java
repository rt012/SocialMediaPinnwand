package hdm.social.media.pinnwand.server;


import java.util.ArrayList;
import java.util.Date;

import hdm.social.media.pinnwand.client.gui.LoginInfo;
import hdm.social.media.pinnwand.report.BeitragReport;
import hdm.social.media.pinnwand.report.Column;
import hdm.social.media.pinnwand.report.CompositeParagraph;
import hdm.social.media.pinnwand.report.HTMLReportWriter;
import hdm.social.media.pinnwand.report.NutzerReport;
import hdm.social.media.pinnwand.report.Row;
import hdm.social.media.pinnwand.report.SimpleParagraph;
import hdm.social.media.pinnwand.server.db.AboMapper;
import hdm.social.media.pinnwand.server.db.BeitragMapper;
import hdm.social.media.pinnwand.server.db.LikeMapper;
import hdm.social.media.pinnwand.server.db.NutzerMapper;
import hdm.social.media.pinnwand.server.db.PinnwandMapper;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.ReportGeneratorAdministration;
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReportGeneratorAdministrationImpl extends RemoteServiceServlet implements ReportGeneratorAdministration  {

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
	  public ReportGeneratorAdministrationImpl() throws IllegalArgumentException {
	  }

	  /**
	   * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor.
	   * 
	   * @see #ReportGeneratorImpl()
	   */
	  public void init() throws IllegalArgumentException {
	    /*
	     * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf eine
	     * Pinnwand-Instanz.
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
	 * Erstelle einen String welches durch den Report als HTML repräsentiert 
	 * 
	 * @param datumVon definiert den Anfang der Suchanfrage
	 * @param datumBis definiert das Ende der Suchanfrage
	 * @return der fertige HTML-Report
	 */
	public String createBeitragReport(String datumVon, String datumBis) throws IllegalArgumentException {

	    if (this.getPinnwandVerwaltung() == null)
	      return null;

	    /**
	     * Zunächst legen wir uns einen leeren Report an.
	     */
	    BeitragReport result = new BeitragReport();

	    // Jeder Report hat einen Titel (Bezeichnung / Überschrift).
	    result.setTitle("Alle Beiträge");


	    /**
	     * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
	     * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
	     */
	    result.setCreated(new Date());

	    /**
	     * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die oben
	     * auf dem Report stehen) des Reports. Die Kopfdaten sind mehrzeilig, daher
	     * die Verwendung von CompositeParagraph.
	     */
	    CompositeParagraph header = new CompositeParagraph();

	    /**
	     * Zeitraum der Report Suchanfrage in die Kopfzeile hinzufügen
	     */
	    header.addSubParagraph(new SimpleParagraph("Zeitraum Von: " + datumVon + " Bis: "
	        + datumBis));


	    // Hinzufügen der zusammengestellten Kopfdaten zu dem Report
	    result.setHeaderData(header);

	    /**
	     * Zunächst legen wir eine Kopfzeile für die Nutzer-Tabelle an.
	     */
	    Row headline = new Row();

	    /**
	     * Wir wollen Zeilen mit 4 Spalten in der Tabelle erzeugen. In die erste
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

	    /**
	     * Nun werden sämtliche Beiträge innerhalb eines Zeitraumes ausgelesen und Autor, Inhalt, Likes und 
	     * die Anzahl der Kommentare in die Tabelle eingetragen.
	     */
	    ArrayList<Beitrag> beitraege  = BeitragMapper.beitragMapper().getBeitraegeBetweenTwoDates(datumVon, datumBis);
	 
	    beitraege = sort(beitraege);
	    
	    for (Beitrag beitrag : beitraege) {
	      // Eine leere Zeile anlegen.
	      Row accountRow = new Row();

	      // Erste Spalte: Nachname und Vorname hinzufügen
	      accountRow.addColumn(new Column(beitrag.getPinnwand().getNutzer().getVorname() + " " 
	    		  + beitrag.getPinnwand().getNutzer().getName()));

	      // Zweite Spalte: Inhalt hinzufügen
	      
	      accountRow.addColumn(new Column(beitrag.getInhalt()));
	      
	      // Dritte Spalte: Like-Anzahl hinzufügen
	      accountRow.addColumn(new Column(String.valueOf(beitrag.getLikeList().size())));
	      
	      // Dritte Spalte: Kommentar-Anzahl hinzufügen
	      accountRow.addColumn(new Column(String.valueOf(beitrag.getKommentarListe().size())));

	      // und schließlich die Zeile dem Report hinzufügen.
	      result.addRow(accountRow); 
	    }
	    
	    /**
	     * Übergebe den erstellten Report dem HTMLReportWriter um HTML zu erzeugen
	     */
	    HTMLReportWriter writer = new HTMLReportWriter();
	    writer.process(result);
	    
	    /**
	     * Zum Schluss müssen wir noch den fertigen HTML-Report zurückgeben.
	     */
	    return writer.getReportText();
	  }
	  
	@Override
	public String CreateNutzerReport(Nutzer n, String datumVon, String datumBis) throws IllegalArgumentException {

		if (this.getPinnwandVerwaltung() == null)
		      return null;

		    /**
		     * Zunächst legen wir uns einen leeren Report an.
		     */
		    NutzerReport result = new NutzerReport();

		    // Jeder Report hat einen Titel (Bezeichnung / Überschrift)
		    result.setTitle("Informationen über " + n.getVorname() + " " + n.getName());

		    /**
		     * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
		     * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		     */
		    result.setCreated(new Date());

		    /**
		     * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die oben
		     * auf dem Report stehen) des Reports. Die Kopfdaten sind mehrzeilig, daher
		     * die Verwendung von CompositeParagraph.
		     */
		    CompositeParagraph header = new CompositeParagraph();

		    // Name und Vorname des Nutzers aufnehmen
		    header.addSubParagraph(new SimpleParagraph(n.getName() + ", "
		        + n.getVorname()));

		    // Email-Adresse aufnehmen
		    header.addSubParagraph(new SimpleParagraph("Email:" + n.getEmail()));
		    
		    // Zeitraum aufnehmen
		    header.addSubParagraph(new SimpleParagraph("Zeitraum Von: " + datumVon + " Bis: "
			        + datumBis));

		    // Hinzufügen der zusammengestellten Kopfdaten zu dem Report
		    result.setHeaderData(header);

		    /**
		     * Zunächst legen wir eine Kopfzeile für die Konto-Tabelle an.
		     */
		    Row headline = new Row();

		    /**
		     * Wir wollen Zeilen mit 4 Spalten in der Tabelle erzeugen. In die erste
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
		    
		    Row accountRow = new Row();
		    
		    /**
		     * Berechne die Abonnentenanzahl
		     */
		    ArrayList<Abo> aboListe = AboMapper.aboMapper().getAboBetweenTwoDates(datumVon, datumBis, n);  
		    if (aboListe != null){
		    	accountRow.addColumn(new Column(String.valueOf(aboListe.size())));
		    }else{
		    	accountRow.addColumn(new Column("0"));
		    }
		
		    /**
		     * Berechne die Beitragsanzahl
		     */
		    ArrayList<Beitrag> beitragListe = BeitragMapper.beitragMapper().getBeitraegeBetweenTwoDates(datumVon, datumBis, 
		    		PinnwandMapper.pinnwandMapper().getPinnwandByNutzer(n).getId());
		    if (beitragListe != null){
		    	accountRow.addColumn(new Column(String.valueOf(beitragListe.size())));
		    }else{
		    	accountRow.addColumn(new Column("0"));
		    }
		    
		    /**
		     * Berechne die Like Anzahl
		     */
		    int likeAnzahl = 0;
		    if (beitragListe != null){
			    for (Beitrag beitrag : beitragListe) {
				    if(beitrag.getLikeList() != null)
				    likeAnzahl += beitrag.getLikeList().size();
			    }
		    }
		    accountRow.addColumn(new Column(String.valueOf(likeAnzahl)));
		    
		    /**
		     * Berechne die gegeben Likes
		     */
		    accountRow.addColumn(new Column(String.valueOf(LikeMapper.likeMapper().getLikeCountByNutzer(n, datumVon, datumBis))));
		
		    // und schließlich die Zeile dem Report hinzufügen.
		    result.addRow(accountRow);
		    HTMLReportWriter writer = new HTMLReportWriter();
		    writer.process(result);
		    /*
		     * Zum Schluss müssen wir noch den fertigen Report zurückgeben.
		     */
		    return writer.getReportText();
	}

	@Override
	public ArrayList<Nutzer> getAllNutzer() throws IllegalArgumentException {
		return NutzerMapper.nutzerMapper().getAllNutzer();
	}

	/**
	 * Bubblesort um eine Beitragsliste nach den Likes aufsteigend zu sortieren
	 * 
	 * @param array
	 * @return
	 */
	private ArrayList<Beitrag> sort(ArrayList<Beitrag> array) {
		int n = array.size();
		for(int i = n-1; i >= 0; i--) {
			for (int j = 1; j <= i; j++) {
				if ( array.get(j-1).getLikeList().size() < array.get(j).getLikeList().size()) {
				Beitrag temp = array.get(j-1);
	        	array.set(j-1, array.get(j));
	        	array.set(j, temp);
					
				}
			}
		}
		return array;
	}
			
			@Override
			public LoginInfo login(String requestUri) {
				UserService userService = UserServiceFactory.getUserService();
				User user = userService.getCurrentUser();
				LoginInfo loginInfo = new LoginInfo();

				if (user != null) {
					loginInfo.setLoggedIn(true);
				    loginInfo.setEmailAddress(user.getEmail());
				    loginInfo.setNickname(user.getNickname());
				    loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
				}else {
				    loginInfo.setLoggedIn(false);
				    loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
				}
					return loginInfo;
			}

			@Override
			public Nutzer getNutzerByEmail(String email)
					throws IllegalArgumentException {
				return NutzerMapper.nutzerMapper().getNutzerByEmail("'"+email+"'");
			}
	
	
}
