package hdm.social.media.pinnwand.client.gui;

import java.io.Serializable;
/**
 * Diese 
 *
 */
public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean loggedIn = false;
	  private String loginUrl;
	  private String logoutUrl;
	  private String emailAddress;
	  private String nickname;
	/**
	 * Methode zum Auslesen des Attributes ob ein Nutzer eingeloggt
	 */
	  public boolean isLoggedIn() {
	    return loggedIn;
	  }
	/**
	 * Methode 
	 */
	  public void setLoggedIn(boolean loggedIn) {
	    this.loggedIn = loggedIn;
	  }
	
	  public String getLoginUrl() {
	    return loginUrl;
	  }
	
	  public void setLoginUrl(String loginUrl) {
	    this.loginUrl = loginUrl;
	  }
	
	  public String getLogoutUrl() {
	    return logoutUrl;
	  }
	
	  public void setLogoutUrl(String logoutUrl) {
	    this.logoutUrl = logoutUrl;
	  }
	
	  public String getEmailAddress() {
	    return emailAddress;
	  }
	
	  public void setEmailAddress(String emailAddress) {
	    this.emailAddress = emailAddress;
	  }
	
	  public String getNickname() {
	    return nickname;
	  }
	
	  public void setNickname(String nickname) {
	    this.nickname = nickname;
	  }
}