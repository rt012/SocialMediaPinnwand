package hdm.social.media.pinnwand.server;

import hdm.social.media.pinnwand.client.LoginInfo;
import hdm.social.media.pinnwand.server.db.AboMapper;
import hdm.social.media.pinnwand.server.db.BeitragMapper;
import hdm.social.media.pinnwand.server.db.LikeMapper;
import hdm.social.media.pinnwand.server.db.NutzerMapper;
import hdm.social.media.pinnwand.shared.PinnwandAdministration;
import hdm.social.media.pinnwand.shared.bo.Abo;
import hdm.social.media.pinnwand.shared.bo.Beitrag;
import hdm.social.media.pinnwand.shared.bo.Kommentar;
import hdm.social.media.pinnwand.shared.bo.Like;
import hdm.social.media.pinnwand.shared.bo.Nutzer;
import hdm.social.media.pinnwand.shared.bo.Pinnwand;

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
return null;
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
return null;
}

@Override
public Beitrag createBeitrag(Beitrag b) throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
}

@Override
public void saveBeitrag(Beitrag b) throws IllegalArgumentException {
// TODO Auto-generated method stub

}

@Override
public Beitrag updateBeitrag(Beitrag b) throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
}

@Override
public void deleteBeitrag(Beitrag b) throws IllegalArgumentException {
// TODO Auto-generated method stub

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
return null;
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
// TODO Auto-generated method stub

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
return null;
}

@Override
public void saveLike(Like l) throws IllegalArgumentException {
// TODO Auto-generated method stub

}

@Override
public void deleteLike(Like l) throws IllegalArgumentException {
// TODO Auto-generated method stub

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
public ArrayList<Abo> getAboByNutzer(int id) throws IllegalArgumentException {
	return AboMapper.aboMapper().getAboByNutzer(id);
}

@Override
public int getLikeCountByNutzer(Nutzer n) throws IllegalArgumentException {
	return LikeMapper.likeMapper().getLikeCountByNutzer(n);
}
}

