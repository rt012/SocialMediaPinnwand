package hdm.social.media.pinnwand.shared;

import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReportGeneratorAsync {

	void CreateNutzerReport(Nutzer n, AsyncCallback<String> callback);

}
