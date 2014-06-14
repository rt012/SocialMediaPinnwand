package hdm.social.media.pinnwand.server.db;

import java.sql.*;

import com.google.appengine.api.rdbms.AppEngineDriver;

public class DBConnection {
	
	/**
	 * Singleton Variable damit es nur eine DB Verbindung gibt
	 */
	private static Connection con = null;
	
	/**
	 * Geschuetzter Konstruktor zum verhindern einer Instanziierung via new
	 */
	protected DBConnection(){
	}
	
	

	//Datenbank URL
	//CloudSQL
	
	private static String url2 = "jdbc:google:rdbms://it-projekt-2014:it-projekt/it_projekt?user=root";
		

	
	/**
	 * Ersatz fÃ¼r Konstruktor zum erstellen einer Verbindung
	 * @return
	 */
	public static Connection connection(){
		/** 
		 * Wenn es bisher keine Conncetion zur DB gab, ... 
		 */
		if ( con == null ) {
			try {
				//Installieren des geeigneten DB-Treibers
				//im Moment noch standard sql-Treiber, später AppEngine Treiber
				DriverManager.registerDriver(new AppEngineDriver());
				con = DriverManager.getConnection(url2);
				System.out.println("Verbindung hergestellt");
			} 
			// Wenn die Verbindung scheitert
			catch (SQLException e1) {
				con = null;
				System.out.println("Verbindung fehlgeschlagen!");
				e1.printStackTrace();
	
			}
		}
		
		// ZurÃ¯Â¿Â½ckgegeben der Verbindung
		return con;
	}
}
