package hdm.social.media.pinnwand.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
   
/**
 * Custom DialogBox um die Login Informationen des Google UserService
 * zu erweitern. Erweitert die Klasse DialogBox
 * 
 * @author Eric Schmidt
 */
public class LoginCustomDialog  extends DialogBox implements ClickHandler { 
	private String vorname, nachname, nickname;
	private TextBox nachnameTextBox, vornameTextBox, nicknameTextBox;
	
	/**
	 * Erzeugt s�mtliche Widgets innerhalb der DialogBox und schreibt den Nickname,
	 * welche aus dem Google Nutzer gelesen wird, in eine TextBox.
	 * 
	 * @param nickname Der Nickname aus dem Google Nutzer
	 */
	public LoginCustomDialog(String nickname) {
		setText("Sie starten die Applikation zum ersten Mal");
		Button anmeldenButton = new Button("Erstelle einen Account", this);
		HTML msg = new HTML("<center>Bitte Sie untenstehnde Informationen ein</center>",true);
		
		HTML vornameLabel = new HTML("Vorname");
		vornameTextBox = new TextBox();
		vornameTextBox.setTitle("Vorname");
		
		HTML nicknameLabel = new HTML("Nickname");
		nicknameTextBox = new TextBox();
		nicknameTextBox.setTitle("Nickname");
		nicknameTextBox.setText(nickname);
		
		HTML nachnameLabel = new HTML("Nachname");
		nachnameTextBox = new TextBox();
		nachnameTextBox.setTitle("Vorname");
		
		DockPanel dock = new DockPanel();
		dock.setSpacing(4);
		dock.add(anmeldenButton, DockPanel.SOUTH);
		dock.add(msg, DockPanel.NORTH);
		dock.add(vornameLabel, DockPanel.NORTH);
		dock.add(vornameTextBox, DockPanel.NORTH);
		dock.add(nachnameLabel, DockPanel.NORTH);
		dock.add(nachnameTextBox, DockPanel.NORTH);
		dock.add(nicknameLabel, DockPanel.NORTH);
		dock.add(nicknameTextBox, DockPanel.NORTH);
		dock.setCellHorizontalAlignment(anmeldenButton, DockPanel.ALIGN_RIGHT);
		dock.setWidth("100%");
		setWidget(dock);
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public String getNickname() {
		return nickname;
	}

	
	/**
	 * Erstellt ein onClick Event, welches die eingebenen Werte auf die Klassen Variablen 
	 * schreibt. Diese werden spaeter durch die getter ausgelesen.
	 * 
	 */
	@Override
	public void onClick(ClickEvent event) {
		nachname = nachnameTextBox.getText();
		vorname = vornameTextBox.getText();
		nickname = nicknameTextBox.getText();
		hide();
	}
}


	