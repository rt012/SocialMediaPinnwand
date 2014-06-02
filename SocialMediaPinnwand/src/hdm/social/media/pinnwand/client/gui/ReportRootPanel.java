package hdm.social.media.pinnwand.client.gui;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabBar;


public class ReportRootPanel extends SplitLayoutPanel{
	
	private TabBar tabBar = new TabBar();
	
	public ReportRootPanel(){
		tabBar.addTab("Nutzer-Report");
		tabBar.addTab("Beitrag-Report");

	    // Hook up a tab listener to do something when the user selects a tab.
		tabBar.addSelectionHandler(new SelectionHandler<Integer>() {
	      public void onSelection(SelectionEvent<Integer> event) {
	        // Let the user know what they just did.
	        switch(event.getSelectedItem().intValue()){
	        case 0:
	        	clear();
	        	addNorth(tabBar, 32);
	        	add(new NutzerReportPanel());
	        	
	        	break;
	        case 1:
	        	clear();
	        	addNorth(tabBar, 32);
	        	add (new BeitragReportPanel());
	        	break;
	        }
	      }
	    });
	    
		tabBar.selectTab(0); //Starte mit Social Media Pinnwand
		
	}
	
	
}
