package hdm.social.media.pinnwand.server.db;

import java.sql.*;
import java.util.ArrayList;
import hdm.social.media.pinnwand.shared.*;

/*
 * Methoden:
 * getLikeById(int id)
 * getLikeByBeitrag(int id)
 * countLikeByBeitrag(int id)
 * insertLike(Like l)
 * deleteLike(Like l)
 */

public class LikeMapper {
		 
	 // Variable die besagt ob schon LikeMapperverbindung besteht
	 private static LikeMapper likeMapper = null;
	 
	 
	 //leerer Konstruktor wegen Singleton
	 protected LikeMapper() {
	 }

	 
	 //Singleton "Konstruktor"-methode
	 public static LikeMapper likeMapper(){
		 if (likeMapper==null){
			 likeMapper= new LikeMapper();
		 }
		 return likeMapper;
	 }
	 
	 
	 /*
	 * @see 	getLikeById(int id): Sucht Like anhand von ID
	 * @param 	Like ID
	 * @return 	Like Objekt
	 */
	 public Like getLikeById(int id){
		
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Felder der Liketabelle anhand von ID
			ResultSet rs = stmt.executeQuery("SELECT * FROM `like` WHERE like_ID=" + id );
			
			//Maximal ein R�ckgabewert da Id Prim�rschl�ssel
			if (rs.next()) {
		        // Ergebnis in Like- Objekt umwandeln
		        Like l = new Like();
		        l.setId(rs.getInt("like_ID"));
		        l.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        l.setNutzer(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("nutzer_ID")));
		        //l.setBeitrag(BeitragMapper.beitragMapper().getBeitragById(rs.getInt("beitrag_ID")));
		             
		        
		        //LikeObjekt zur�ckgeben
		        return l;
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
	 * @see 	getLikeByBeitrag(int id): Sucht alle Likes die zu einem Beitrag geh�ren
	 * @param	Beitrag ID
	 * @return 	ArrayList mit Like Objekten
	 */
	 public ArrayList<Like> getLikeByBeitrag(int id){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		ArrayList <Like> likeListe= new ArrayList<Like>();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Likes zu einem Beitrag
			ResultSet rs = stmt.executeQuery("SELECT * FROM `like` WHERE beitrag_ID="+id);

			while (rs.next()) {
		        // Ergebnis in Like- Objekt umwandeln
		        Like l = new Like();
		        l.setId(rs.getInt("like_ID"));
		        l.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        l.setNutzer(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("nutzer_ID")));
		        //l.setBeitrag(BeitragMapper.beitragMapper().getBeitragById(rs.getInt("beitrag_ID")));
		             
		        
		        //LikeObjekt zu LikeListe hinzuf�gen
		        likeListe.add(l);
		       
		      }
			return likeListe;
		}
		
	    catch (SQLException e) {
	    		e.printStackTrace();
	    }
		//Falls keines gefunden leere Liste
		return likeListe;
	 }
	 
	 
	 /*
	 * @see 	countLikeByBeitrag(int id): Z�hlt alle Likes zu einem Beitrag
	 * @param 	Beitrag ID
	 * @return 	int mit Anzahl
	 */
	 public int countLikeByBeitrag(int id){

		int count = -1;
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Likes zu einem Beitrag
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM `like` WHERE beitrag_ID=" + id);

		    //Maximal ein R�ckgabewert da Id Prim�rschl�ssel
			while (rs.next()) {
		        count=rs.getInt(1);
		      }
			
		}
		
	    catch (SQLException e) {
	    		e.printStackTrace();
	    }
		
		//Falls keines gefunden R�ckgabe 0, sonst R�ckgabe -1
		return count;
	}
	 
	 
	 /*
	 * @see 	insertLike(Like l):	Speichert einen Like in der Datenbank
	 * @param	Objekt von Typ Like
	 * @return 	Gespeichertes Objekt vom Typ Like
	 */ 
	public Like insertLike(Like l){
		
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
	      ResultSet rs = stmt.executeQuery("SELECT MAX(like_ID) AS maxid "
	          + "FROM `like`");

	      // Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	    	  	//b erh�lt um 1 h�here ID als das maximale Element in der Tabelle
	    	  	maxid=rs.getInt("maxid");
		        l.setId(rs.getInt("maxid") + 1);
	
		        stmt = con.createStatement();
	
		        // Jetzt erst erfolgt die tats�chliche Einf�geoperation
		        stmt.executeUpdate("INSERT INTO `like` (like_ID, beitrag_ID, nutzer_ID) "
		            + "VALUES ('" + l.getId() + "','"  + l.getBeitrag().getId() + "','" + l.getNutzer().getId() +"')");
	      }
	    }
		
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    return getLikeById(maxid+1);
	}
	
	
	 /*
	 * @see 	deleteLike(Like l): L�scht einen Like aus der Datenbank
	 * @param 	Objekt von Typ Like
	 * @return 		-
	 */
	public void deleteLike(Like l){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
	    try {
	      Statement stmt = con.createStatement();
	      //L�sche Like mit gleicher ID aus Tabelle
	      stmt.executeUpdate("DELETE FROM `like` WHERE `nutzer_ID`=" + l.getNutzer().getId() + " AND `beitrag_ID`=" + l.getBeitrag().getId());
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    } 
	}
	
	public boolean checIfLiked(Nutzer n, Beitrag b) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM `like` WHERE nutzer_ID="+ n.getId() +" AND beitrag_ID=" + b.getId());
			 if(rs.next() == true) {
				 return false;
				
			 } else return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

}