package hdm.social.media.pinnwand.shared;

import hdm.social.media.pinnwand.client.gui.LoginInfo;
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PinnwandAdministrationAsync {

	void createAbo(Nutzer abonnement, Nutzer lieferant,
			AsyncCallback<Abo> callback);

	void createBeitrag(Beitrag b, AsyncCallback<Beitrag> callback);

	void createKommentar(Kommentar k, AsyncCallback<Kommentar> callback);

	void createLike(Like l, AsyncCallback<Like> callback);

	void createNutzer(Nutzer n, AsyncCallback<Nutzer> callback);

	void deleteAbo(Abo a, AsyncCallback<Void> callback);

	void deleteBeitrag(Beitrag b, AsyncCallback<Void> callback);

	void deleteKommentar(Kommentar k, AsyncCallback<Void> callback);

	void deleteLike(Like l, AsyncCallback<Void> callback);

	void deleteNutzer(Nutzer n, AsyncCallback<Void> callback);

	void findAllBeitraege(AsyncCallback<ArrayList<Beitrag>> callback);

	void findAllNutzer(AsyncCallback<ArrayList<Nutzer>> callback);

	void getKommentarByBeitrag(Beitrag b, AsyncCallback<ArrayList<Kommentar>> callback);

	void getLikeByBeitrag(Beitrag b, AsyncCallback<ArrayList<Like>> callback);

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

	void checkIfliked(Nutzer n, Beitrag b, AsyncCallback<Boolean> callback);

	void getAllNutzer(AsyncCallback<ArrayList<Nutzer>> callback);

	void getNutzerById(int i, AsyncCallback<Nutzer> callback);

	void countLikeByBeitrag(int id, AsyncCallback<Integer> callback);

	void login(String requestUri, AsyncCallback<LoginInfo> callback);

	void checkIfLiked(Nutzer n, Beitrag b, AsyncCallback<Boolean> callback);

	void getNutzerByEmail(String email, AsyncCallback<Nutzer> callback);

	void getAboByNutzer(int id, AsyncCallback<ArrayList<Abo>> callback);

	void getAllBeitragByNutzer(Nutzer n, AsyncCallback<ArrayList<Beitrag>> callback);

	void getAllBeitragByAktuellerNutzer(Nutzer n, AsyncCallback<ArrayList<Beitrag>> callback);

	void getPinnwandByNutzer(Nutzer n, AsyncCallback<Pinnwand> callback);

}

