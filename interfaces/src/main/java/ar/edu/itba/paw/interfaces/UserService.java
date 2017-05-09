package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;

import java.util.Collection;


/**
 * Created by juanfra on 23/03/17.
 */
public interface UserService {

    User findById(long id);
    User findByUsername(String username);

    User create(String username, String Password, String img);

    Wealth getUserWealth(long id);
    Productions getUserProductions(long id);
    Storage getUserStorage(long id);

    boolean purchaseFactory(long userid, FactoryType type);
    boolean purchaseUpgrade(long userid, FactoryType type);
    Wealth purchaseResourceType(long userid, ResourceType resourceType, double amount);
    Wealth sellResourceType(long userid, ResourceType resourceType, double amount);

    Collection<Factory> getUserFactories(long id);

    String getProfileImage(long userid);
}
