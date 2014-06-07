package hdm.social.media.pinnwand.shared;

import java.util.ArrayList;
import java.util.Date;

import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

	public abstract String CreateNutzerReport(
		      Nutzer n, Date datumVon, Date datumBis) throws IllegalArgumentException;
	
	public abstract String CreatBeitragReport(Date datumVon, Date datumBis) throws IllegalArgumentException;
	  /**
	   * Gebe alle Nutzer aus
	   * 
	   * @return ArrayList<Nutzer>
	   * @author Eric Schmidt
	   */
	  public ArrayList<Nutzer> getAllNutzer() throws IllegalArgumentException;

}
