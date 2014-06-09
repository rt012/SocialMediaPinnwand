package hdm.social.media.pinnwand.client;

import hdm.social.media.pinnwand.client.gui.LoginCustomDialog;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Diese Klasse Verwaltet den Eingeloggten Nutzer
 * 
 * @author Eric Schmidt
 *
 */
public class NutzerVerwaltung {
	/**
	 * Erstellt einen Remote Service Proxy um mit dem Server-Seitigem Service zu kommunizieren.
	 */
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);	
	/**
	 * Verweis auf EntryPoint
	 */
	private EntryPoint entryPointKlasse = null;
	
	/**
	 * Klassenvariable für das Object LoginInfo welches die LogIn Informationen des Nutzers enthält
	 *
	 */
	private LoginInfo loginInfo = null;
	
	/**
	 * Konstruktor der Klasse NutzerVerwaltung
	 * 
	 * @param loginInfo
	 */
	public NutzerVerwaltung(LoginInfo loginInfo, EntryPoint entryPointKlasse){
		this.loginInfo = loginInfo;
		this.entryPointKlasse = entryPointKlasse;
	}
	
	/**
	 * Prüft anhand der Email-Adresse ob der angemeldete Nutzer bereits in der Datenbank ist
	 * 
	 * @author Eric Schmidt
	 */
	public void nutzerInDatenbank(final LoginInfo googleNutzer){
		PinnwandAdministration.getNutzerByEmail(googleNutzer.getEmailAddress(), new AsyncCallback<Nutzer>() {
			public void onFailure
			 (Throwable caught) {
			 // TODO: Do something with errors.
			 }

			@Override
			public void onSuccess(Nutzer result) {
				if (result != null && result.getEmail() == googleNutzer.getEmailAddress()){
					if (entryPointKlasse instanceof SocialMediaPinnwand){
						((SocialMediaPinnwand) entryPointKlasse).setAktuellerNutzer(result);
						((SocialMediaPinnwand) entryPointKlasse).loadSocialMediaPinnwand();
					}else{
						((ReportGenerator) entryPointKlasse).loadReportGenerator();
					}
				}

				else{
					createNutzer(googleNutzer);
				}

			}
		});
	}
	
	/**
	 * Legt den Nutzer in der Datenbank an
	 * 
	 * @author Eric Schmidt 
	 */
	private void createNutzer(final LoginInfo googleNutzer){
		final Nutzer nutzer = new Nutzer();
		nutzer.setEmail(googleNutzer.getEmailAddress());
		nutzer.setErstellungsZeitpunkt(new Date());
		/**
		 * Fordert den Nutzer auf Vor-, Nachname und Nickname einzugeben,
		 * da diese Information nicht über die Google API bezogen werden kann
		 * bzw. geändert werden soll
		 * 
		 * @author Eric Schmidt
		 */
		final LoginCustomDialog dialog = new LoginCustomDialog(googleNutzer.getNickname());
		DialogBox dlb = dialog;
		dlb.center();
		
		dlb.addCloseHandler(new CloseHandler<PopupPanel>(){
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				nutzer.setVorname(dialog.getVorname());
				nutzer.setName(dialog.getNachname());
				nutzer.setNickname(dialog.getNickname());
				PinnwandAdministration.createNutzer(nutzer, new AsyncCallback<Nutzer>(){
					@Override
					public void onFailure(Throwable caught) {}

					@Override
					public void onSuccess(Nutzer result) {
						if (entryPointKlasse instanceof SocialMediaPinnwand){
							((SocialMediaPinnwand) entryPointKlasse).setAktuellerNutzer(result);
						}
					}
					
				});
			}
		});
	}
	
	/**
	 * Sollte der User nicht bei Google angemeldet sein, 
	 * verlinke auf login Seite von Google
	 * 
	 * @author Eric Schmidt
	 */
	 public void loadLogin() {	  
		  Window.Location.assign(loginInfo.getLoginUrl());
	  }
	  
	  /**
	   * Logge den aktuellen Nutzer aus und verlinke auf das Login Fenster
	   * 
	   * @author Eric Schmidt
	   */
	  public void loadLogout() {	  
		  Window.Location.assign(loginInfo.getLogoutUrl());
	  }
}
