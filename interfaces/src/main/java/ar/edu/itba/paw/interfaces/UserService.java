package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.ResourcePackage;
import ar.edu.itba.paw.model.User;

import java.util.Collection;


/**
 * Created by juanfra on 23/03/17.
 */
public interface UserService {

    User findById(long id);
    User create(String username, String Password, String img);

    ResourcePackage getUserProductions(long id);
    ResourcePackage getUserStorage(long id);

    Collection<Factory> getUserFactories(long id);

    String getProfileImage(long userid);
}
