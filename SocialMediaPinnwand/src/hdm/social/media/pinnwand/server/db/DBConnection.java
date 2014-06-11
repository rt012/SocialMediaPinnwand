package hdm.social.media.pinnwand.server.db;

import java.sql.*;

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
	private static String url = "jdbc:mysql://localhost/it-projekt";


	
	/**
	 * Nutzerdaten f�r DB-login
	 */
	private static String name="root";
	private static String password="";
	
	/**
	 * Ersatz f�r Konstruktor zum erstellen einer Verbindung
	 * @return
	 */
	public static Connection connection()  {
		/** 
		 * Wenn es bisher keine Conncetion zur DB gab, ... 
		 */
		if ( con == null ) {
			try {
				//Installieren des geeigneten DB-Treibers
				//im Moment noch standard sql-Treiber, sp�ter AppEngine Treiber
				DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
				
				con = DriverManager.getConnection(url, name, password);
				System.out.println("Verbindung hergestellt");
			} 
			// Wenn die Verbindung scheitert
			catch (SQLException e1) {
				con = null;
				System.out.println("Verbindung fehlgeschlagen!");
				e1.printStackTrace();
	
			}
		}
		
		// Zur�ckgegeben der Verbindung
		return con;
	}
}
