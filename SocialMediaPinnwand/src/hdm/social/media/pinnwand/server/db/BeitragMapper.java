package hdm.social.media.pinnwand.server.db;

import java.sql.*;
import java.util.ArrayList;

import hdm.social.media.pinnwand.shared.*;
import hdm.social.media.pinnwand.shared.bo.Beitrag;


import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

/*
 * Methoden:
 * getBeitragById(int id)
 * getBeitragByPinnwand(int id)
 * getAllBeitrag()
 * countBeitrag()
 * insertBeitrag(Beitrag b)
 * deleteBeitrag(Beitrag b)
 * updateBeitrag(Beitrag b)
 */

public class BeitragMapper {
		 
	 // Variable die besagt ob schon BeitragMapperverbindung besteht
	 private static BeitragMapper beitragMapper = null;
	 
	 
	 //leerer Konstruktor wegen Singleton
	 protected BeitragMapper() {
	 }

	 
	 //Singleton "Konstruktor"-methode
	 public static BeitragMapper beitragMapper(){
		 if (beitragMapper==null){
			 beitragMapper= new BeitragMapper();
		 }
		 return beitragMapper;
	 }
	 
	
	 /**
	 * @see 	getBeitragById (int id): Sucht Beitrag anhand von ID
	 * @param 	Beitrag ID
	 * @return 	Beitrag Objekt
	 */
	 public Beitrag getBeitragById(int id){
		
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Felder der Beitragtabelle anhand von ID
			ResultSet rs = stmt.executeQuery("SELECT * FROM beitrag WHERE beitrag_ID=" + id );
			
			//Maximal ein R�ckgabewert da Id Prim�rschl�ssel
			if (rs.next()) {
		        // Ergebnis in Beitrag- Objekt umwandeln
		        Beitrag b = new Beitrag();
		        b.setId(rs.getInt("beitrag_ID"));
		        b.setErstellungsZeitpunkt(rs.getTimestamp("erstellung"));
		        b.setInhalt(rs.getString("inhalt"));
		        b.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandById(rs.getInt("pinnwand_ID")));
		        
		        //Aufruf des KommentarMappers um alle zum Beitrag geh�rigen Kommentare als ArrayList zuzuweisen

		        b.setKommentarListe(KommentarMapper.kommentarMapper().getKommentarByBeitrag(b));
		        
		        //Aufruf des LikeMappers um alle zum Beitrag geh�rigen Likes als ArrayList zuzuweisen

		        b.setLikeList(LikeMapper.likeMapper().getLikeByBeitrag(b));			       
		        
		        //BeitragObjekt zur�ckgeben
		        return b;
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
	 * getBeitr�geBetweenTwoDates gibt alle Beitr�ge zwischen einem Zeitraum aus
	 *
	 * @param datumVon String welches das Anfangsdatum der Suchanfrage bestimmt
	 * @param datumVis String welches das Enddatum der Suchanfrage bestimmt
	 *
	 * @return ArrayList mit allen Beitragobjekten in einem Zeitraum
	 */
	public ArrayList<Beitrag> getBeitraegeBetweenTwoDates(String datumVon,String datumBis) {
		// Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		ArrayList<Beitrag> beitragListe = new ArrayList<Beitrag>();
		// Versuch der Abfrage
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT * from beitrag WHERE erstellung between '"+ datumVon + "' AND '" + datumBis + "'";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// Ergebnis in Beitrag- Objekt umwandeln
				Beitrag b = new Beitrag();
				b.setId(rs.getInt("beitrag_ID"));
				b.setErstellungsZeitpunkt(rs.getDate("erstellung"));
				b.setInhalt(rs.getString("inhalt"));
				b.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandById(
						rs.getInt("pinnwand_ID")));
				b.setLikeList(LikeMapper.likeMapper().getLikeByBeitrag(b));
				b.setKommentarListe(KommentarMapper.kommentarMapper()
						.getKommentarByBeitrag(b));
				beitragListe.add(b);
			}
			return beitragListe;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	/**
	 * Gibt alle Beitr�ge zwischen einem Zeitraum aus
	 * 
	 * @param	datumVon String welches das Anfangsdatum der Suchanfrage bestimmt
	 * @param	datumVis String welches das Enddatum der Suchanfrage bestimmt
	 * @param	pinnwandId int verwei�t auf eine spezifische Pinnwand
	 * 
	 * @return	ArrayList mit allen Beitragobjekten in einem Zeitraum
	 */
	public ArrayList<Beitrag> getBeitraegeBetweenTwoDates (String datumVon, String datumBis, int pinnwandId){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		ArrayList <Beitrag> beitragListe= new ArrayList<Beitrag>();
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			String sql = "SELECT * from beitrag WHERE pinnwand_ID =" + pinnwandId + " AND erstellung between '" + datumVon + "' AND '" + datumBis + "'";
			ResultSet rs = stmt.executeQuery
					(sql);
			
			while (rs.next()) {
				// Ergebnis in Beitrag- Objekt umwandeln
		        Beitrag b = new Beitrag();
		        b.setId(rs.getInt("beitrag_ID"));
		        b.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        b.setInhalt(rs.getString("inhalt"));
		        b.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandById(rs.getInt("pinnwand_ID")));
				b.setLikeList(LikeMapper.likeMapper().getLikeByBeitrag(b));
				b.setKommentarListe(KommentarMapper.kommentarMapper().getKommentarByBeitrag(b));
		        beitragListe.add(b);
			}
			return beitragListe;		
		}
		   catch (SQLException e) {
	    		e.printStackTrace();
	    		return null;
		    }				
	}
	
	 
	 /**
	 * @see		getBeittragByPinnwand(int id): Sucht alle Beitr�ge die zu einer Pinnwand geh�ren
	 * @param 	Pinnwand Id
	 * @return	ArrayList mit Beitragobjekten
	 */
	 public ArrayList<Beitrag> getBeitragByPinnwand(int id){
		
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		ArrayList <Beitrag> beitragListe= new ArrayList<Beitrag>();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Beitrag
			ResultSet rs = stmt.executeQuery("SELECT * FROM beitrag WHERE pinnwand_ID="+id);

			while (rs.next()) {
		        // Ergebnis in Beitrag- Objekt umwandeln
		        Beitrag b = new Beitrag();
		        b.setId(rs.getInt("beitrag_ID"));
		        b.setErstellungsZeitpunkt(rs.getTimestamp("erstellung"));
		        b.setInhalt((rs.getString("inhalt")));
		        b.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandById(rs.getInt("pinnwand_ID")));
		        
		        //Aufruf des KommentarMappers um alle zum Beitrag geh�rigen Kommentare als ArrayList zuzuweisen

		        b.setKommentarListe(KommentarMapper.kommentarMapper().getKommentarByBeitrag(b));

		        
		        //Aufruf des LikeMappers um alle zum Beitrag geh�rigen Likes als ArrayList zuzuweisen

		        b.setLikeList(LikeMapper.likeMapper().getLikeByBeitrag(b));			       

		        
		        //BeitragObjekte der ArrayList hinzuf�gen
		        beitragListe.add(b);
		      }
			return beitragListe;
		}
		
	    catch (SQLException e) {
	    		e.printStackTrace();
	    }
	//Falls keines gefunden leere Liste
	return beitragListe;
	}
	 
	 
	/**
	* @see		getAllBeitrag: Sucht alle Beitr�ge die zu einer Pinnwand geh�ren
	* @param 	Pinnwand Id
	* @return 	ArrayList mit Beitragobjekten
	*/
	public ArrayList<Beitrag> getAllBeitrag(){
			
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		ArrayList <Beitrag> beitragListe= new ArrayList<Beitrag>();
			
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Beitrag
			ResultSet rs = stmt.executeQuery("SELECT * FROM beitrag");

			while (rs.next()) {
				// Ergebnis in Beitrag- Objekt umwandeln
			    Beitrag b = new Beitrag();
			    b.setId(rs.getInt("beitrag_ID"));
		        b.setErstellungsZeitpunkt(rs.getTimestamp("erstellung"));
			    b.setInhalt((rs.getString("inhalt")));
			    b.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandById(rs.getInt("pinnwand_ID")));
			    b.setNutzer(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("pinnwand_ID")));
			    
			    //Aufruf des KommentarMappers um alle zum Beitrag geh�rigen Kommentare als ArrayList zuzuweisen
			    b.setKommentarListe(KommentarMapper.kommentarMapper().getKommentarByBeitrag(b));
			        
			    //Aufruf des LikeMappers um alle zum Beitrag geh�rigen Likes als ArrayList zuzuweisen
			    b.setLikeList(LikeMapper.likeMapper().getLikeByBeitrag(b));			       	       

			    //BeitragObjekte der ArrayList hinzuf�gen
			    beitragListe.add(b);
			}
			return beitragListe;
		}
			
		catch (SQLException e) {
		    	e.printStackTrace();
		}
	//Falls keines gefunden leere Liste
	return beitragListe;
	}
	 
	 
	 /**
	 * @see		countBeitraege(): Z�hlt alle Beitr�ge
	 * @param 	-
	 * @return 	Anzahl der Beitr�ge als int
	 */
	 public int countBeitraege(){
		
		int count = -1;
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Beitrag
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM beitrag");

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
	
	 
	 /**
	  * @see 	insertBeitrag(Beitrag b): Speichert Beitragobjekt in DB
	  * @param 	Beitragobjekt
	  * @return Das gespeicherte Beitragobjekt
	  */ 
	 public void insertBeitrag(Beitrag b){
		//Aufbau der DBVerbindung
		
		Connection con = DBConnection.connection();
		
						
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();

	      /**
	       * Zun�chst schauen wir nach, welches der momentan h�chste
	       * Prim�rschl�sselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(beitrag_ID) AS maxid "
	          + "FROM beitrag ");

	      // Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	    	  	//b erh�lt um 1 h�here ID als das maximale Element in der Tabelle
		        b.setId(rs.getInt("maxid") + 1);
	
		        stmt = con.createStatement();
	
		        // Jetzt erst erfolgt die tats�chliche Einf�geoperation
		        stmt.executeUpdate("INSERT INTO beitrag (beitrag_ID, inhalt, pinnwand_ID) "
		            + "VALUES (" + b.getId() + ",'"  + b.getInhalt() + "','" + b.getPinnwand().getId() +"')"
		            );
	      }
	      
	    }
		
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	}

	
	/**
	* @see 		deleteBeitrag(Beitrag b): L�scht Beitrag aus der Datenbank
	* @param 	Beitragobjekt
	* @return 		-
	*/ 
	 public void deleteBeitrag(Beitrag b){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
	    try {
	      Statement stmt = con.createStatement();
	      //L�sche Beitrag mit gleicher ID aus Tabelle
	      stmt.executeUpdate("DELETE FROM beitrag WHERE beitrag_ID=" + b.getId());
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    } 
	 }
	
	 
	 /**
	  * @see	updateBeitrag(Beitrag b): Aktualisiert Beitraginformationen in der Datenbank
	  * @param	zu aktualisierendes Beitragobjekt
	  * @return	aktualisiertes Beitragobjekt
	  */ 
	 public Beitrag updateBeitrag(Beitrag b){
			//Aufbau der DBVerbindung
			Connection con = DBConnection.connection();
			
			//Versuch der Abfrage
		    try {
		      Statement stmt = con.createStatement();
		      //Aktualisieren des Inhalts
		      stmt.executeUpdate("UPDATE beitrag SET inhalt=\"" + b.getInhalt() + "\" WHERE beitrag_ID= " + b.getId());

		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Zur�ckgeben des aktuellen Beitragobjektes
		    return getBeitragById(b.getId());
		 }

	 /**
	   *  Methode welche �berpr�ft ob ein bestimmter Beitrag vom eingeloggten Nutzer stammt.
	   * @param nutzer Nutzer der momentan eingeloggt ist
	   * @param beitrag Beitrag der angezeigt werden soll
	   * @return true/false, je nach dem ob eingeloggter Nutzer Autor ist oder nicht.
	   * 
	   */
	public boolean checkAutor(Nutzer nutzer, Beitrag beitrag) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM beitrag WHERE pinnwand_ID="+ PinnwandMapper.pinnwandMapper().getPinnwandByNutzer(nutzer).getId() +" AND beitrag_ID=" + beitrag.getId());
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
