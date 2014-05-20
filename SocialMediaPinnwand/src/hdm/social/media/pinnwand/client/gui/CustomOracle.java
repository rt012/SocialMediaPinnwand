package hdm.social.media.pinnwand.client.gui;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.user.client.ui.SuggestOracle;

public class CustomOracle extends SuggestOracle{
	Collection<CustomSuggest> collection;

	public void setCollection(Collection<CustomSuggest> collection){
		 this.collection = collection;
	}

	@Override
	public void requestSuggestions(Request request, Callback callback) {
	  final Response response = new Response();
	  // this is the string, the user has typed so far
      String query = request.getQuery();
      ArrayList<CustomSuggest> responseList = new ArrayList<CustomSuggest>();
      ArrayList<CustomSuggest> collectionArrayList = new ArrayList<CustomSuggest>();
      collectionArrayList.addAll(collection);
      // look up for suggestions, only if at least 2 letters have been typed
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
