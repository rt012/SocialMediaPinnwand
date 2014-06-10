package hdm.social.media.pinnwand.shared;

import java.util.ArrayList;

import hdm.social.media.pinnwand.client.gui.LoginInfo;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGeneratorAdministration extends RemoteService {

	public abstract String CreateNutzerReport(
		      Nutzer n, String datumVon, String datumBis) throws IllegalArgumentException;
	
	public abstract String createBeitragReport(String datumVon, String datumBis) throws IllegalArgumentException;
	  
	
	/**
	   * Gebe alle Nutzer aus
	   * 
	   * @return ArrayList<Nutzer>
	   * @author Eric Schmidt
	*/
	public ArrayList<Nutzer> getAllNutzer() throws IllegalArgumentException;
	
	
	public LoginInfo login(String requestUri);
	
	public Nutzer getNutzerByEmail(String email) throws IllegalArgumentException;
}
