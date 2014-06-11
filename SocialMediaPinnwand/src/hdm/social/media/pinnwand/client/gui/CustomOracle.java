package hdm.social.media.pinnwand.client.gui;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.user.client.ui.SuggestOracle;

/**
 * Die Klasse dient dazu, dem Widget SuggestBox anderer Werte (Suggest) zuzuweisen
 * als Strings (Hierbei werden Nutzer Objekte übergeben). Erweitert die Klasse SuggestOracle 
 * 
 *@author Eric Schmidt
 */
public class CustomOracle extends SuggestOracle{
	Collection<CustomSuggest> collection;

	/**
	 * Setzt eine Collection aus CustomSuggest auf die Klassen Variable collection.
	 * Diese behinhaltet die Nutzer Objekte
	 * 
	 * @param collection
	 */
	public void setCollection(Collection<CustomSuggest> collection){
		 this.collection = collection;
	}

	/**
	 * Überschreibt die Methode requestSuggestion der Klasse SuggestOracle.
	 * Diese dient dem Zweck, Eingaben in die SuggestBox zu verwalten.
	 * 
	 */
	@Override
	public void requestSuggestions(Request request, Callback callback) {
	  final Response response = new Response();
	 /**
	  * Die Variable query beinhaltet die bis jetzt eingegebene Suchanfrage
	  */
      String query = request.getQuery();
      ArrayList<CustomSuggest> responseList = new ArrayList<CustomSuggest>();
      ArrayList<CustomSuggest> collectionArrayList = new ArrayList<CustomSuggest>();
      collectionArrayList.addAll(collection);
      /**
       *  Durchsucht die Suggestions, sobald mindestens als zwei Buchstaben eingebenen wurden
       */
      if (query.length() >= 2) { 
    	  for (CustomSuggest s : collectionArrayList){
    		  if ((s.getNutzer().getName() + "," + s.getNutzer().getVorname()).contains(query)){
    			  CustomSuggest suggest = new CustomSuggest(s.getNutzer());
    			  responseList.add(suggest);
    		  }
    	  }
    	  Collection<CustomSuggest> collectionResponse = responseList;
    	  response.setSuggestions(collectionResponse);
      } else {

      }
      callback.onSuggestionsReady(request, response);
	}
}
