package hdm.social.media.pinnwand.server.db;

import java.sql.*;
import java.util.ArrayList;

import hdm.social.media.pinnwand.shared.*;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

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
			
			//Maximal ein Rückgabewert da Id Primärschlüssel
			if (rs.next()) {
		        // Ergebnis in Like- Objekt umwandeln
		        Like l = new Like();
		        l.setId(rs.getInt("like_ID"));
		        l.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        l.setNutzer(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("nutzer_ID")));
		        l.setBeitrag(BeitragMapper.beitragMapper().getBeitragById(rs.getInt("beitrag_ID")));
		             
		        
		        //LikeObjekt zurückgeben
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
	 * @see 	getLikeByBeitrag(int id): Sucht alle Likes die zu einem Beitrag gehören
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
		        l.setBeitrag(BeitragMapper.beitragMapper().getBeitragById(rs.getInt("beitrag_ID")));
		             
		        
		        //LikeObjekt zu LikeListe hinzufügen
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
	 * @see 	countLikeByBeitrag(int id): Zählt alle Likes zu einem Beitrag
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

		    //Maximal ein Rückgabewert da Id Primärschlüssel
			while (rs.next()) {
		        count=rs.getInt(1);
		      }
			
		}
		
	    catch (SQLException e) {
	    		e.printStackTrace();
	    }
		
		//Falls keines gefunden Rückgabe 0, sonst Rückgabe -1
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
	       * Zunächst schauen wir nach, welches der momentan höchste
	       * Primärschlüsselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(like_ID) AS maxid "
	          + "FROM `like`");

	      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	    	  	//b erhält um 1 höhere ID als das maximale Element in der Tabelle
	    	  	maxid=rs.getInt("maxid");
		        l.setId(rs.getInt("maxid") + 1);
	
		        stmt = con.createStatement();
	
		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
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
	 * @see 	deleteLike(Like l): Löscht einen Like aus der Datenbank
	 * @param 	Objekt von Typ Like
	 * @return 		-
	 */
	public void deleteLike(Like l){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
	    try {
	      Statement stmt = con.createStatement();
	      //Lösche Like mit gleicher ID aus Tabelle
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
	
	public int getLikeCountByNutzer(Nutzer n) {
		Connection con = DBConnection.connection();
		int anzahl = 0;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(`like_ID`) FROM `like` WHERE `nutzer_ID` = " + n.getId());
			if(rs.next()) {
				anzahl = rs.getInt(1);
			}
			return anzahl;
		}
		catch  (SQLException e) {
			e.printStackTrace();
			
		}
		return anzahl;
	}

}