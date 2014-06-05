package hdm.social.media.pinnwand.server.db;

import java.sql.*;
import java.util.ArrayList;

import hdm.social.media.pinnwand.shared.*;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;

/*
 * Methoden:
 * getNutzerById(int id)
 * getNutzerByEmail(String email)
 * getAllNutzer()
 * countNutzer()
 * insertNutzer(Nutzer n)
 * deleteNutzer(Nutzer n)
 * updateNutzer(Nutzer n)
 */

public class NutzerMapper{
	
	 // Variable die besagt ob schon nutzerMapperverbindung besteht
	 private static NutzerMapper nutzerMapper = null;
	
	 
	 //leerer Konstruktor wegen Singleton
	 protected NutzerMapper() {
		 
	 }

	 
	 //Singleton "Konstruktor"-methode
	 public static NutzerMapper nutzerMapper(){
		 if (nutzerMapper==null){
			 nutzerMapper= new NutzerMapper();
		 }
		 return nutzerMapper;
	 }
	 
	 
	/*
	 * @see 	getNutzrById(int id): Sucht Nutzer anhand der email 
	 * @param 	String email
	 * @return 	1 Nutzerobjekt entweder mit Ergebnis oder leer 
	 */
	 public Nutzer getNutzerByEmail(String email){
		
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Felder der Nutzertabelle anhand von ID
			ResultSet rs = stmt.executeQuery("SELECT * FROM nutzer WHERE email=" + email);

		    //Maximal ein Rückgabewert da Id Primärschlüssel
			if (rs.next()) {
		        // Ergebnis in Nutzer- Objekt umwandeln
		        Nutzer n = new Nutzer();
		        n.setId(rs.getInt("nutzer_ID"));
		        n.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        n.setVorname(rs.getString("vorname"));
		        n.setName(rs.getString("name"));
		        n.setEmail(rs.getString("email"));
		        n.setNickname(rs.getString("nickname"));
		        /*
		        //Verweis auf PinnwandMapper um zugehörige Pinnwand herauszufinden
		        n.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandByNutzer(rs.getInt("nutzer_ID")));
		        //Verweis auf AbonnementMapper um zugehörige Abos herauszufinden
		        n.setAbonnementListe(AboMapper.aboMapper().getAboByNutzer(rs.getInt("nutzer_ID")));
		        */	
		        return n;
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
	 * @see 	getNutzrById(int id): Sucht Nutzer anhand der id 
	 * @param 	int id
	 * @return 	1 Nutzerobjekt entweder mit Ergebnis oder leer 
	 */
	 public Nutzer getNutzerById(int id){
			
			//Aufbau der DBVerbindung
			Connection con = DBConnection.connection();
			
			//Versuch der Abfrage
			try{
				Statement stmt = con.createStatement();
				//Suche alle Felder der Nutzertabelle anhand von ID
				ResultSet rs = stmt.executeQuery("SELECT * FROM nutzer WHERE nutzer_ID=" + id );

			    //Maximal ein Rückgabewert da Id Primärschlüssel
				if (rs.next()) {
			        // Ergebnis in Nutzer- Objekt umwandeln
			        Nutzer n = new Nutzer();
			        n.setId(rs.getInt("nutzer_ID"));
			        n.setErstellungsZeitpunkt(rs.getDate("erstellung"));
			        n.setVorname(rs.getString("vorname"));
			        n.setName(rs.getString("name"));
			        n.setEmail(rs.getString("email"));
			        n.setNickname(rs.getString("nickname"));
			        /*
			        //Verweis auf PinnwandMapper um zugehörige Pinnwand herauszufinden
			        n.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandByNutzer(rs.getInt("nutzer_ID")));
			        //Verweis auf AbonnementMapper um zugehörige Abos herauszufinden
			        n.setAbonnementListe(AboMapper.aboMapper().getAboByNutzer(rs.getInt("nutzer_ID")));
			        */	
			        return n;
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
	 * @see 	getAllNutzer(): Sucht alle Nutzer
	 * @param 	-
	 * @return 	ArrayList mit Nutzerobjekten
	 */
	 public ArrayList<Nutzer> getAllNutzer(){
		
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		ArrayList <Nutzer> nutzerListe= new ArrayList<Nutzer>();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Nutzer
			ResultSet rs = stmt.executeQuery("SELECT * FROM nutzer");

			while (rs.next()) {
				// Ergebnis in Nutzer- Objekt umwandeln
		        Nutzer n = new Nutzer();
		        n.setId(rs.getInt("nutzer_ID"));
		        n.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        n.setVorname(rs.getString("vorname"));
		        n.setName(rs.getString("name"));
		        n.setEmail(rs.getString("email"));
		        n.setNickname(rs.getString("nickname"));
		        /*
		        //Verweis auf PinnwandMapper um zugehörige Pinnwand herauszufinden
		        n.setPinnwand(PinnwandMapper.pinnwandMapper().getPinnwandByNutzer(rs.getInt("nutzer_ID")));
		        //Verweis auf AbonnementMapper um zugehörige Abos herauszufinden
		        n.setAbonnementListe(AboMapper.aboMapper().getAboByNutzer(rs.getInt("nutzer_ID")));	
		        */
		        
		        //NutzerObjekte der ArrayList hinzufügen
		        nutzerListe.add(n);
		      }
			return nutzerListe;
		}
		
	    catch (SQLException e) {
	    		e.printStackTrace();
	    }
	//Falls keines gefunden leeres Objekt
	return nutzerListe;
	}
	
	
	 /*
	 * @see		countNutzer(): Zählt alle angemeldeten Nutzer
	 * @param 	-
	 * @return 	int mit Anzahl
	 */
	 public int countNutzer(){
		 int count = -1;
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Nutzer
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM nutzer");

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
	* @see		insertNutzer(Nutzer n): Speichert Nutzerobjekt in DB
	* @param 	Nutzerobjekt
	* @return 	Das gespeicherte Nutzerobjekt
	*/ 
	public Nutzer insertNutzer(Nutzer n){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		int maxid = 0;
						
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();

	      /*
	       * ZunÃ¤chst schauen wir nach, welches der momentan höchste
	       * Primärschlüsselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(nutzer_ID) AS maxid "
	          + "FROM nutzer ");

	      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
		        //n erhält um 1 höhere ID als das maximale Element in der Tabelle
	    	  	maxid=rs.getInt("maxid");
		        n.setId(rs.getInt("maxid") + 1);
	
		        stmt = con.createStatement();
		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("INSERT INTO nutzer (nutzer_ID, vorname, name, email, nickname) "
		            + "VALUES (" + n.getId() + ",'" + n.getVorname() + "','"
		            + n.getName() + "','" + n.getEmail() + "','" + n.getNickname()+"')");
	      	}
	    }
		
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
		
		Pinnwand p= new Pinnwand();
		p.setNutzer(getNutzerById(maxid+1));
		PinnwandMapper.pinnwandMapper().insertPinnwand(p);
				
	    return getNutzerById(maxid+1);
	}
	
	
	/*
	* @see 		deleteNutzer(Nutzer n): Löscht Nutzer aus der Datenbank
	* @param 	Nutzerobjekt
	* @return 	-
	*/ 
	public void deleteNutzer(Nutzer n){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
	    try {
	      Statement stmt = con.createStatement();
	      //Lösche Nutzer aus Tabelle mit gleicher ID
	      stmt.executeUpdate("DELETE FROM nutzer WHERE nutzer_ID=" + n.getId());
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    } 
	}
	
	
	/*
	* @see		updateNutzer(Nutzwr n): Aktualisiert Nutzerinformationen in der Datenbank
	* @param	zu aktualisierendes Nutzerobjekt
	* @return 	aktualisiertes Nutzerobjekt
	*/ 
	public Nutzer updateNutzer(Nutzer n){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
	    try {
	      Statement stmt = con.createStatement();
	      //Aktualisieren des Vornamens, Nachnamens, der Email und des Nicknames
	      stmt.executeUpdate("UPDATE nutzer " + "SET vorname= \"" 
	          + n.getVorname() + "\", " + "name=\"" + n.getName() + "\", " + "email= \"" + n.getEmail() + 
	          "\", " + "nickname= \""+ n.getNickname() +  "\"" + "WHERE nutzer_ID=" + n.getId());

	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    // Zurückgeben des aktuellen Nutzerobjektes
	    return getNutzerById(n.getId());
	}
	 
}
