package hdm.social.media.pinnwand.server.db;

import java.sql.*;
import java.util.ArrayList;
import hdm.social.media.pinnwand.shared.*;

/*
 * Methoden:
 * getKommentarById(int id)
 * getKommentarByBeitrag(int id)
 * countKommentareByBeitrag(int id)
 * insertKommentar(Kommentar k)
 * deleteKommentar(Kommentar k)
 * updateKommentar(Kommentar k)
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
	 
	 
	 /*
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
			
			//Maximal ein R�ckgabewert da Id Prim�rschl�ssel
			if (rs.next()) {
		        // Ergebnis in Beitrag- Objekt umwandeln
		        Kommentar k = new Kommentar();
		        k.setId(rs.getInt("kommentar_ID"));
		        k.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        k.setInhalt(rs.getString("inhalt"));
		        //k.setBeitrag(BeitragMapper.beitragMapper().getBeitragById(rs.getInt("beitrag_ID")));
		        k.setNutzer(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("nutzer_ID")));
		        
		        
		        //Kommentar Objekt zur�ckgeben
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
	 
	 
	 /*
	 * @see		getKommentarByBeitrag(int id): Sucht alle Kommentare zu einem Beitrag
	 * @param 	Beitrag ID
	 * @return 	ArrayList mit Kommentar Objekten
	 */
	 public ArrayList<Kommentar> getKommentarByBeitrag(int id){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		ArrayList <Kommentar> kommentarListe= new ArrayList<Kommentar>();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Kommentar zu einem Beitrag
			ResultSet rs = stmt.executeQuery("SELECT * FROM kommentar WHERE beitrag_ID="+id);

			while (rs.next()) {
		        // Ergebnis in Kommentar- Objekt umwandeln
				Kommentar k = new Kommentar();
		        k.setId(rs.getInt("kommentar_ID"));
		        k.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        k.setInhalt(rs.getString("inhalt"));
		        k.setBeitrag(BeitragMapper.beitragMapper().getBeitragById(rs.getInt("beitrag_ID")));
		        k.setNutzer(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("nutzer_ID")));
		        
		        //Kommentar Objekte der ArrayList hinzuf�gen
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
	 
	 
	 /*
	 * @see 	countKommentarByBeitrag(int id): Z�hlt alle Kommentare zu einem Beitrag
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

		    //Maximal ein R�ckgabewert da Id Prim�rschl�ssel
			while (rs.next()) {
		        count=rs.getInt(1);
		      }
			
		}
		
	    catch (SQLException e) {
	    		e.printStackTrace();
	    }
		
		//Falls keines gefunden R�ckgabe 0, sonst R�ckgabe der Anzahl oder bei Fehler -1
		return count;
	 }
	 
	 
	 /*
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

	      /*
	       * Zun�chst schauen wir nach, welches der momentan h�chste
	       * Prim�rschl�sselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(kommentar_ID) AS maxid "
	          + "FROM kommentar ");

	      // Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	    	  	//k erh�lt um 1 h�here ID als das maximale Element in der Tabelle
	    	  	maxid=rs.getInt("maxid");
		        k.setId(rs.getInt("maxid") + 1);
	
		        stmt = con.createStatement();
	
		        // Jetzt erst erfolgt die tats�chliche Einf�geoperation
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
	 
	 
	 /*
	 * @see 	deleteKommentar(Kommentar k): L�scht Kommentar Objekt aus Datenbank
	 * @param	Kommentar Objekt
	 * @return 		-
	 */
	 public void deleteKommentar(Kommentar k){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
	    try {
	      Statement stmt = con.createStatement();
	      //L�sche Beitrag mit gleicher ID aus Tabelle
	      stmt.executeUpdate("DELETE FROM kommentar WHERE kommentar_ID=" + k.getId());
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    } 
	 }
	 
	 
	 /*
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

	    // Zur�ckgeben des aktuellen Beitragobjektes
	    return getKommentarById(k.getId());
		 
	 }
	
}
