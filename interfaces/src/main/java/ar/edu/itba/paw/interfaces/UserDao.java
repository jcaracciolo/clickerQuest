package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;

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
    User create(String username, String Password, String profileImage);

    String getProfileImage(final long userid);

    Productions getUserProductions(long userid);

    Storage getUserStorage(long userid);

    Collection<Factory> getUserFactories(long userid);
    Factory getUserFactory(long userid,FactoryType f);
    Factory update(Factory f);

    Wealth getUserWealth(long userid);
    Wealth update(Wealth w);


}
