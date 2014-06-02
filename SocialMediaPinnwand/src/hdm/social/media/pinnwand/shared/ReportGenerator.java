package hdm.social.media.pinnwand.shared;

import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

	public abstract String CreateNutzerReport(
		      Nutzer n) throws IllegalArgumentException;

}
