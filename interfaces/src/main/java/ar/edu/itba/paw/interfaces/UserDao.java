package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.ResourcePackage;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Wealth;

import java.util.Collection;

/**
 * Created by juanfra on 23/03/17.
 */
public interface UserDao {

    User findById(long id);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @return The created user.
     */
    User create(String username, String Password);

    String getProfileImage(final long userid);

    ResourcePackage getUserProductions(long userid);
    ResourcePackage getUserStorage(long userid);
    Collection<Factory> getUserFactories(long userid);
    Wealth getUserWealth(long userid);


}
