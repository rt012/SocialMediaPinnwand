package hdm.social.media.pinnwand.client.gui;

import java.io.Serializable;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import hdm.social.media.pinnwand.shared.bo.Nutzer;

/**
 * Custome Suggest um Nutzer Objekte anzuzeigen
 * 
 * @author Eric Schmidt
 */
public class CustomSuggest implements Suggestion, Serializable {

	private static final long serialVersionUID = 1L;
	private Nutzer value;

    public CustomSuggest(Nutzer value) {
        this.value = value;
    }

    @Override
    public String getDisplayString() {
        return value.getName() + ", " + value.getVorname();
    }

	@Override
	public String getReplacementString() {
		// TODO Auto-generated method stub
		return null;
	} 
	
	public Nutzer getNutzer(){
		return this.value;
	}
}
