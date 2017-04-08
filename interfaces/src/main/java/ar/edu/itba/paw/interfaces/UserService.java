package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.model.packages.Production;
import ar.edu.itba.paw.model.packages.ResourcePackage;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.packages.Storage;

import java.util.Collection;


/**
 * Created by juanfra on 23/03/17.
 */
public interface UserService {

    User findById(long id);
    User create(String username, String Password, String img);

    Wealth getUserWealth(long id);
    Production getUserProductions(long id);
    Storage getUserStorage(long id);

    boolean purchaseFactory(long userid, FactoryType f);

    Collection<Factory> getUserFactories(long id);

    String getProfileImage(long userid);
}
