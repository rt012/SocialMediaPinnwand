package hdm.social.media.pinnwand.server;

import hdm.social.media.pinnwand.client.PinnwandAdministration;
import hdm.social.media.pinnwand.server.db.AboMapper;
import hdm.social.media.pinnwand.server.db.BeitragMapper;
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

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class PinnwandAdministrationImpl extends RemoteServiceServlet implements PinnwandAdministration {

public PinnwandAdministrationImpl() throws IllegalArgumentException {}

public void init() throws IllegalArgumentException {}
@Override

public Nutzer createNutzer() throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
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
return LikeMapper.likeMapper().insertLike(l);
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
public Abo createAbo(Nutzer abonnement, Nutzer lieferant)
throws IllegalArgumentException {
// TODO Auto-generated method stub
return null;
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


}
