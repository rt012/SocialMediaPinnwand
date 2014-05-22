package hdm.social.media.pinnwand.client;

import hdm.social.media.pinnwand.shared.Kommentar;
import hdm.social.media.pinnwand.shared.Like;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
/**
 * Diese Klasse repräsentiert eine DialogBox welche eine Liste von Nutzern anzeigt welche einen bestimmten Beitrag geliked haben
 * @author remi
 *
 */
public class LikeListe extends DialogBox  {

private Label LabelInformation;
private FlexTable FlexTableLikeListe;
private Button ButtonSchliessen;

public LikeListe(ArrayList<Like> likeListe){
	// Label für eine kurze Information über das DialogFenster
	LabelInformation = new Label("Nutzer, denen das gefällt:");
	LabelInformation.setStyleName("LabelInformation");
	// FlexTable für darstellung der Nutzer 
	FlexTableLikeListe = new FlexTable();
	FlexTableLikeListe.setStyleName("FlexTableLikeListe");
	// Durchlauf durch die LikeListe welche als Paramenter im Konstruktor übergeben wurde. Jeder Nutzer wird in einer row hinzugefügt. 
	for(int i = 0; i < likeListe.size(); i++) {
		FlexTableLikeListe.setHTML(i, 0, likeListe.get(i).getNutzer().getName());
	}
	// Button mit Hilfe man die DialogBox wieder schließen kann 
	ButtonSchliessen = new Button("Schließen");
	ButtonSchliessen.setStyleName("ButtonSchließen");
	ButtonSchliessen.addClickHandler(new ClickHandler() {
		 
		public void onClick(ClickEvent event) {
			hide();
		}
});
	
	// DockPanel welches die zuvor initialisierten Elemente Bündelt 
	DockPanel dock = new DockPanel();
	dock.setSpacing(4);
	dock.add(LabelInformation, DockPanel.NORTH);
	dock.add(FlexTableLikeListe, DockPanel.NORTH);
	dock.add(ButtonSchliessen, DockPanel.NORTH);
	dock.setWidth("100%");
	setWidget(dock);
}
}
