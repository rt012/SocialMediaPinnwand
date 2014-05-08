package hdm.social.media.pinnwand.client;

import hdm.social.media.pinnwand.shared.Abo;
import hdm.social.media.pinnwand.shared.Beitrag;
import hdm.social.media.pinnwand.shared.Kommentar;
import hdm.social.media.pinnwand.shared.Like;
import hdm.social.media.pinnwand.shared.Nutzer;
import hdm.social.media.pinnwand.shared.Pinnwand;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PinnwandAdministrationAsync {

	void createAbo(Nutzer abonnement, Nutzer lieferant,
			AsyncCallback<Abo> callback);

	void createBeitrag(Beitrag b, AsyncCallback<Beitrag> callback);

	void createKommentar(Kommentar k, AsyncCallback<Kommentar> callback);

	void createLike(Like l, AsyncCallback<Like> callback);

	void createNutzer(AsyncCallback<Nutzer> callback);

	void deleteAbo(Abo a, AsyncCallback<Void> callback);

	void deleteBeitrag(Beitrag b, AsyncCallback<Void> callback);

	void deleteKommentar(Kommentar k, AsyncCallback<Void> callback);

	void deleteLike(Like l, AsyncCallback<Void> callback);

	void deleteNutzer(Nutzer n, AsyncCallback<Void> callback);

	void findAllBeitraege(AsyncCallback<ArrayList<Beitrag>> callback);

	void findAllNutzer(AsyncCallback<ArrayList<Nutzer>> callback);

	void getAllKommentarJeBeitrag(Beitrag b,
			AsyncCallback<ArrayList<Kommentar>> callback);

	void getAllLikeJeBeitrag(Beitrag b, AsyncCallback<ArrayList<Like>> callback);

	void getNutzerById(int id, AsyncCallback<Nutzer> callback);

	void getPinnwandById(int id, AsyncCallback<Pinnwand> callback);

	void init(AsyncCallback<Void> callback);

	void saveAbo(Abo a, AsyncCallback<Void> callback);

	void saveBeitrag(Beitrag b, AsyncCallback<Void> callback);

	void saveKommentar(Kommentar k, AsyncCallback<Void> callback);

	void saveLike(Like l, AsyncCallback<Void> callback);

	void saveNutzer(Nutzer n, AsyncCallback<Void> callback);

	void savePinnwand(Pinnwand p, AsyncCallback<Void> callback);

	void updateBeitrag(Beitrag b, AsyncCallback<Beitrag> callback);

	void updateKommentar(Kommentar k, AsyncCallback<Kommentar> callback);

	void updateNutzer(Nutzer n, AsyncCallback<Nutzer> callback);

}
