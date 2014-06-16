package hdm.social.media.pinnwand.client.gui.pinnwand;


import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.bo.Like;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
/**
 * Diese Klasse repr�sentiert eine DialogBox welche eine Liste von Nutzern anzeigt welche einen bestimmten Beitrag geliked haben
 * @author remi
 *
 */
public class LikeListe extends DialogBox  {
	

private Label LabelInformation;
private FlexTable FlexTableLikeListe;
private Button ButtonSchliessen;

public LikeListe(ArrayList<Like> likeListe){
	
	
	/**
	 *  Label f�r eine kurze Information �ber das DialogFenster
	 */
	LabelInformation = new Label("Nutzer, denen das gefaellt:");
	LabelInformation.setStyleName("LabelInformation");
	
	/**
	 *  FlexTable f�r darstellung der Nutzer 
	 */
	FlexTableLikeListe = new FlexTable();
	FlexTableLikeListe.setStyleName("FlexTableLikeListe");
	
	// Durchlauf durch die LikeListe welche als Paramenter im Konstruktor �bergeben wurde. Jeder Nutzer wird in einer row hinzugef�gt. 
	for(int i = 0; i < likeListe.size(); i++) {
		FlexTableLikeListe.setHTML(i, 0, likeListe.get(i).getNutzer().getVorname()+" "+likeListe.get(i).getNutzer().getName());
	}
	
	/**
	 *  Button mit Hilfe man die DialogBox wieder schlie�en kann 
	 */
	ButtonSchliessen = new Button("Schlie&szligen");
	ButtonSchliessen.setStyleName("ButtonSchliessen");
	ButtonSchliessen.addClickHandler(new ClickHandler() {
		 
		public void onClick(ClickEvent event) {
			hide();
		}
		
	});	
	

	/**
	 *  DockPanel welches die zuvor initialisierten Elemente B�ndelt 
	 */
	
	DockPanel dock = new DockPanel();
	dock.setSpacing(4);
	dock.add(LabelInformation, DockPanel.NORTH);
	dock.add(FlexTableLikeListe, DockPanel.NORTH);
	dock.add(ButtonSchliessen, DockPanel.NORTH);
	dock.setWidth("100%");
	setWidget(dock);

}
}
