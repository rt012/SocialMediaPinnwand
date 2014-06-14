package hdm.social.media.pinnwand.server.db;

import java.sql.*;

import hdm.social.media.pinnwand.shared.*;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;

/*
 * Methoden:
 * getPinnwandById(int id)
 * getPinnwandByNutzer()
 * insertPinnwand(Pinnwand p)
 */

public class PinnwandMapper {
	
	// Variable die besagt ob schon nutzerMapperverbindung besteht
	private static PinnwandMapper pinnwandMapper = null;

	
	//leerer Konstruktor wegen Singleton
	protected PinnwandMapper() {
	}

	
	//Singleton "Konstruktor"-methode
	 public static PinnwandMapper pinnwandMapper(){
		if (pinnwandMapper==null){
			 pinnwandMapper= new PinnwandMapper();
		}
		 return pinnwandMapper;
		}
	
	 
	/**
	* @see 		getPinnwandById(int id): Sucht Pinnwand anhand der id 
	* @param 	int id
	* @return 	1 Pinnwandobjekt entweder mit Ergebnis oder leer 
	*/
	public Pinnwand getPinnwandById(int id){
			
	//Aufbau der DBVerbindung
	Connection con = DBConnection.connection();
			
	//Versuch der Abfrage
	try{
		Statement stmt = con.createStatement();
		//Suche alle Felder der Pinnwandtabelle anhand von ID
		ResultSet rs = stmt.executeQuery("SELECT * FROM pinnwand " + "WHERE pinnwand_ID=" + id );

		 //Maximal ein Rückgabewert da Id Primärschlüssel
		if (rs.next()) {
			// Ergebnis in Pinnwandobjekt umwandeln
			Pinnwand p = new Pinnwand();
			p.setId(rs.getInt("pinnwand_ID"));
			p.setErstellungsZeitpunkt(rs.getDate("erstellung"));
			p.setNutzer(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("nutzer_ID")));
			return p;
			}
		}
			
		catch (SQLException e) {
		   e.printStackTrace();
		   return null;
		  }
	//Falls keines gefunden leeres Objekt
	return null;
	}
	
	
	/**
	* @see 		getPinnwandByNutzer(int id): Sucht zugehörige Pinnwand zu einem Nutzer
	* @param	Nutzer id
	* @return 	Pinnwandobjekt welches zum Nutzer gehört
	*/
	public Pinnwand getPinnwandByNutzer(Nutzer nutzer){
			
	//Aufbau der DBVerbindung
	Connection con = DBConnection.connection();
			
	//Versuch der Abfrage
	try{
		Statement stmt = con.createStatement();
		//Suche alle Felder der Pinnwandtabelle anhand von ID
		ResultSet rs = stmt.executeQuery("SELECT * FROM pinnwand " + "WHERE nutzer_ID=" + nutzer.getId());

		 //Maximal ein Rückgabewert da Id Primärschlüssel
		if (rs.next()) {
			// Ergebnis in Pinnwandobjekt umwandeln
			Pinnwand p = new Pinnwand();
			p.setId(rs.getInt("pinnwand_ID"));
			p.setErstellungsZeitpunkt(rs.getDate("erstellung"));
			p.setNutzer(nutzer);   	
			return p;
			}
		}
			
		catch (SQLException e) {
		   e.printStackTrace();
		   return null;
		  }
	//Falls keines gefunden leeres Objekt
	return null;
	}
	
	
	/**
	* @see 		insertPinnwand(Pinnwand p): Speichert Pinnwandobjekt in DB
	* @param 	Pinnwandobjekt
	* @return 	Das gespeicherte Pinnwandobjekt
	*/ 
	public Pinnwand insertPinnwand(Pinnwand p){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		int maxid=0;				
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			
	      /**
	       * Zunächst schauen wir nach, welches der momentan höchste
	       * Primärschlüsselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(pinnwand_ID) AS maxid "
	          + "FROM pinnwand ");

	      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
		        /**
		         * p erhält den bisher maximalen, nun um 1 inkrementierten
		         * Primärschlüssel.
		         */
	    	  	maxid=rs.getInt("maxid");
		        p.setId(rs.getInt("maxid") + 1);
	
		        stmt = con.createStatement();
	
		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("INSERT INTO pinnwand (pinnwand_ID, nutzer_ID) "
		            + "VALUES (" + p.getId() + ",'" + p.getNutzer().getId() + "')");
	      	}
	    }
		
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    return getPinnwandById(maxid+1);
	}
	
}
