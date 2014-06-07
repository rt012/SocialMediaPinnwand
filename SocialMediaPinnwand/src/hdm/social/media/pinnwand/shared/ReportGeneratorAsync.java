package hdm.social.media.pinnwand.shared;

import java.util.ArrayList;
import java.util.Date;

import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReportGeneratorAsync {

	void CreateNutzerReport(Nutzer n, Date datumVon, Date datumBis,
			AsyncCallback<String> callback);

	void getAllNutzer(AsyncCallback<ArrayList<Nutzer>> callback);

	void CreatBeitragReport(Date datumVon, Date datumBis,
			AsyncCallback<String> callback);

}
