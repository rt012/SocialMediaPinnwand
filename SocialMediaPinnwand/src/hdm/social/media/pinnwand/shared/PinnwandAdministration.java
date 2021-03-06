package hdm.social.media.pinnwand.shared;

import hdm.social.media.pinnwand.client.gui.LoginInfo;
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
* <p>
* Synchrone Schnittstelle für eine RPC-fähige Klasse zur Verwaltung von Pinnwänden.
* </p>
* <p>
* <code>@RemoteServiceRelativePath("pinnwandadministration")</code> ist bei der
* Adressierung des aus der zugehörigen Impl-Klasse entstehenden
* Servlet-Kompilats behilflich. Es gibt im Wesentlichen einen Teil der URL des
* Servlets an.
* </p>
*
* @author Fuchs, Schmidt
*/

@RemoteServiceRelativePath("pinnwandadministration")
public interface PinnwandAdministration extends RemoteService{

  /**
   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
   * RPC zusätzlich zum No Argument Constructor der implementierenden Klasse
   * {@link PinnwandAdministrationImpl} notwendig. Bitte diese Methode direkt nach der
   * Instantiierung aufrufen.
   *
   * @throws IllegalArgumentException
   */
  public void init() throws IllegalArgumentException;
 
  /**
   * Einen Nutzer anlegen.
   *
   * @return Ein fertiges Nutzer-Objekt.
   * @throws IllegalArgumentException
   */
  public Nutzer createNutzer(Nutzer n) throws IllegalArgumentException;
 
  /**
   * Einen Nutzer aktualisieren.
   *
   * @param n Nutzer
   * @return Ein aktualisiertes Nutzer-Objekt.
   * @throws IllegalArgumentException
   */
  public Nutzer updateNutzer(Nutzer n) throws IllegalArgumentException;
  
  /**
   * Einen Nutzer löschen.
   *
   * @param n Nutzer
   * @throws IllegalArgumentException
   */ 
  public void deleteNutzer(Nutzer n) throws IllegalArgumentException;
 
  /**
   * Einen Nutzer speichern.
   *
   * @param n Nutzer
   * @throws IllegalArgumentException
   */
  public void saveNutzer(Nutzer n) throws IllegalArgumentException;
 
  /**
   * Gebe alle Nutzer aus
   * 
   * @return ArrayList<Nutzer>
   * @author Eric Schmidt
   */
  public ArrayList<Nutzer> getAllNutzer() throws IllegalArgumentException;
  
  /**
   * Eine Pinnwand speichern.
   *
   * @param n Nutzer
   * @throws IllegalArgumentException
   */
  public void savePinnwand(Pinnwand p) throws IllegalArgumentException;
 
  /**
   * Eine Pinnwand durch dessen ID finden.
   *
   * @param id int
   * @return Pinnwand
   * @throws IllegalArgumentException
   */
  public Pinnwand getPinnwandById(int id) throws IllegalArgumentException;
 
  /**
   * Einen Beitrag anlegen.
   *
   * @param b Beitrag
   * @return Beitrag
   * @throws IllegalArgumentException
   */
  public Beitrag createBeitrag(Beitrag b) throws IllegalArgumentException;
 
  /**
   * Einen Beitrag speichern.
   *
   * @param b Beitrag
   * @throws IllegalArgumentException
   */
  public void saveBeitrag(Beitrag b) throws IllegalArgumentException;
 
  /**
   * Einen Beitrag aktualisieren.
   *
   * @param b Beitrag
   * @return Beitrag
   * @throws IllegalArgumentException
   */
  public Beitrag updateBeitrag(Beitrag b) throws IllegalArgumentException;
 
  /**
   * Einen Beitrag löschen.
   *
   * @param b Beitrag
   * @throws IllegalArgumentException
   */
  public void deleteBeitrag(Beitrag b) throws IllegalArgumentException;
 
  /**
   * Gebe alle Beitäge aus.
   *
   * @return ArrayList<Beitrag>
   * @throws IllegalArgumentException
   */
  public ArrayList<Beitrag> findAllBeitraege() throws IllegalArgumentException;
 
  /**
   * Gebe alle Nutzer aus.
   *
   * @return ArrayList<Nutzer>
   * @throws IllegalArgumentException
   */
  public ArrayList<Nutzer> findAllNutzer() throws IllegalArgumentException;
 
  /**
   * Einen Kommentar erstellen.
   *
   * @param k Kommentar
   * @return Kommentar
   * @throws IllegalArgumentException
   */
  public Kommentar createKommentar(Kommentar k) throws IllegalArgumentException;
 
  /**
   * Einen Kommentar speichern.
   *
   * @param k Kommentar
   * @throws IllegalArgumentException
   */
  public void saveKommentar(Kommentar k) throws IllegalArgumentException;
 
