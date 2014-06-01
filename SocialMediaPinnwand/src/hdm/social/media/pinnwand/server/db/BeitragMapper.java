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
			
			//Maximal ein Rï¿½ckgabewert da Id Primï¿½rschlï¿½ssel
			if (rs.next()) {
		        // Ergebnis in Beitrag- Objekt umwandeln
		        Beitrag b = new Beitrag();
		        b.setId(rs.getInt("beitrag_ID"));
		        b.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        b.setInhalt(rs.getString("inhalt"));
		        b.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandById(rs.getInt("pinnwand_ID")));
		        
		        //Aufruf des KommentarMappers um alle zum Beitrag gehï¿½rigen Kommentare als ArrayList zuzuweisen
		        b.setKommentarListe(KommentarMapper.kommentarMapper().getKommentarByBeitrag(rs.getInt("beitrag_ID")));
		        
		        //Aufruf des LikeMappers um alle zum Beitrag gehï¿½rigen Likes als ArrayList zuzuweisen
		        b.setLikeList(LikeMapper.likeMapper().getLikeByBeitrag(rs.getInt("beitrag_ID")));			       
		        
		        //BeitragObjekt zurï¿½ckgeben
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
	 * @see		getBeittragByPinnwand(int id): Sucht alle Beitrï¿½ge die zu einer Pinnwand gehï¿½ren
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
		        
		        //Aufruf des KommentarMappers um alle zum Beitrag gehï¿½rigen Kommentare als ArrayList zuzuweisen
		        b.setKommentarListe(KommentarMapper.kommentarMapper().getKommentarByBeitrag(rs.getInt("beitrag_ID")));
		        
		        //Aufruf des LikeMappers um alle zum Beitrag gehï¿½rigen Likes als ArrayList zuzuweisen
		        b.setLikeList(LikeMapper.likeMapper().getLikeByBeitrag(rs.getInt("beitrag_ID")));			       
		        
		        //BeitragObjekte der ArrayList hinzufï¿½gen
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
	* @see		getAllBeitrag: Sucht alle Beitrï¿½ge die zu einer Pinnwand gehï¿½ren
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
			    b.setNutzer(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("pinnwand_ID")));
			    //Aufruf des KommentarMappers um alle zum Beitrag gehï¿½rigen Kommentare als ArrayList zuzuweisen
			    b.setKommentarListe(KommentarMapper.kommentarMapper().getKommentarByBeitrag(rs.getInt("beitrag_ID")));
			        
			    //Aufruf des LikeMappers um alle zum Beitrag gehï¿½rigen Likes als ArrayList zuzuweisen
			    b.setLikeList(LikeMapper.likeMapper().getLikeByBeitrag(rs.getInt("beitrag_ID")));			       
			        
			    //BeitragObjekte der ArrayList hinzufï¿½gen
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
	 * @see		countBeitraege(): Zï¿½hlt alle Beitrï¿½ge
	 * @param 	-
	 * @return 	Anzahl der Beitrï¿½ge als int
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
	
	 
	 /*
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

	      /*
	       * Zunï¿½chst schauen wir nach, welches der momentan hï¿½chste
	       * Primï¿½rschlï¿½sselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(beitrag_ID) AS maxid "
	          + "FROM beitrag ");

	      // Wenn wir etwas zurï¿½ckerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	    	  	//b erhï¿½lt um 1 hï¿½here ID als das maximale Element in der Tabelle
		        b.setId(rs.getInt("maxid") + 1);
	
		        stmt = con.createStatement();
	
		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("INSERT INTO beitrag (beitrag_ID, inhalt, pinnwand_ID) "
		            + "VALUES (" + b.getId() + ",'"  + b.getInhalt() + "','" + b.getPinnwand().getId() +"')"
		            );
	      }
	      
	    }
		
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	}

	
	/*
	* @see 		deleteBeitrag(Beitrag b): Lï¿½scht Beitrag aus der Datenbank
	* @param 	Beitragobjekt
	* @return 		-
	*/ 
	 public void deleteBeitrag(Beitrag b){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
	    try {
	      Statement stmt = con.createStatement();
	      //Lï¿½sche Beitrag mit gleicher ID aus Tabelle
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

		    // Zurï¿½ckgeben des aktuellen Beitragobjektes
		    return getBeitragById(b.getId());
		 }

}
