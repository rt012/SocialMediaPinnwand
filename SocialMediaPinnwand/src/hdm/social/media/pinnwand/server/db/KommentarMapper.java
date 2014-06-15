package hdm.social.media.pinnwand.server.db;

import java.sql.*;
import java.util.ArrayList;

import hdm.social.media.pinnwand.shared.*;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

/*
 * Methoden:
 * getKommentarById(int id)
 * getKommentarByBeitrag(int id)
 * countKommentareByBeitrag(int id)
 * insertKommentar(Kommentar k)
 * deleteKommentar(Kommentar k)
 * updateKommentar(Kommentar k)
 * checkAutor(Nutzer nutzer, Kommentar kommentar)
 */


public class KommentarMapper {
	// Variable die besagt ob schon kommentarMapperverbindung besteht
	 private static KommentarMapper kommentarMapper = null;
	 
	 
	 //leerer Konstruktor wegen Singleton
	 protected KommentarMapper() {
	 }

	 
	 //Singleton "Konstruktor"-methode
	 public static KommentarMapper kommentarMapper(){
		 if (kommentarMapper==null){
			 kommentarMapper= new KommentarMapper();
		 }
		 return kommentarMapper;
	 }
	 
	 
	 /**
	 * @see 	getKommentarById(int id): Sucht Kommentar anhand von ID
	 * @param 	Kommentar ID
	 * @return 	Kommentar Objekt
	 */
	 public Kommentar getKommentarById(int id){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Felder der Kommentartabelle anhand von ID
			ResultSet rs = stmt.executeQuery("SELECT * FROM kommentar WHERE kommentar_ID=" + id );
			
			//Maximal ein Rï¿½ckgabewert da Id Primï¿½rschlï¿½ssel
			if (rs.next()) {
		        // Ergebnis in Beitrag- Objekt umwandeln
		        Kommentar k = new Kommentar();
		        k.setId(rs.getInt("kommentar_ID"));
		        k.setErstellungsZeitpunkt(rs.getTimestamp("erstellung"));
		        k.setInhalt(rs.getString("inhalt"));
		        //k.setBeitrag(BeitragMapper.beitragMapper().getBeitragById(rs.getInt("beitrag_ID")));
		        k.setNutzer(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("nutzer_ID")));
		        
		        
		        //Kommentar Objekt zurï¿½ckgeben
		        return k;
			}
		}
		
	    catch (SQLException e) {
    		e.printStackTrace();
    		return null;
	    }
		//Falls keines gefunden leere Liste
		return null;

	 }
	 
	 
	 /**
	 * @see		getKommentarByBeitrag(int id): Sucht alle Kommentare zu einem Beitrag
	 * @param 	Beitrag ID
	 * @return 	ArrayList mit Kommentar Objekten
	 */
	 public ArrayList<Kommentar> getKommentarByBeitrag(Beitrag beitrag){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		ArrayList <Kommentar> kommentarListe= new ArrayList<Kommentar>();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Kommentar zu einem Beitrag
			ResultSet rs = stmt.executeQuery("SELECT * FROM kommentar WHERE beitrag_ID="+beitrag.getId());

			while (rs.next()) {
		        // Ergebnis in Kommentar- Objekt umwandeln
				Kommentar k = new Kommentar();
		        k.setId(rs.getInt("kommentar_ID"));
		        k.setErstellungsZeitpunkt(rs.getTimestamp("erstellung"));
		        k.setInhalt(rs.getString("inhalt"));
		        k.setBeitrag(beitrag);
		        k.setNutzer(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("nutzer_ID")));
		        
		        //Kommentar Objekte der ArrayList hinzufï¿½gen
		        kommentarListe.add(k);
		      }
			return kommentarListe;
		}
		
	    catch (SQLException e) {
	    		e.printStackTrace();
	    }
		//Falls keines gefunden leere Liste
		return kommentarListe;
	 }
	 
	 
	 /**
	 * @see 	countKommentarByBeitrag(int id): Zï¿½hlt alle Kommentare zu einem Beitrag
	 * @pram 	Beitrag ID
	 * @return 	int mit Anzahl
	 */
	 public int countKommentareByBeitrag(int id){
		 int count = -1;
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Beitrag
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM kommentar WHERE beitrag_ID=" +id);

		    //Maximal ein Rï¿½ckgabewert da Id Primï¿½rschlï¿½ssel
			while (rs.next()) {
		        count=rs.getInt(1);
		      }
			
		}
		
	    catch (SQLException e) {
	    		e.printStackTrace();
	    }
		
		//Falls keines gefunden Rï¿½ckgabe 0, sonst Rï¿½ckgabe der Anzahl oder bei Fehler -1
		return count;
	 }
	 
	 
	 /**
	 * @see 	insertKommentar(Kommentar k): Speichert Kommentar Objekt in der Datenbank
	 * @param 	Kommentar Objekt
	 * @return 	Gespeichertes Kommentar Objekt
	 */
	 public Kommentar insertKommentar(Kommentar k){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		int maxid = 0;
						
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();

	      /**
	       * Zunï¿½chst schauen wir nach, welches der momentan hï¿½chste
	       * Primï¿½rschlï¿½sselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(kommentar_ID) AS maxid "
	          + "FROM kommentar ");

	      // Wenn wir etwas zurï¿½ckerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	    	  	//k erhï¿½lt um 1 hï¿½here ID als das maximale Element in der Tabelle
	    	  	maxid=rs.getInt("maxid");
		        k.setId(rs.getInt("maxid") + 1);
	
		        stmt = con.createStatement();
	
		        // Jetzt erst erfolgt die tatsï¿½chliche Einfï¿½geoperation
		        stmt.executeUpdate("INSERT INTO kommentar (kommentar_ID, inhalt, nutzer_ID, beitrag_ID) "
		            + "VALUES (" + k.getId() + ",'"  + k.getInhalt() + "','" + k.getNutzer().getId()+ "','" 
		        	+ k.getBeitrag().getId() +"')");
		        
	      }
	    }
		
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    return getKommentarById(maxid+1);
	 }
	 
	 
	 /**
	 * @see 	deleteKommentar(Kommentar k): Lï¿½scht Kommentar Objekt aus Datenbank
	 * @param	Kommentar Objekt
	 * @return 		-
	 */
	 public void deleteKommentar(Kommentar k){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
	    try {
	      Statement stmt = con.createStatement();
	      //Lï¿½sche Beitrag mit gleicher ID aus Tabelle
	      stmt.executeUpdate("DELETE FROM kommentar WHERE kommentar_ID=" + k.getId());
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    } 
	 }
	 
	 
	 /**
	 * @see 	updateKommentar(Kommentar k): Updated den Inhalt eines Kommentarobjektes
	 * @param 	Kommentar Objekt
	 * @return	Geupdateder Kommentar
	 */
	 public Kommentar updateKommentar(Kommentar k){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
			//Versuch der Abfrage
		    try {
		      Statement stmt = con.createStatement();
		      //Aktualisieren des Inhalts
		      stmt.executeUpdate("UPDATE kommentar SET inhalt=\"" + k.getInhalt() + "\" WHERE kommentar_ID=" + k.getId());
	
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

	    // Zurï¿½ckgeben des aktuellen Beitragobjektes
	    return getKommentarById(k.getId());
		 
	 }
	 
	 /**
	   *  Methode welche überprüft ob ein bestimmter Kommentar vom eingeloggten Nutzer stammt.
	   * @param nutzer Nutzer der momentan eingeloggt ist
	   * @param kommentar Kommentar der angezeigt werden soll
	   * @return true/false, je nach dem ob eingeloggter Nutzer Autor ist oder nicht.
	   */
	public boolean checkAutor(Nutzer nutzer, Kommentar kommentar) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM kommentar WHERE nutzer_ID="+ nutzer.getId() +" AND kommentar_ID=" + kommentar.getId());
			 if(rs.next() == true) {
				 return true;
				
			 } else return false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
