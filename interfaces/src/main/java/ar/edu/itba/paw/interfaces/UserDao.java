package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;

import java.util.Collection;

/**
 * Created by juanfra on 23/03/17.
 */
public interface UserDao {

    User findById(long id);
    User findByUsername(String username);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @return The created user.
     */
    User create(String username, String Password, String profileImage);
    Factory create(Factory factory, long userId);
    ResourceType create(ResourceType type, long userId);

    String getProfileImage(final long userid);
    Productions getUserProductions(long userid);
    Storage getUserStorage(long userid);

    Collection<Factory> getUserFactories(long userid);
    Factory update(Factory f);

    Wealth getUserWealth(long userid);
    Wealth update(Wealth w);


}
