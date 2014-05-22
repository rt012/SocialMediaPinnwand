package hdm.social.media.pinnwand.server;

import hdm.social.media.pinnwand.client.LoginInfo;
import hdm.social.media.pinnwand.client.PinnwandAdministration;
import hdm.social.media.pinnwand.server.db.AboMapper;
import hdm.social.media.pinnwand.server.db.BeitragMapper;
import hdm.social.media.pinnwand.server.db.KommentarMapper;
import hdm.social.media.pinnwand.server.db.LikeMapper;
import hdm.social.media.pinnwand.server.db.NutzerMapper;
import hdm.social.media.pinnwand.server.db.PinnwandMapper;
import hdm.social.media.pinnwand.shared.Abo;
import hdm.social.media.pinnwand.shared.Beitrag;
import hdm.social.media.pinnwand.shared.Kommentar;
import hdm.social.media.pinnwand.shared.Like;
import hdm.social.media.pinnwand.shared.Nutzer;
import hdm.social.media.pinnwand.shared.Pinnwand;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class PinnwandAdministrationImpl extends RemoteServiceServlet implements PinnwandAdministration {

public PinnwandAdministrationImpl() throws IllegalArgumentException {}

public void init() throws IllegalArgumentException {}
@Override

public Nutzer createNutzer(Nutzer n) throws IllegalArgumentException {
	return NutzerMapper.nutzerMapper().insertNutzer(n);

}

@Override
public Nutzer updateNutzer(Nutzer n) throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
}

@Override
public Nutzer getNutzerById(int id) throws IllegalArgumentException {

// TODO Auto-generated method stub
return NutzerMapper.nutzerMapper().getNutzerById(id);
}

@Override
public void deleteNutzer(Nutzer n) throws IllegalArgumentException {
// TODO Auto-generated method stub

}

@Override
public void saveNutzer(Nutzer n) throws IllegalArgumentException {
// TODO Auto-generated method stub

}

@Override
public void savePinnwand(Pinnwand p) throws IllegalArgumentException {
// TODO Auto-generated method stub

}

@Override
public Pinnwand getPinnwandById(int id) throws IllegalArgumentException {
// TODO Auto-generated method stub
return PinnwandMapper.pinnwandMapper().getPinnwandById(id);
}

@Override
public Beitrag createBeitrag(Beitrag b) throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
}

@Override
public void saveBeitrag(Beitrag b) throws IllegalArgumentException {
BeitragMapper.beitragMapper().insertBeitrag(b);

}

@Override
public Beitrag updateBeitrag(Beitrag b) throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
}

@Override
public void deleteBeitrag(Beitrag b) throws IllegalArgumentException {
BeitragMapper.beitragMapper().deleteBeitrag(b);

}

@Override
public ArrayList<Beitrag> findAllBeitraege()
throws IllegalArgumentException {

return  BeitragMapper.beitragMapper().getAllBeitrag();
}

@Override
public ArrayList<Nutzer> findAllNutzer() throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
}

@Override
public Kommentar createKommentar(Kommentar k)
throws IllegalArgumentException {
// TODO Auto-generated method stub
return KommentarMapper.kommentarMapper().insertKommentar(k);
}

@Override
public void saveKommentar(Kommentar k) throws IllegalArgumentException {
// TODO Auto-generated method stub

}

@Override
public Kommentar updateKommentar(Kommentar k)
throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
}

@Override
public void deleteKommentar(Kommentar k) throws IllegalArgumentException {
KommentarMapper.kommentarMapper().deleteKommentar(k);
}

@Override
public ArrayList<Kommentar> getAllKommentarJeBeitrag(Beitrag b)
throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
}

@Override
public Like createLike(Like l) throws IllegalArgumentException {
// TODO Auto-generated method stub
return LikeMapper.likeMapper().insertLike(l);
}

@Override
public void saveLike(Like l) throws IllegalArgumentException {
// TODO Auto-generated method stub

}

@Override
public void deleteLike(Like l) throws IllegalArgumentException {
LikeMapper.likeMapper().deleteLike(l);

}

@Override
public ArrayList<Like> getAllLikeJeBeitrag(Beitrag b)
throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
}

@Override
public Abo createAbo(Nutzer abonnement, Nutzer lieferant) throws IllegalArgumentException {
	Abo a = new Abo();
	a.setAbonnent(abonnement);
	a.setLieferant(lieferant);
	a.setErstellungsZeitpunkt(new Date());
	return AboMapper.aboMapper().insertAbo(a);
}

@Override
public void saveAbo(Abo a) throws IllegalArgumentException {
// TODO Auto-generated method stub

}

@Override
public void deleteAbo(Abo a) throws IllegalArgumentException {
// TODO Auto-generated method stub

}


@Override
public ArrayList<Nutzer> getAllNutzer() throws IllegalArgumentException {
	return NutzerMapper.nutzerMapper().getAllNutzer();
}


/*public ArrayList<Abo> getAboByNutzer(Nutzer n) {
  return AboMapper.aboMapper().getAboByNutzer(n.getId());
 }
 */

public ArrayList<Abo> getAboByNutzer(){
	return AboMapper.aboMapper().getAboByNutzer(1);
}

public int countLikeByBeitrag(int id) throws IllegalArgumentException {
	return LikeMapper.likeMapper().countLikeByBeitrag(id);
	// TODO Auto-generated method stub

}


/**
 * Die Methode ist daf�r zust�ndig Userlogin zu bestimmen
 * Wenn eingeloggt: �bertr�gt infos von Userservice auf Login info
 * Wenn nicht: 	Set Login auf false
 * 				Set login url auf request Uri -> r�ckleitung
 * 
 */
@Override
public LoginInfo login(String requestUri) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	LoginInfo loginInfo = new LoginInfo();

	if (user != null) {
		loginInfo.setLoggedIn(true);
	    loginInfo.setEmailAddress(user.getEmail());
	    loginInfo.setNickname(user.getNickname());
	    loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
	}else {
	    loginInfo.setLoggedIn(false);
	    loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
	}
		return loginInfo;
	}




@Override
public boolean checkIfliked(Nutzer n, Beitrag b)
		throws IllegalArgumentException {
	
	return LikeMapper.likeMapper().checIfLiked(n, b);
}

}

