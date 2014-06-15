package hdm.social.media.pinnwand.client.gui.pinnwand;

import hdm.social.media.pinnwand.client.SocialMediaPinnwand;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.PinnwandAdministrationAsync;
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Nutzer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
   
/**
 * Frag ab ob Nutzer wirklich eine Abonnementbeziehung mit einem anderen Nutzer eingehen will
 * 
 * @author Eric Schmidt
 *
 */
@SuppressWarnings("deprecation")
public class AbonnementCustomDialog  extends DialogBox{ 
	private final PinnwandAdministrationAsync PinnwandAdministration = GWT.create(PinnwandAdministration.class);
	
	/**
	 * Konstruktor der Klasse
	 * 
	 * @param content
	 * @param title
	 * @param abonnent
	 * @param lieferant
	 * @param flexTableAbonniertePinnwaende
	 * @param showbeitraege
	 */
	public AbonnementCustomDialog(final SocialMediaPinnwand s, String content, String title, final Nutzer abonnent, final Nutzer lieferant, 
			final Abolist flexTableAbonniertePinnwaende, final ShowBeitraege showbeitraege) {
		setText(content);
		
		/**
		 * Feuert wenn Nutzer auf Abonnieren drückt
		 */
		Button abonnierenButton = new Button("Abonnieren", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				PinnwandAdministration.createAbo(abonnent, lieferant, new AsyncCallback<Abo>() {
					public void onFailure(Throwable error) {
						Window.alert("Abonnement wurde nicht abgeschlossen");	
					}
					@Override
					public void onSuccess(Abo result) {
						Window.alert("Pinnwand abonniert");	
						flexTableAbonniertePinnwaende.refresh(s.getAktuellerNutzer());
						showbeitraege.refresh(s.getAktuellerNutzer());
					}
			    });	
				hide();
			}
		});
		Button abbrechenButton = new Button("Abbrechen", new ClickListener(){
			@Override
			public void onClick(Widget sender) {
				hide();	
			}	
		});
		HTML msg = new HTML("<center>" + title + "</center>",true);
		DockPanel dock = new DockPanel();
		dock.setSpacing(4);
		dock.add(abonnierenButton, DockPanel.SOUTH);
		dock.add(abbrechenButton, DockPanel.SOUTH);
		dock.add(msg, DockPanel.NORTH);
		dock.setCellHorizontalAlignment(abonnierenButton, DockPanel.ALIGN_RIGHT);
		dock.setCellHorizontalAlignment(abbrechenButton, DockPanel.ALIGN_RIGHT);
		dock.setWidth("100%");
		setWidget(dock);
	}
}


	