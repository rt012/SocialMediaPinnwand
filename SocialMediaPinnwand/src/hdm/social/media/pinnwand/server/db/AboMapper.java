package hdm.social.media.pinnwand.server.db;

import java.sql.*;
import java.util.ArrayList;
import hdm.social.media.pinnwand.shared.*;

/*
 * Methoden:
 * getAboById(int id)
 * getAboByNutzer(int id)
 * insertAbo(Abo a)
 * deleteAbo(Abo a)
 */

public class AboMapper {
	
	// Variable die besagt ob schon AboMapperverbindung besteht
	private static AboMapper aboMapper = null;
		 
	//leerer Konstruktor wegen Singleton
	protected AboMapper() {
	}

	//Singleton "Konstruktor"-methode
	public static AboMapper aboMapper(){
		 if (aboMapper==null){
		 aboMapper= new AboMapper();
		}
		 return aboMapper;
	}
	
	
	/*
	* @see		getAboById(int id): Gibt Abo Objekt anhand von Id aus
	* @param 	Abonemment ID
	* @return	Abo objekt
	*/ 
	public Abo getAboById(int id){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Felder der Abotabelle anhand von ID
			ResultSet rs = stmt.executeQuery("SELECT * FROM abonnement WHERE abonnement_ID=" + id );
			
			//Maximal ein R�ckgabewert da Id Prim�rschl�ssel
			if (rs.next()) {
		        // Ergebnis in Abo- Objekt umwandeln
		        Abo a = new Abo();
		        a.setId(rs.getInt("abonnement_ID"));
		        a.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        a.setAbonnent(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("abonnent_ID")));
		        a.setLieferant(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("lieferant_ID")));
		        
		        //Abo Objekt zur�ckgeben
		        return a;
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
	* @see 		getAboBNutzer(int id): gibt komplette Liste an Abonnements zur�ck die ein Nutzer besitzt
	* @param 	Nutzer Id
	* @return 	ArrayList mit Abo objekten
	*/ 
	public ArrayList<Abo> getAboByNutzer(int id){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		ArrayList <Abo> aboListe= new ArrayList<Abo>();
		
		//Versuch der Abfrage
		try{
			Statement stmt = con.createStatement();
			//Suche alle Abonnements von einem Nutzer
			ResultSet rs = stmt.executeQuery("SELECT * FROM abonnement WHERE abonnent_ID="+id);

			while (rs.next()) {
		        // Ergebnis in Abo- Objekt umwandeln
				Abo a = new Abo();
		        a.setId(rs.getInt("abonnement_ID"));
		        a.setErstellungsZeitpunkt(rs.getDate("erstellung"));
		        a.setAbonnent(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("abonnent_ID")));
		        a.setLieferant(NutzerMapper.nutzerMapper().getNutzerById(rs.getInt("lieferant_ID")));
		             
		        
		        //LikeObjekt zu LikeListe hinzuf�gen
		        aboListe.add(a);
		       
		      }
			return aboListe;
		}
		
	    catch (SQLException e) {
	    		e.printStackTrace();
	    }
		//Falls keines gefunden leere Liste
		return aboListe;
		
	}
	
		
	/*
	* @see		insertAbo(Abo a): Speichert Abonnementobjekt in DB
	* @param	Abonnementrobjekt
	* @return 	Das gespeicherte Abonnementobjekt
	*/ 
	public Abo insertAbo(Abo a){
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
	      ResultSet rs = stmt.executeQuery("SELECT MAX(abonnement_ID) AS maxid "
	          + "FROM abonnement ");

	      // Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
		        /*
		         * c erh�lt den bisher maximalen, nun um 1 inkrementierten
		         * Prim�rschl�ssel.
		         */
	    	  	maxid=rs.getInt("maxid");
		        a.setId(rs.getInt("maxid") + 1);
	
		        stmt = con.createStatement();
	
		        // Jetzt erst erfolgt die tats�chliche Einf�geoperation
		        stmt.executeUpdate("INSERT INTO abonnement (abonnement_ID, abonnent_ID, lieferant_ID) "
		            + "VALUES (" + a.getId() + ",'" + a.getAbonnent().getId() + "','"
		            + a.getLieferant().getId() +"')");
	      	}
	    }
		
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    return getAboById(maxid+1);
	}
	
	
	/*
	* @see 		deleteAbo(Abo a): L�scht Abonnement aus der Datenbank
	* @param 	Abonnementobjekt
	* @return 		-
	*/ 
	public void deleteAbo(Abo a){
		//Aufbau der DBVerbindung
		Connection con = DBConnection.connection();
		
		//Versuch der Abfrage
	    try {
	      Statement stmt = con.createStatement();
	      //L�sche Abonnement aus Tabelle mit gleicher ID
	      stmt.executeUpdate("DELETE FROM abonnement WHERE abonnement_ID=" + a.getId());
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    } 
	}
	
}
