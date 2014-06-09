package hdm.social.media.pinnwand.shared;

import java.util.ArrayList;

import hdm.social.media.pinnwand.client.LoginInfo;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReportGeneratorAdministrationAsync {

	void CreateNutzerReport(Nutzer n, String datumVon, String datumBis,
			AsyncCallback<String> callback);

	void getAllNutzer(AsyncCallback<ArrayList<Nutzer>> callback);

	void createBeitragReport(String datumVon, String datumBis,
			AsyncCallback<String> callback);

	void login(String requestUri, AsyncCallback<LoginInfo> callback);

}
