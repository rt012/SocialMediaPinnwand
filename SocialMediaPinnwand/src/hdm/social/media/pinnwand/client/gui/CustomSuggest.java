package hdm.social.media.pinnwand.client.gui;

import java.io.Serializable;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import hdm.social.media.pinnwand.shared.bo.Nutzer;

/**
 * CustomSuggest um Nutzer Objekte anzuzeigen. Implementiert die Interfaces
 * Suggestion, und Serializable (Da diese Informationen im Netz.
 * 
 * @author Eric Schmidt
 */
public class CustomSuggest implements Suggestion, Serializable {

	private static final long serialVersionUID = 1L;
	private Nutzer value;

	/**
	 * Konstruktor um den aktuellen Nutzer als Suggestion einzutragen
	 * @param value
	 */
    public CustomSuggest(Nutzer value) {
        this.value = value;
    }
    /**
     * Gibt an wie die ausgegebene Suggestion angezeigt werden soll
     */
    @Override
    public String getDisplayString() {
        return value.getName() + ", " + value.getVorname();
    }
  
	/**
	 * Methode um Nutzer auszulesen 
	 * @return
	 */
	public Nutzer getNutzer(){
		return this.value;
	}
	
	/**
	 * Methode wird nicht benoetigt und enthält keine implementation
	 */
	@Override
	public String getReplacementString() {
		// TODO Auto-generated method stub
		return null;
	}
}
