package hdm.social.media.pinnwand.server.db;

import java.sql.*;
import java.util.ArrayList;
import hdm.social.media.pinnwand.shared.*;

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
	 
	
	 /*
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
			
			//Maximal ein Rückgabewert da Id Primärschlüssel
			if (rs.next()) {
		        // Ergebnis in Beitrag- Objekt umwandeln
		        Beitrag b = new Beitrag();
		        b.setId(rs.getInt("beitrag_ID"));
		        b.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        b.setInhalt(rs.getString("inhalt"));
		        b.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandById(rs.getInt("pinnwand_ID")));
		        
		        //Aufruf des KommentarMappers um alle zum Beitrag gehörigen Kommentare als ArrayList zuzuweisen
		        //b.setKommentarListe(KommentarMapper.kommentarMapper().getKommentarByBeitrag(rs.getInt("beitrag_ID")));
		        
		        //Aufruf des LikeMappers um alle zum Beitrag gehörigen Likes als ArrayList zuzuweisen
		        //b.setLikeListe(LikeMapper.likeMapper().getLikeByBeitrag(rs.getInt("beitrag_ID")));			       
		        
		        //BeitragObjekt zurückgeben
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
	 
	 
	 /*
	 * @see		getBeittragByPinnwand(int id): Sucht alle Beiträge die zu einer Pinnwand gehören
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
		        b.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        b.setInhalt((rs.getString("inhalt")));
		        b.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandById(rs.getInt("pinnwand_ID")));
		        
		        //Aufruf des KommentarMappers um alle zum Beitrag gehörigen Kommentare als ArrayList zuzuweisen
		        //b.setKommentarListe(KommentarMapper.kommentarMapper().getKommentarByBeitrag(rs.getInt("beitrag_ID")));
		        
		        //Aufruf des LikeMappers um alle zum Beitrag gehörigen Likes als ArrayList zuzuweisen
		        //b.setLikeListe(LikeMapper.likeMapper().getLikesByBeitrag(rs.getInt("beitrag_ID")));			       
		        
		        //BeitragObjekte der ArrayList hinzufügen
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
	 
	 
	/*
	* @see		getAllBeitrag: Sucht alle Beiträge die zu einer Pinnwand gehören
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
			    b.setErstellungsZeitpunkt(rs.getDate("erstellung"));
			    b.setInhalt((rs.getString("inhalt")));
			    b.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandById(rs.getInt("pinnwand_ID")));
			        
			    //Aufruf des KommentarMappers um alle zum Beitrag gehörigen Kommentare als ArrayList zuzuweisen
			    b.setKommentarListe(KommentarMapper.kommentarMapper().getKommentarByBeitrag(rs.getInt("beitrag_ID")));
			        
			    //Aufruf des LikeMappers um alle zum Beitrag gehörigen Likes als ArrayList zuzuweisen
			    b.setLikeList(LikeMapper.likeMapper().getLikeByBeitrag(rs.getInt("beitrag_ID")));			       
			        
			    //BeitragObjekte der ArrayList hinzufügen
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
	 
	 
	 /*
	 * @see		countBeitraege(): Zählt alle Beiträge
	 * @param 	-
	 * @return 	Anzahl der Beiträge als int
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

		    //Maximal ein Rückgabewert da Id Primärschlüssel
			while (rs.next()) {
		        count=rs.getInt(1);
		      }
			
		}
		
	    catch (SQLException e) {
	    		e.printStackTrace();
	    }
		
		//Falls keines gefunden Rückgabe 0, sonst Rückgabe der Anzahl oder bei Fehler -1
		return count;
	} 
	
	 
	 /*
	  * @see 	insertBeitrag(Beitrag b): Speichert Beitragobjekt in DB
	  * @param 	Beitragobjekt
	  * @return Das gespeicherte Beitragobjekt
	  */ 
	 public Beitrag insertBeitrag(Beitrag b){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		int maxid = 0;
						
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();

	      /*
	       * Zunächst schauen wir nach, welches der momentan höchste
	       * Primärschlüsselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(beitrag_ID) AS maxid "
	          + "FROM beitrag ");

	      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	    	  	//b erhält um 1 höhere ID als das maximale Element in der Tabelle
	    	  	maxid=rs.getInt("maxid");
		        b.setId(rs.getInt("maxid") + 1);
	
		        stmt = con.createStatement();
	
		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("INSERT INTO beitrag (beitrag_ID, inhalt, pinnwand_ID) "
		            + "VALUES (" + b.getId() + ",'"  + b.getInhalt() + "','" + b.getPinnwand().getId() +"')");
	      }
	    }
		
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    return getBeitragById(maxid+1);
	}

	
	/*
	* @see 		deleteBeitrag(Beitrag b): Löscht Beitrag aus der Datenbank
	* @param 	Beitragobjekt
	* @return 		-
	*/ 
	 public void deleteBeitrag(Beitrag b){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
	    try {
	      Statement stmt = con.createStatement();
	      //Lösche Beitrag mit gleicher ID aus Tabelle
	      stmt.executeUpdate("DELETE FROM beitrag WHERE beitrag_ID=" + b.getId());
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    } 
	 }
	
	 
	 /*
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

		    // Zurückgeben des aktuellen Beitragobjektes
		    return getBeitragById(b.getId());
		 }

}