  /**
   * Einen Kommentar aktualisieren.
   *
   * @param k Kommentar
   * @return Kommentar
   * @throws IllegalArgumentException
   */
  public Kommentar updateKommentar(Kommentar k) throws IllegalArgumentException;
 
  /**
   * Einen Kommentar löschen.
   *
   * @param k Kommentar
   * @throws IllegalArgumentException
   */
  public void deleteKommentar(Kommentar k) throws IllegalArgumentException;
 
  /**
   * Gebe alle Kommentare zu einem Beitrag aus.
   *
   * @param b Beitrag
   * @return ArrayList<Kommentar>
   * @throws IllegalArgumentException
   */
  public ArrayList<Kommentar> getKommentarByBeitrag(Beitrag b) throws IllegalArgumentException;
 
  /**
   * Einen Like erstellen.
   *
   * @param l Like
   * @return Like
   * @throws IllegalArgumentException
   */
  public Like createLike(Like l) throws IllegalArgumentException;
 
  /**
   * Einen Like speichern.
   *
   * @param l Like
   * @throws IllegalArgumentException
   */
  public void saveLike(Like l) throws IllegalArgumentException;
 
  /**
   * Einen Like löschen.
   *
   * @param l Like
   * @throws IllegalArgumentException
   */
  public void deleteLike(Like l) throws IllegalArgumentException;
 
  /**
   * Gebe alle Likes je Beitrag aus.
   *
   * @param b Beitrag
   * @return ArrayList<Like>
   * @throws IllegalArgumentException
   */
  public ArrayList<Like> getLikeByBeitrag(Beitrag b) throws IllegalArgumentException;
 
  /**
   * Pr�fen ob Nutzer einen Beitrag geliked hat
   * @param n Nutzer
   * @param b Beitrag 
   * @return true / false 
   * @throws IllegalArgumentException
   */
  
  public boolean checkIfliked(Nutzer n, Beitrag b) throws IllegalArgumentException;
  /**
   * Einen Abonnement Beziehung erstellen.
   *
   * @param abonnement Nutzer
   * @param lieferant Nutzer
   * @return Abo
   * @throws IllegalArgumentException
   */
  public Abo createAbo(Nutzer abonnement, Nutzer lieferant) throws IllegalArgumentException;
 
  /**
   * Einen Abonnement speichern.
   *
   * @param a Abo
   * @throws IllegalArgumentException
   */
  public void saveAbo(Abo a) throws IllegalArgumentException;
 
  /**
   * Einen Abonnement löschen.
   *
   * @param a Abo
   * @throws IllegalArgumentException
   */
  public void deleteAbo(Abo a) throws IllegalArgumentException;
  
  public Nutzer getNutzerById(int i) throws IllegalArgumentException;

  public int countLikeByBeitrag(int id)throws IllegalArgumentException;

  /**
   * Gibt s�mtliche Abos eines Nutzers aus
   * 
   * @param id der Nutzers
   * @return ArrayList der Abos des Nutzers
   * @throws IllegalArgumentException
   */
  public ArrayList<Abo> getAboByNutzer(int id) throws IllegalArgumentException;
  
  /**
   * Login der Applikation
   * 
   * @param requestUri
   * @return
   * @author Eric Schmidt
   */
  public LoginInfo login(String requestUri) throws IllegalArgumentException;
  
  public Nutzer getNutzerByEmail(String email) throws IllegalArgumentException;
  
  public ArrayList<Beitrag> getAllBeitragByNutzer(Nutzer n) throws IllegalArgumentException;
  
  public ArrayList<Beitrag> getAllBeitragByAktuellerNutzer(Nutzer n) throws IllegalArgumentException;

  public Pinnwand getPinnwandByNutzer(Nutzer n) throws IllegalArgumentException;

  public boolean checkIfLiked(Nutzer n, Beitrag b) throws IllegalArgumentException;

  /**
   *  Methode welche �berpr�ft ob ein bestimmter Beitrag vom eingeloggten Nutzer stammt.
   * @param nutzer Nutzer der momentan eingeloggt ist
   * @param beitrag Beitrag der angezeigt werden soll
   * @return true/false, je nach dem ob eingeloggter Nutzer Autor ist oder nicht.
   */
  boolean checkAutor(Nutzer nutzer, Beitrag beitrag) throws IllegalArgumentException;

  /**
   *  Methode welche �berpr�ft ob ein bestimmter Kommentar vom eingeloggten Nutzer stammt.
   * @param nutzer Nutzer der momentan eingeloggt ist
   * @param kommentar Kommentar der angezeigt werden soll
   * @return true/false, je nach dem ob eingeloggter Nutzer Autor ist oder nicht.
   */
  boolean checkAutorKommentar(Nutzer nutzer, Kommentar kommentar);

}

